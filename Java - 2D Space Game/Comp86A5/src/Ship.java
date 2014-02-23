
/**
 * ******************************************************************
 * NAME: Jiayuan Wang (Jay) EMAIL: jayw.92@gmail.com
 *
 * DESCRIPTION: Ship.java is the abstract Ship class that defines an abstract
 * ship object. Ship objects can be made by extending this class.
 * ******************************************************************
 */
import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import java.awt.GraphicsConfiguration;
import java.util.ArrayList;

public abstract class Ship {

   // PRIVATE VARIABLES COMMON TO EVERY SHIP CLASS
   private int posX, posY;
   private double direction;
   private int health;
   private int shield;
   private int attack;
   private int speed;
   private int maxSpeed;
   private int ID;
   private int type;
   private int height, width;
   private int radius;
   private int cost;
   private boolean isVisable;
   private Shape zone, highlighter;
   private boolean isSelected;
   private int zoomFactor;
   private Point offset;
   private Point shipCenter;
   private Point destination;
   private int ownerID;
   private String ownerName;
   private Color ownerColor;
   private boolean destroyed;
   private final String[] typeStrings = {"Warship", "Fightership", "Mothership"};
   private final int[] detectionRadius = {160, 80, 40};
   private BufferedImage shipImage;
   private String logs;
   private final Color lightTeal180 = new Color(50, 170, 170, 180);

   // BASIC SHIP CONSTRUCTOR
   public Ship() {

   }

   public Ship(int posX, int posY, int health, int shield, int attack,
           int maxSpeed, int type, int height, int width, Player owner, int cost) {
      this.posX = posX;
      this.posY = posY;
      this.health = health;
      this.shield = shield;
      this.attack = attack;
      this.maxSpeed = maxSpeed;
      this.type = type;
      this.height = height;
      this.width = width;
      this.ownerID = owner.getID();
      this.ownerName = owner.getName();
      this.ownerColor = owner.getMainColor();
      this.cost = cost;
      this.destroyed = false;

      Random gen = new Random();
      ID = 1000000 + gen.nextInt(8999999);
      int x = gen.nextInt(12000) - 6000;
      int y = gen.nextInt(12000) - 6000;
      destination = new Point(x, y);
      shipCenter = new Point(posX, posY);
      direction = getRadians(shipCenter, destination);
      speed = gen.nextInt(maxSpeed);
      isVisable = true;
      isSelected = false;
      offset = new Point(0, 0);
      radius = (int) Math.sqrt(width * width + height * height) / 2;
      zone = zoneAt(shipCenter, radius + detectionRadius[type]);
      highlighter = zoneAt(shipCenter, radius);
      try {
         String path = "/sprites/ship" + type + ".png";
         shipImage = ImageIO.read(getClass().getResourceAsStream(path));
      } catch (IOException ex) {
         Logger.getLogger(Ship.class.getName()).log(Level.SEVERE, null, ex);
      }
      logs = "[------------START OF LOG------------]\n\n" + getDesc();
   }

   // GET & SET METHODS OF THE CLASS
   public int needHelp() {
      return health - shield;
   }

   public void getHelp(int x) {
      shield = shield + x;
   }

   public int getCost() {
      return cost;
   }

   public int getSpeed() {
      return speed;
   }

   public void setSpeed(int speed) {
      this.speed = speed;
   }

   public int getType() {
      return type;
   }

   public int getID() {
      return ID;
   }

   public int getMaxSpeed() {
      return maxSpeed;
   }

   public void setMaxSpeed(int maxSpeed) {
      this.maxSpeed = maxSpeed;
   }

   public boolean isIsVisable() {
      return isVisable;
   }

