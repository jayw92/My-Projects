
/**
 * ******************************************************************
 * NAME: Jiayuan Wang (Jay) EMAIL: jayw.92@gmail.com
 *
 * DESCRIPTION: This class extends the abstract class Ship.java. It is a
 * fighter ship type. This is the fast attack ship that allows for it to use
 * MISSILES on other fighters.
 * ******************************************************************
 */
import java.util.Random;

public class Fightership extends Ship {

   // CLASS PRIVATE VARIABLES
   private int missiles;

   // CONSTRUCTOR
   public Fightership(int posX, int posY, Player owner) {
      super(posX, posY, 2000, 2000, 1000, 20, 1, 70, 62, owner, 1000);
      missiles = 2;
   }

   // CLASS SPECIAL MOVES
   private String launchMissileAt(Ship s) {
      String prevOwner = s.getOwnerName().toUpperCase();
      String id = "" + s.getID();
      s.desttroy();
      missiles--;

      return "\n" + getOwnerName().toUpperCase() + "'s ship (ID: " + getID() + ")"
              + "\nShip was quick to respond. \nIt used MISSLES to take over enemy ship."
              + "\nID of ship destroyed is " + id + "."
              + "\nShip destroyed belonged to " + prevOwner + ".";
   }

   // OVERRIDES FUNCTIONS OF ABSTRACT CLASS
   public String getDescription() {
      String desc = getDesc() + "\nMissles: " + missiles
              + "\nThis is a FIGHTER and can use \nMISSLES to destroy other FIGHTERS.";
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
            if (missiles > 0 && s.getType() == 1) {
               details = details + launchMissileAt(s);
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
      shipString = shipString + missiles;
      return shipString;
   }
   
   public void setShipFromString(String s){
      String[] vars = setGeneralShipFromString(s);
      missiles = Integer.parseInt(vars[11]);
   }
}
