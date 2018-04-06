//class SeaPort
// 6/4/2017  Author: Jeremiah Larsen
// Purpose: Stores ArrayLists of Docks, queue of Ships, Ships in the dock, and Persons.  Subclass of Thing.


package cmsc.pkg335.project.pkg1;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Scanner;
import javax.swing.JProgressBar;
import javax.swing.tree.DefaultMutableTreeNode;


class SeaPort extends Thing{
    
    ArrayList<Dock> docks = new ArrayList<>();
    ArrayList<Ship> que = new ArrayList<>();
    ArrayList<Ship> ships = new ArrayList<>();
    ArrayList<Person> persons = new ArrayList<>();
    ArrayList<Job> jobs = new ArrayList<>();
    ArrayList<JProgressBar> jps = new ArrayList<>();
    ArrayList<String> skills = new ArrayList<>();
    ArrayList<Thread> threads = new ArrayList<>();
    
    HashMap<Integer, Dock> dockMap = new HashMap<>();
    HashMap<Integer, Ship> queMap = new HashMap<>();
    HashMap<Integer, Ship> shipsMap = new HashMap<>();
    HashMap<Integer, Person> personMap = new HashMap<>();
    HashMap<Integer, Job> jobMap = new HashMap<>();
    HashMap<Job, Thread> jobThreads = new HashMap<>();
    
    
    //All the Comparators to sort by various fields.
    Comparator<Ship> byWeight = new Comparator<Ship>(){
            @Override
            public int compare(Ship o1, Ship o2) {
                double i = o1.weight-o2.weight;
                if(i>0) return 1;
                if(i<1) return -1;
                return 0;
            }
    };
    
    Comparator<Ship> byLength = new Comparator<Ship>(){
            @Override
            public int compare(Ship o1, Ship o2) {
                double i = o1.length-o2.length;
                if(i>0) return 1;
                if(i<1) return -1;
                return 0;
            }
    };
    
    Comparator<Ship> byWidth = new Comparator<Ship>(){
            @Override
            public int compare(Ship o1, Ship o2) {
                double i = o1.width-o2.width;
                if(i>0) return 1;
                if(i<1) return -1;
                return 0;
            }
    };
    
    Comparator<Ship> byDraft = new Comparator<Ship>(){
            @Override
            public int compare(Ship o1, Ship o2) {
                double i = o1.draft-o2.draft;
                if(i>0) return 1;
                if(i<1) return -1;
                return 0;
            }
    };

    public SeaPort(Scanner sc) {
        super(sc);
    }
    
    // Create a thread for each job and store them in a HashMap with the key to the thread being the job.
    // Also create a JProgressBar and store it in the ArrayList.
    public void addJob(Job tempJob){
        for(Ship s:ships){
            if(s.index==tempJob.parent) s.addJob(tempJob);
        }
        jobs.add(tempJob);
        
        JProgressBar jp = new JProgressBar();
        jp.setStringPainted(true);
        jps.add(jp);
        
        // If skills are available for a job, borrow the Persons and start progressing the JProgressBar.
        Thread t=new Thread(new Runnable(){
            public void run()
            {
                if(jobCanBeDone(tempJob)){
                    while(!personsAvailable(tempJob)){}
                    jp.setMaximum((int) (5*tempJob.duration));
                    for(int i=0;i<=jp.getMaximum();i++)
                    {
                        // Update value
                        jp.setValue(i);
                        try{
                            // Get the effect
                            Thread.sleep(200);
                        }catch(InterruptedException e){}
                    }  
                    unlockPersons(tempJob);
                }
                else System.out.println(tempJob.name+" can't be done, Job cancelled");
                tempJob.jobDone = true;
            }
        }, name);
        
        jobThreads.put(tempJob, t);
    }
    
    public void addDock(Dock dock){
        dockMap.put(dock.index, dock);
        docks.add(dock);
    }
    
    public void addShip(Ship ship){
        shipsMap.put(ship.index, ship);
        ships.add(ship);
    }
    
    public void addQue(Ship ship){
        queMap.put(ship.index, ship);
        que.add(ship);
    }
    
    public void addPerson(Person person){
        personMap.put(person.index, person);
        persons.add(person);
        skills.add(person.skill);
    }
    
    public Dock searchDockByIndex(int i){
        return dockMap.get(i);
    }
    
    public String sortShipByWeight(){
        Collections.sort(que, byWeight);
        String st = "";
        for(Ship ms: que) st+= "\n >" +ms+ " (Weight:"+ms.weight+")";
        return st;
    }
    
    public String sortShipByLength(){
        Collections.sort(que, byLength);
        String st = "";
        for(Ship ms: que) st+= "\n >" +ms+ " (Length:"+ms.length+")";
        return st;
    }
    
    public String sortShipByWidth(){
        Collections.sort(que, byWidth);
        String st = "";
        for(Ship ms: que) st+= "\n >" +ms+ " (Width:"+ms.width+")";
        return st;
    }
    
