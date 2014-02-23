
/**
 * ******************************************************************
 * NAME: Jiayuan Wang (Jay) EMAIL: jayw.92@gmail.com
 *
 * DESCRIPTION: This class extends the abstract class Ship.java. It is a WarShip
 * and holds 2 colonize planets and 4 nukes to destroy smaller ships with.
 * ******************************************************************
 */
import java.util.Random;

public class Warship extends Ship {

   // CLASS PRIVATE VARIABLES
   private int colonize;
   private int nukes;

   // CONSTRUCTOR
   public Warship(int posX, int posY, Player owner) {
      super(posX, posY, 30000, 30000, 18000, 12, 0, 150, 323, owner, 20000);
      colonize = 2;
      nukes = 4;
   }

   // CLASS SPECIAL MOVES
   public int getColonize() {
      return colonize;
   }

   public void collectPlanet() {
      if (colonize > 0) {
         colonize--;
      }
   }

   private String nukeShip(Ship s) {
      String prevOwner = s.getOwnerName().toUpperCase();
      String id = "" + s.getID();
      s.desttroy();
      nukes--;

      return "\n" + getOwnerName().toUpperCase() + "'s ship (ID: " + getID() + ")"
              + "\nShip was quick to respond.\nIt used NUKES to destroy the enemy ship."
              + "\nID of ship nuked is " + id + "."
              + "\nShip nuked belonged to " + prevOwner + ".";
   }

   // OVERRIDES FUNCTIONS OF ABSTRACT CLASS

   public String getDescription() {
      String desc = getDesc()
              + "\nNukes Stored: " + nukes
              + "\nColonize Power: " + colonize
              + "\nThis is a WARSHIP, thus it can use \nCOLONIZE to claim PLANETS."
              + "\nIt can also use NUKES to instanly \ndestry non-WARSHIP ships.";
      return desc;
   }

   public String encounter(Ship s, boolean first) {
      String details = "[------SHIP ENCOUNTER LOG------]";
      if (s.getOwnerID() == getOwnerID()) {
         s.setFullSpeed();
         setFullSpeed();
         return "";
      } else {
         if (first) {
            if (nukes > 0 && s.getType() != 0) {
               details = details + nukeShip(s);
            } else {
               details = details + fight(s, "");
            }
         } else {
            Random gen = new Random();
            int i = gen.nextInt(50);
            if (i < 25) {
               details = encounter(s, true);
            } else {
               details = s.encounter(this, true);
            }
         }
         return details;
      }
   }
   
   
   public String getShipAsString() {
      String shipString = getGeneralShipAsString();
      shipString = shipString + colonize + "%";
      shipString = shipString + nukes;
      return shipString;
   }
   
   public void setShipFromString(String s){
      String[] vars = setGeneralShipFromString(s);
      colonize = Integer.parseInt(vars[11]);
      nukes = Integer.parseInt(vars[12]);
   }
}
