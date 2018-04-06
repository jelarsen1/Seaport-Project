// class Thing
// 6/4/2017  Author: Jeremiah Larsen
// Purpose: Super class for several other classes.  

package cmsc.pkg335.project.pkg1;

import java.util.Comparator;
import java.util.Scanner;


public class Thing implements Comparator <Thing>, Comparable <Thing>{

    int index, parent;
    String name;
    
    public Thing(Scanner sc){
        name = sc.next();
        index = sc.nextInt();
        parent = sc.nextInt();
    }
    
    public String toString(){
        return name + " " + index + " " + parent;
    }

    @Override
    public int compare(Thing o1, Thing o2) {
        int result = o1.name.compareTo(o2.name);
        return result;
    }

    @Override
    public int compareTo(Thing o) {
        return (this.name).compareTo(o.name);
    }
}
