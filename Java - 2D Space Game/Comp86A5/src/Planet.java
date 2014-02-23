
/**
 * ******************************************************************
 * NAME: Jiayuan Wang (Jay) EMAIL: jayw.92@gmail.com
 *
 * DESCRIPTION: Planet.java is the object class that defines a planet object
 * that is static on the map. They can't be destroyed by ships or move.
 * The player can take over a planet to gain more income. Colonizing
 * all planets will win the game for the player.
 * ******************************************************************
 */
import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class Planet {

   private int posX, posY;
   private int diameter;
   private int ID;
   private int temperature;
   private int income;
   private int type;
   private int size;
   private String desc;
   private String name;
   private Shape highlighter;
   private Shape zone;
   private boolean isSelected;
   private int zoomFactor;
   private Point offset;
   private Point planetCenter;
   private Image planets;
   private int health;
   private int ownerID;
   private String ownerName;
   private final String[] typeStrings = {"OCEANIC", "NIMBIC", "TERRAN",
      "DUST", "MINERAL", "STORM", "GREENHORN", "AURIC", "EMRALD", "ICE",
      "FJORD", "ACID", "GAS GIANT", "PELLUCID", "OOLITE", "MAGNETIC", "CRIMSON",
      "MAGMA", "METALLIC"};
   private final String[] sizeStrings = {"SMALL", "MEDIUM", "GIANT"};
   private final Color lightBlue180 = new Color(50, 100, 200, 180);

   // BASIC PLANET CONSTRUCTOR
   public Planet(int x, int y) {
      planets = null;

      Random gen = new Random();
      posX = x;
      posY = y;
      diameter = gen.nextInt(200) + 300;
      ID = 1000000 + gen.nextInt(8999999);
      temperature = gen.nextInt(75) - 45;
      income = 1000 + gen.nextInt(2000);
      type = gen.nextInt(18);
      if (diameter < 370) {
         size = 0;
      } else if (diameter >= 370 && diameter < 450) {
         size = 1;
      } else {
         size = 2;
      }
      name = "No Name";
      planetCenter = new Point(posX, posY);
      zone = zoneAt(planetCenter, diameter / 2);
      highlighter = zoneAt(planetCenter, diameter / 2);
      isSelected = false;
      zoomFactor = 1;
      offset = new Point(0, 0);
      try {
         String path = "/sprites/planet" + type + ".png";
         planets = ImageIO.read(getClass().getResourceAsStream(path));
      } catch (IOException ex) {
         Logger.getLogger(Planet.class.getName()).log(Level.SEVERE, null, ex);
      }
      health = 100000;
      ownerID = 0;
      ownerName = "NONE";
   }

   // PLANET INTO STRING
   public String getPlanetAsString() {
      String planetString = "";
      planetString = planetString + posX + "&";
      planetString = planetString + posY + "&";
      planetString = planetString + diameter + "&";
      planetString = planetString + ID + "&";
      planetString = planetString + temperature + "&";
      planetString = planetString + income + "&";
      planetString = planetString + type + "&";
      planetString = planetString + size + "&";
      planetString = planetString + name + "&";
      planetString = planetString + ownerID + "&";
      planetString = planetString + ownerName;
      return planetString;
   }
   
   // STRING INTO PLANET
   public void setPlanetFromString(String s){
      String[] vars = s.split("&");
      posX = Integer.parseInt(vars[0]);
      posY =  Integer.parseInt(vars[1]);
      diameter =  Integer.parseInt(vars[2]);
      ID =  Integer.parseInt(vars[3]);
      temperature =  Integer.parseInt(vars[4]);
      income =  Integer.parseInt(vars[5]);
      type =  Integer.parseInt(vars[6]);
      size =  Integer.parseInt(vars[7]);
      name = vars[8];
      ownerID =  Integer.parseInt(vars[9]);
      ownerName = vars[10];
      planetCenter = new Point(posX, posY);
      zone = zoneAt(planetCenter, diameter / 2);
      highlighter = zoneAt(planetCenter, diameter / 2);
      isSelected = false;
      try {
         String path = "/sprites/planet" + type + ".png";
         planets = ImageIO.read(getClass().getResourceAsStream(path));
      } catch (IOException ex) {
         Logger.getLogger(Planet.class.getName()).log(Level.SEVERE, null, ex);
      }
      health = 100000;
   }

   // MAKE A CIRCLE WITH CENTER AT c AND RADIUS r
   private Shape zoneAt(Point c, int r) {
      return (new Ellipse2D.Float(c.x - r, c.y - r, r * 2, r * 2));
   }

   // GET & SET METHODS OF THE CLASS
   public int getID() {
      return ID;
   }

   public int getHealth() {
      return health;
   }

   public void setHealth(int health) {
      this.health = health;
   }

   public int getOwnerID() {
      return ownerID;
   }

   public String getOwnerName() {
      return ownerName;
   }

   public void setOwner(Player owner) {
      ownerID = owner.getID();
      ownerName = owner.getName();
   }

   public Point getPlanetCenter() {
      return planetCenter;
   }

   public Shape getHighlighter() {
      return highlighter;
   }

   public int getIncome() {
      return income;
   }

   public String getDescription() {
      desc = "\n[Planet Details]"
              + "\nLocated: (" + posX + " , " + posY + ")"
              + "\nName: " + name + " (ID: " + ID + ")"
              + "\nType: " + typeStrings[type] + " (" + type + ")"
              + "\nSize: " + sizeStrings[size] + " (D = " + diameter + ")"
              + "\nTemp: " + temperature + " °C"
              + "\nIncome: Ω " + income;
      if (ownerID != 0) {
         desc = desc + "\nOwner: " + ownerName + " (ID: " + ownerID + ")";
      }
      return desc;
   }

   public void setDesc(String desc) {
      this.desc = desc;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public boolean isIsSelected() {
      return isSelected;
   }

   public void setIsSelected(boolean isSelected) {
      this.isSelected = isSelected;
   }

   public int getZoomFactor() {
      return zoomFactor;
   }

   public void setZoomFactor(int zoomFactor) {
      this.zoomFactor = zoomFactor;
   }

   public Point getOffset() {
      return offset;
   }

   public void setOffset(Point offset) {
      this.offset = offset;
   }

   public Shape getZone() {
      return zone;
   }

   // GET DRAW COORDINATES
   public int getDrawX() {
      return (int) (posX / zoomFactor);
   }

   public int getDrawY() {
      return (int) (posY / zoomFactor);
   }

   public void drawPlanet(Graphics g) {
      int d = diameter / zoomFactor;
      int x = (getDrawX() - d / 2 + offset.x);
      int y = (getDrawY() - d / 2 + offset.y);
      Point planetCenter2 = new Point(x + d / 2, y + d / 2);

      Graphics2D g2d = (Graphics2D) g;
      g2d.drawImage(planets, x, y, x + d, y + d,
              0, 0, 300, 300, null);
      highlighter = zoneAt(planetCenter2, d / 2);
      if (isSelected) {
         g2d.setColor(lightBlue180);
         g2d.draw(highlighter);
      }
      if (ownerID != 0) {
         g2d.setColor(Color.ORANGE);
         g2d.fill(zoneAt(planetCenter2, 40/zoomFactor));
      }
   }

   // CHECK IF COORDINATES ARE INSIDE THE SHAPES
   public boolean checkCoordinates(Point loc) {
      return highlighter.contains(loc);
   }

   // CHECK IF OBJECT IS COLLIDING WITH THIS PLANET
   public boolean checkZonePlanet(Planet p) {
      return checkZones(p.getZone(), zone);
   }

   public boolean checkZoneShip(Ship s) {
      return zone.contains(s.getShipCenter());
   }

   // RETURN TRUE IF SHAPE A INTERSECTS SHAPE B USING AREA
   private boolean checkZones(Shape shapeA, Shape shapeB) {
      Area areaA = new Area(shapeA);
      areaA.intersect(new Area(shapeB));
      return !areaA.isEmpty();
   }

}
