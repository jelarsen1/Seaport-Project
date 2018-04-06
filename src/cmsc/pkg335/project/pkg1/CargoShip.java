// class CargoShip
// 6/4/2017  Author: Jeremiah Larsen
// Purpose: Stores information about a CargoShip.  Subclass of Ship.


package cmsc.pkg335.project.pkg1;

import java.util.Scanner;


public class CargoShip extends Ship{
    double cargoValue, cargoVolume, cargoWeight;

    public CargoShip(Scanner sc) {
        super(sc);
        if(sc.hasNextDouble()) cargoValue = sc.nextDouble();
        if(sc.hasNextDouble()) cargoVolume = sc.nextDouble();
        if(sc.hasNextDouble()) cargoWeight = sc.nextDouble();
    }
    
        public String toString(){
        String st = "Cargo ship: " + super.toString();
        return st;
    }
}
