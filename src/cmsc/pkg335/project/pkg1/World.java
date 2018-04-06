// class World
// 6/4/2017  Author: Jeremiah Larsen
// Purpose: stores an ArrayList of SeaPorts.  Subclass of Thing.


package cmsc.pkg335.project.pkg1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;
import javax.swing.JProgressBar;
import javax.swing.tree.DefaultMutableTreeNode;


public class World extends Thing{
    ArrayList<SeaPort> ports = new ArrayList<>();
    HashMap<Integer, SeaPort> portMap = new HashMap<>();

    public World(Scanner sc) {
        super(sc);
    }
    
    public void addPort(SeaPort port){
        portMap.put(port.index, port);
        ports.add(port);
    }
    
    public void addShip(Ship s){
        SeaPort tempPort = this.searchPortByIndex(s.parent);
        if(tempPort!=null) {
            tempPort.addShip(s);
            tempPort.addQue(s);
        }
        else{
            for(SeaPort sp:ports){
                Dock tempDock = sp.searchDockByIndex(s.parent);
                if(tempDock!=null) {
                    tempDock.addShip(s);
                    this.searchPortByIndex(tempDock.parent).addShip(s);
                }
            }
        }
    }
    
    public void addJob(Job tempJob) {
        for(SeaPort tempPort:ports){
            tempPort.addJob(tempJob);
        }
    }
    
    public SeaPort searchPortByIndex(int index){
        return portMap.get(index);
    }
   
    public ArrayList<Ship> searchShipName(String n){
        ArrayList<Ship> searchList = new ArrayList<Ship>();
        for(SeaPort a: ports){
            for(Ship s:a.ships){
                if(s.name.compareTo(n)==0) searchList.add(s);
            }
        }
        
        return searchList;
    }
    
    public ArrayList<Ship> searchShipIndex(int i){
        ArrayList<Ship> searchList = new ArrayList<Ship>();
        for(SeaPort a: ports){
            for(Ship s: a.ships){
                if(s.index==i) searchList.add(s);
            }
        }
        
        return searchList;
    }
    
    public ArrayList<Person> searchPersonSkill(String s){
        ArrayList<Person> searchList = new ArrayList<Person>();
        for(SeaPort a: ports){
            for(Person p: a.persons){
                if(p.skill.compareTo(s)==0) searchList.add(p);
            }
        }
        
        return searchList;
    }
    
    public String toString(){
        String st;
        st = ">>>>> The World";
        for(SeaPort sp: ports) st+= "\n" + sp;
        
        return st;
    }
    
    public String sortShipsByWeight(){
        String sorted = "----Ships in Queue by Weight----";
        for(SeaPort sp: ports ){
            sorted += "\n\n>>>  Port: " + sp.name + " " + sp.index + " " + sp.parent + "\n";
            sorted += sp.sortShipByWeight();
        }
        return sorted;
    }
    
    public String sortShipsByLength(){
        String sorted = "----Ships in Queue by Length----";
        for(SeaPort sp: ports ){
            sorted += "\n\n>>>  Port: " + sp.name + " " + sp.index + " " + sp.parent + "\n";
            sorted += sp.sortShipByLength();
        }
        return sorted;
    }
    
    public String sortShipsByWidth(){
        String sorted = "----Ships in Queue by Width----";
        for(SeaPort sp: ports ){
            sorted += "\n\n>>>  Port: " + sp.name + " " + sp.index + " " + sp.parent + "\n";
            sorted += sp.sortShipByWidth();
        }
        return sorted;
    }
    
    public String sortShipsByDraft(){
        String sorted = "----Ships in Queue by Draft----";
        for(SeaPort sp: ports ){
            sorted += "\n\n>>>  Port: " + sp.name + " " + sp.index + " " + sp.parent + "\n";
            sorted += sp.sortShipByDraft();
        }
        return sorted;
    }
    
    public String sortShipsByName(){
        String sorted = "----All Ships by Name----";
        for(SeaPort sp: ports ){
            sorted += "\n\n>>>  Port: " + sp.name + " " + sp.index + " " + sp.parent + "\n";
            sorted += sp.sortShipByName();
        }
        return sorted;
    }
    
    public String sortPeople(){
        String sorted = "----All People by Name----";
        for(SeaPort sp: ports ){
            sorted += "\n\n>>>  Port: " + sp.name + " " + sp.index + " " + sp.parent + "\n";
            sorted += sp.sortPeople();
        }
        return sorted;
    }
    
    public String sortPorts(){
        String sorted = "----All Ports by Name---";
        Collections.sort(ports);
        for(SeaPort sp:ports) sorted += "\n\n>>>  Port: " + sp.name + " " + sp.index + " " + sp.parent + "\n";
        return sorted;
    }
    
    public String sortDocks(){
        String sorted = "----All Docks by Name----";
        for(SeaPort sp: ports ){
            sorted += "\n\n>>>  Port: " + sp.name + " " + sp.index + " " + sp.parent + "\n";
            sorted += sp.sortDocks();
        }
        return sorted;
    }
    
    public int jobSize(){
        int i=0;
        for(SeaPort sp:ports){
            for(Ship s:sp.ships){
                i+=s.jobs.size();
            }
        }
        return i;
    }
    
    public void startThread(){
        for(SeaPort sp:ports){
            sp.startThread();
        }
    }

    public ArrayList<JProgressBar> getJPs(){
        ArrayList<JProgressBar> jps = new ArrayList<>();
        for(SeaPort sp:ports){
            jps.addAll(sp.getJPs());
        }
        return jps;
    }
    
    public ArrayList<Job> getJobs(){
        ArrayList<Job> jobs = new ArrayList<>();
        for(SeaPort sp:ports){
            jobs.addAll(sp.jobs);
        }
        return jobs;
    }
    
    public DefaultMutableTreeNode getTree(){
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("World: "+name);
        for(SeaPort sp:ports){
            root.add(sp.getTree());
        }
        return root;
    }

    public boolean jobsDone() {
        for(SeaPort p:ports) if(!p.jobsDone()) return false;
        return true;
    }
    
    public String getResources(){
        String st = "";
        for(SeaPort sp:ports){
            st+="Seaport: "+sp.name+"\n";
            st+=sp.getResources();
        }
        return st;
    }
}
