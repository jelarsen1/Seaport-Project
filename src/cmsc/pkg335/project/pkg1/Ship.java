// class Ship
// 6/4/2017  Author: Jeremiah Larsen
// Purpose: Holds information about a ship.  Subclass of Thing


package cmsc.pkg335.project.pkg1;


import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JProgressBar;
import javax.swing.tree.DefaultMutableTreeNode;



class Ship extends Thing{
    PortTime arrivalTime, dockTime;
    double draft, length, weight, width;
    ArrayList<Job> jobs = new ArrayList<>();

    public Ship(Scanner sc) {
        super(sc);
        if(sc.hasNextInt()) arrivalTime = new PortTime(sc.nextInt());
        if(sc.hasNextInt()) dockTime = new PortTime(sc.nextInt());
        if(sc.hasNextDouble()) draft = sc.nextDouble();
        if(sc.hasNextDouble()) length = sc.nextDouble();
        if(sc.hasNextDouble()) weight = sc.nextDouble();
        if(sc.hasNextDouble()) width = sc.nextDouble();
    }
    
    public DefaultMutableTreeNode getTree() {
        DefaultMutableTreeNode node = new DefaultMutableTreeNode("Ship: "+name);
        for(Job j:jobs) node.add(j.getTree());
        return node;
    }
    
    public void addJob(Job tempJob){
        jobs.add(tempJob);
    }
}
