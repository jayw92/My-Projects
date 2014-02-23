
/**
 * ******************************************************************
 * NAME: Jiayuan Wang (Jay) EMAIL: jayw.92@gmail.com
 *
 * DESCRIPTION: This class extends the abstract class Ship.java. It is a
 * Mother ship type. These are medium speed and cost and can use
 * helppackages to aid friendly ships it crosses by.
 * ******************************************************************
 */
import java.util.Random;

public class Mothership extends Ship {

   // CLASS PRIVATE VARIABLES
   private int helppackage;

   // CONSTRUCTOR
   public Mothership(int posX, int posY, Player owner) {
      super(posX, posY, 4000, 4000, 500, 16, 2, 92, 180, owner, 4000);
      helppackage = 50000;
   }

   // CLASS SPECIAL MOVES
   private String giveHelpPackage(Ship s) {
      int need = s.needHelp();
      int sendHelp = 0;
      if (helppackage <= need) {
         sendHelp = helppackage;
      } else {
         sendHelp = need;
      }
      helppackage = helppackage - sendHelp;
      s.getHelp(sendHelp);

      return "\n" + getOwnerName().toUpperCase() + "'s ship (ID: " + getID() + ")"
              + "\nShip uses HELP PACKAGES to \nheal friendly ship's shields."
              + "\nID of ship helped is " + s.getID() + "."
              + "\nHELP PACKAGES sent to ally: " + sendHelp + ".";
   }

   // OVERRIDES FUNCTIONS OF ABSTRACT CLASS
   public String getDescription() {
      String desc = getDesc() + "\nHelp Packages: " + helppackage
              + "\nIt can use HELP PACKAGES to \nrestore shields of friendly ships.";
      return desc;
   }

   public String encounter(Ship s, boolean first) {
      String details = "[------SHIP ENCOUNTER LOG------]";
      if (s.getOwnerID() == getOwnerID()) {
         details = details + giveHelpPackage(s);
      } else {
         if (first) {
            details = details + fight(s, "");
         } else {
            Random gen = new Random();
            int i = gen.nextInt(50);
            if (i < 25) {
               details = encounter(s, true);
            } else {
               details = s.encounter(this, true);
            }
         }
      }
      return details;
   }

   public String getShipAsString() {
      String shipString = getGeneralShipAsString();
      shipString = shipString + helppackage;
      return shipString;
   }
   
   public void setShipFromString(String s){
      String[] vars = setGeneralShipFromString(s);
      helppackage = Integer.parseInt(vars[11]);
   }
}