   public void setIsVisable(boolean isVisable) {
      this.isVisable = isVisable;
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

   public Point getDestination() {
      return destination;
   }

   public void setDestination(Point destination) {
      this.destination = destination;
   }

   public int getOwnerID() {
      return ownerID;
   }

   public String getOwnerName() {
      return ownerName;
   }

   public void setOwner(Player owner) {
      this.ownerColor = owner.getMainColor();
      this.ownerID = owner.getID();
      this.ownerName = owner.getName();
   }

   public Point getShipCenter() {
      return shipCenter;
   }

   public String getLogs() {
      return logs;
   }

   public Shape getZone() {
      return zone;
   }

   public String getDesc() {
      String desc = "[Ship Details] \tID: " + ID
              + "\nLocated: (" + posX + "," + posY + ")"
              + "\nDestination: (" + destination.x + "," + destination.y + ")"
              + "\nType: " + typeStrings[type] + " (" + type + ")"
              + "\nMax Speed: " + maxSpeed
              + "\nHealth: " + health
              + "\nShields: " + shield
              + "\nAttack: " + attack
              + "\nOwner: " + ownerName + " (ID: " + ownerID + ")";
      return desc;
   }

   // GET DEGREES BETWEEN 2 POINTS
   private double getRadians(Point A, Point B) {
      int xDiff = B.x - A.x;
      int yDiff = B.y - A.y;
      return Math.atan2(yDiff, xDiff);
   }

   // MAKE A CIRCLE WITH CENTER AT c AND RADIUS r
   private Shape zoneAt(Point c, int r) {
      return (new Ellipse2D.Float(c.x - r, c.y - r, r * 2, r * 2));
   }

   // MOVES SHIP ACCORDING TO ITS SPEED AND DIRECTION
   public void moveShip() {
      if (zoneAt(destination, 20).contains(shipCenter)) {
         speed = 0;
      } else {
         speed = maxSpeed;
         direction = getRadians(shipCenter, destination);
         int xMove = (int) (speed * Math.cos(direction));
         int yMove = (int) (speed * Math.sin(direction));
         posX = posX + xMove;
         posY = posY + yMove;
         shipCenter = new Point(posX, posY);
      }
   }

   // GET DRAWING CORNER X, Y, WIDTH, AND HEIGHT VALUES
   public int getDrawX() {
      return (int) (posX / zoomFactor);
   }

   public int getDrawY() {
      return (int) (posY / zoomFactor);
   }

   public String fight(Ship s, String log) {
      String details = log;
      Random gen = new Random();
      int dmg = gen.nextInt(attack / 2) + attack / 4;
      s.damage(dmg);
      if (s.getHealth() <= 0) {
         String loser = s.getOwnerName();
         s.desttroy();
         details = details + "\nShip [" + s.getID() + "] received (" + dmg + ") amount of damage."
                 + "\nShip [" + s.getID() + "] was destroyed.\n["
                 + loser + "] lost the battle.";
         return details;
      } else {
         details = "\nShip [" + s.getID() + "] received (" + dmg + ") amount of damage." + s.fight(this, details);
         return details;
      }
   }

   public void desttroy() {
      ownerID = 0;
      ownerName = "NONE";
      destroyed = true;
   }

   public boolean isDestroyed() {
      return destroyed;
   }

   private void damage(int dmg) {
      shield = shield - dmg;
      if (shield < 0) {
         health = health + shield;
         shield = 0;
      }
   }

   private int getHealth() {
      return health;
   }

   public void setFullSpeed() {
      setSpeed(maxSpeed);
   }

   public void repairShip(int health, int shield) {
      this.health = health;
      this.shield = health;
   }

   // CHECK IF COORDINATES ARE INSIDE THE SHIP AREA
   public boolean checkCoordinates(Point loc) {
      return highlighter.contains(loc);
   }

   // CHECK IF SHIPS ARE INSIDE THE SHIP ZONE OF DETECTION
   public boolean checkZoneOfShip(Ship s) {
      if (checkIntersection(zone, s.zone)) {
         setSpeed(0);
         s.setSpeed(0);
         logs = encounter(s, false)
                 + "\n[------END OF SHIP ENCOUNTER------]\n";
         return true;
      }
      return false;
   }

   public Planet isNearPlanet(ArrayList<Planet> ps) {
      Planet p = null;
      for (Planet c : ps) {
         if (c.checkZoneShip(this)) {
            p = c;
         }
      }
      return p;
   }

   // RETURN TRUE IF SHAPE A INTERSECTS SHAPE B USING AREA
   private boolean checkIntersection(Shape shapeA, Shape shapeB) {
      Area areaA = new Area(shapeA);
      areaA.intersect(new Area(shapeB));
      return !areaA.isEmpty();
   }

   public void drawShip(Graphics g) {

      Graphics2D g2d = (Graphics2D) g;
      shipCenter = new Point(posX, posY);
      zone = zoneAt(shipCenter, radius + detectionRadius[type]);
      int r = radius / zoomFactor;
      int r2 = (radius + detectionRadius[type]) / zoomFactor;
      Point shipDrawCenter = new Point(getDrawX() + offset.x, getDrawY() + offset.y);

      highlighter = zoneAt(shipDrawCenter, r);
      Color shipColor = new Color(ownerColor.getRed(), ownerColor.getGreen(), ownerColor.getBlue(), 200);

      if (isVisable) {
         BufferedImage transImg = getTransparentImage(shipImage, Color.white);
         BufferedImage rotatedImg = rotateMyImage(transImg, direction);
         int w = rotatedImg.getWidth() / zoomFactor;
         int h = rotatedImg.getHeight() / zoomFactor;
         int x = (getDrawX() - w / 2 + offset.x);
         int y = (getDrawY() - h / 2 + offset.y);
         g2d.setColor(shipColor);
         g2d.fill(zoneAt(shipDrawCenter, r2));
         g2d.drawImage(rotatedImg,
                 x, y, x + w, y + h,
                 0, 0, rotatedImg.getWidth(), rotatedImg.getHeight(), null);
         if (isSelected) {
            g2d.setColor(lightTeal180);
            g2d.draw(highlighter);
         }
      }

   }

   // ROTATE SHIP IMAGE
   public static BufferedImage rotateMyImage(BufferedImage image, double angle) {
      double sin = Math.abs(Math.sin(angle)), cos = Math.abs(Math.cos(angle));
      int w = image.getWidth(), h = image.getHeight();
      int neww = (int) Math.floor(w * cos + h * sin), newh = (int) Math.floor(h * cos + w * sin);
      GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
      GraphicsDevice gs = ge.getDefaultScreenDevice();
      GraphicsConfiguration gc = gs.getDefaultConfiguration();
      BufferedImage result = gc.createCompatibleImage(neww, newh, Transparency.TRANSLUCENT);
      Graphics2D g = result.createGraphics();
      g.translate((neww - w) / 2, (newh - h) / 2);
      g.rotate(angle, w / 2, h / 2);
      g.drawRenderedImage(image, null);
      g.dispose();
      return result;
   }

   // RETURN A TRANSPARENT IMAGE
   public static BufferedImage getTransparentImage(
           BufferedImage image, Color transparent) {
      // must have a transparent image
      BufferedImage img = new BufferedImage(
              image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
      Graphics2D g = img.createGraphics();
      for (int x = 0; x < img.getWidth(); x++) {
         for (int y = 0; y < img.getHeight(); y++) {
            if (image.getRGB(x, y) != transparent.getRGB()) {
               img.setRGB(x, y, image.getRGB(x, y));
            }
         }
      }
      g.dispose();
      return img;
   }

   public String getGeneralShipAsString() {
      String shipStrin = "";
      shipStrin = shipStrin + posX + "%";
      shipStrin = shipStrin + posY + "%";
      shipStrin = shipStrin + destination.x + "%";
      shipStrin = shipStrin + destination.y + "%";
      shipStrin = shipStrin + health + "%";
      shipStrin = shipStrin + shield + "%";
      shipStrin = shipStrin + speed + "%";
      shipStrin = shipStrin + ID + "%";
      shipStrin = shipStrin + type + "%";
      shipStrin = shipStrin + ownerID + "%";
      shipStrin = shipStrin + ownerName + "%";
      return shipStrin;
   }
   
   public String[] setGeneralShipFromString(String s){
      String[] vars = s.split("%");
      posX = Integer.parseInt(vars[0]);
      posY = Integer.parseInt(vars[1]);
      destination.x = Integer.parseInt(vars[2]);
      destination.y = Integer.parseInt(vars[3]);
      health = Integer.parseInt(vars[4]);
      shield = Integer.parseInt(vars[5]);
      speed = Integer.parseInt(vars[6]);
      ID = Integer.parseInt(vars[7]);
      type = Integer.parseInt(vars[8]);
      ownerID = Integer.parseInt(vars[9]);
      ownerName = vars[10];
      shipCenter = new Point(posX, posY);
      direction = getRadians(shipCenter, destination);
      zone = zoneAt(shipCenter, radius + detectionRadius[type]);
      highlighter = zoneAt(shipCenter, radius);
      try {
         String path = "/sprites/ship" + type + ".png";
         shipImage = ImageIO.read(getClass().getResourceAsStream(path));
      } catch (IOException ex) {
         Logger.getLogger(Ship.class.getName()).log(Level.SEVERE, null, ex);
      }
      return vars;
   }

   // ABSTRACT METHODS TO BE OVERRIDDEN IN SUBCLASSES
   abstract public String getDescription();

   abstract public String getShipAsString();
   
   abstract public void setShipFromString(String s);

   abstract public String encounter(Ship s, boolean first);

}
