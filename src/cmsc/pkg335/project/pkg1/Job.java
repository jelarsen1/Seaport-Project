// class Job
// 7/1/2017  Author: Jeremiah Larsen
// Purpose: Stores information about a Job. 


package cmsc.pkg335.project.pkg1;

import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.tree.DefaultMutableTreeNode;


public class Job extends Thing{
    
    double duration;
    ArrayList<String> requirements = new ArrayList<>();
    boolean jobDone = false;
    ArrayList<Person> borrowed = new ArrayList<>();
    
    public Job(Scanner sc) {
        super(sc);
        if (sc.hasNextDouble()) duration = sc.nextDouble();
        while(sc.hasNext()){
            requirements.add(sc.next());
        }
    }
    
    public DefaultMutableTreeNode getTree(){
        DefaultMutableTreeNode node = new DefaultMutableTreeNode("Job: "+name);
        return node;
    }
    
    public String toString(){
        String st = "Job: " + super.toString();
        for(String s:requirements) st += " " + s;
        return st;
    }
}
