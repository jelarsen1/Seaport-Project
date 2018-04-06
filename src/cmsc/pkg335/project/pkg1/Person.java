// class Person
// 6/4/2017  Author: Jeremiah Larsen
// Purpose: Holds a person object with a skill.  Subclass of Thing.



package cmsc.pkg335.project.pkg1;

import java.util.Scanner;
import javax.swing.tree.DefaultMutableTreeNode;


class Person extends Thing{
    String skill =  "";
    boolean available = true;

    public Person(Scanner sc) {
        super(sc);
        if(sc.hasNext()) skill = sc.next();
    }
    
    public DefaultMutableTreeNode getTree(){
        DefaultMutableTreeNode node = new DefaultMutableTreeNode("Person: "+name);
        return node;
    }
    
    public String toString(){
        String st = "Person: " + super.toString();
        st+=" "+skill;
        return st;
    }
}
