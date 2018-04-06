// class Dock
// 6/4/2017  Author: Jeremiah Larsen
// Purpose: Stores information of one Dock.  Subclass of Thing.
// A Dock can house a single ship.


package cmsc.pkg335.project.pkg1;

import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;


class Dock extends Thing{
    Ship ship;
    int whatJob;
    
    public Dock(Scanner sc) {
        super(sc);
    }
    
    public void addShip(Ship s){
        ship=s;
    } 
    
    public boolean jobsDone(){
        if(ship==null) return true;
        else if(ship.jobs.isEmpty()) return true;
        else {
            for(Job j:ship.jobs){
                if (j.jobDone==false) return false;
            }
            return true;
        }
    }
    
    public String printJobs(){
        String printJobs = ship.name + ":";
        for(Job j:ship.jobs){
            printJobs+=" "+j.name;
        }
        return printJobs;
    }
    
    public String toString(){
        String st = "\nDock: " + super.toString();
        if(ship!=null) st += "\n  Ship: " + ship.toString();
        else st += "\n  No Ship in Dock";
        return st;
    }
    
    public DefaultMutableTreeNode getTree() {
        DefaultMutableTreeNode node = new DefaultMutableTreeNode("Dock: "+name);
        node.add(ship.getTree());
        return node;
    }
}