    public String sortShipByDraft(){
        Collections.sort(que, byDraft);
        String st = "";
        for(Ship ms: que) st+= "\n >" +ms + " (Draft:"+ms.draft+")";
        return st;
    }
    
    public String sortShipByName(){
        Collections.sort(ships);
        String st = "";
        for(Ship ms: ships) st+= "\n >" +ms;
        return st;
    }
    
    public String sortPeople(){
        Collections.sort(persons);
        String st = "";
        for(Person mp:persons) st+= "\n >"+mp;
        return st;
    }
    
    public String sortDocks(){
        Collections.sort(persons);
        String st = "";
        for(Dock md:docks) st+= "\n >"+md;
        return st;
    }
    
    //Checks if all the docks are empty
    public boolean docksHasShip(){
        for(Dock d:docks){
            if(d.ship!=null) return true;
        }
        return false;
    }
    
    //Get All the JProgressBars
    public ArrayList<JProgressBar> getJPs(){
        return jps;
    }
    
    //Process the JProgressBars and all the Job threads
    public void startThread(){
        for(Dock d:docks){
            startThisThreads(d);
        }
        while(docksHasShip()){
            for(Dock d:docks){
                if(d.jobsDone()){
                    if(!que.isEmpty()){
                        d.ship = que.get(que.size()-1);
                        que.remove(que.size()-1);
                        startThisThreads(d);
                    }
                    else {
                        d.ship = null;
                    }
                }
            }
        }
        System.out.println("All Jobs Done!");
    }
    
    // Starts the threads for each of the jobs in a ship.
    public void startThisThreads(Dock d){
        for(Job j:d.ship.jobs){
            jobThreads.get(j).start();
        }
    }
    
    //Check if the people with the appropriate skills for a job are available.
    public synchronized boolean personsAvailable(Job j){ 
        int i = 0;
        boolean available = false;
        for(String s:j.requirements){
            for(Person p:persons){
                if(p.skill.equals(s)&&p.available) i++;
            }
        }
        if(i>=j.requirements.size()) available = true;
        if(available) lockPersons(j);
        return available;
    }
    
    // Find required people and set them unavailable
    public synchronized void lockPersons(Job j){
        ArrayList<String> doubles = new ArrayList<>();
        for(String s:j.requirements){
            for(Person p:persons){
                if(p.skill.equals(s)&&p.available) {
                    if(!doubles.contains(s)){
                        doubles.add(s);
                        p.available = false;
                        j.borrowed.add(p);
                    }
                }
            }
        }
    }
    
    // Makes the borrowed persons available.
    public synchronized void unlockPersons(Job j){
        for(Person p:j.borrowed) p.available = true;
        j.borrowed.clear();
    }
    
    //Check if required skills are in the pool of available people.
    public boolean jobCanBeDone(Job j){
        for(String st1:j.requirements){
            if(!skills.contains(st1)) return false;
        }
        return true;
    }
    
    //Return the tree node to build the JTree in the GUI
    public DefaultMutableTreeNode getTree() {
        DefaultMutableTreeNode portNode = new DefaultMutableTreeNode("Seaport: "+name);
        
        DefaultMutableTreeNode shipNode = new DefaultMutableTreeNode("Ships");
        for(Ship s:ships) shipNode.add(s.getTree());
        
        DefaultMutableTreeNode dockNode = new DefaultMutableTreeNode("Docks");
        for(Dock d:docks) dockNode.add(d.getTree());
        
        DefaultMutableTreeNode personNode = new DefaultMutableTreeNode("Persons");
        for(Person p:persons) personNode.add(p.getTree());
        
        portNode.add(shipNode);
        portNode.add(dockNode);
        portNode.add(personNode);
        return portNode;
    }
    
    public String toString(){
        
        String st = "\n\nSeaPort: " + super.toString();
        for(Dock md:docks) st+= "\n"+md;
        st += "\n\n----- List of all ships in que:";
        for(Ship ms: que) st += "\n >" +ms;
        st += "\n\n---- List of all Ships:";
        for(Ship ms: ships) st += "\n >" +ms;
        st+="\n\n--- List of all persons:";
        for(Person mp: persons) st+= "\n >" + mp;
        st+="\n\n--- List of all jobs:";
        for(Job mj: jobs) st+= "\n >" + mj;
        return st;
    }

    // Check if all the jobs for all the docks are done.
    public boolean jobsDone() {
        for(Dock d:docks) {
            if(!d.jobsDone()) return false;
            if(!que.isEmpty()) return false;
        }
        return true;
    }
    
    // Return the string of resources available and resources acquired.
    public String getResources(){
        String st = "People/Skills available:  \n";
        for(Person p:persons){
            if(p.available) st+="["+p.name+" : "+p.skill+"]";
        }
        
        for(Dock d:docks){
            st+="\n\nDock: "+d.name+" Ship: "+d.ship.name;
            for(Job j:d.ship.jobs){
                st+="\n>>Job: "+j.name+" :  "+jobThreads.get(j).isAlive()+" : ";
                for(Person p:j.borrowed) st+= "["+p.name+" : "+p.skill+"]";
            }
        }
        
        return st;
    }
}
