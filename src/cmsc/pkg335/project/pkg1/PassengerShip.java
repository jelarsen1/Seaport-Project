// class PassengerShip
// 6/4/2017  Author: Jeremiah Larsen
// Purpose: Stores information about a PassengerShip.  Subclass of Ship.


package cmsc.pkg335.project.pkg1;

import java.util.Scanner;


public class PassengerShip extends Ship{
    int numberOfOccupiedRooms, numberOfPassengers, numberOfRooms;

    public PassengerShip(Scanner sc) {
        super(sc);
        if(sc.hasNextInt()) numberOfOccupiedRooms = sc.nextInt();
        if(sc.hasNextInt()) numberOfPassengers = sc.nextInt();
        if(sc.hasNextInt()) numberOfRooms = sc.nextInt();
        
    }
    
    public String toString(){
        String st = "Passenger ship: " + super.toString();
        return st;
    }
}
