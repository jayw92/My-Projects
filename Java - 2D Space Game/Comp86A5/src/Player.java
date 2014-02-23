
/**
 * ******************************************************************
 * NAME: Jiayuan Wang (Jay) EMAIL: jayw.92@gmail.com
 *
 * DESCRIPTION: Player.java is the object class that defines a Player object
 * that is a part of the game. Players have ships and planets. AI moves are
 * defined and may not be fully in depth.
 * ******************************************************************
 */
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

public class Player {

   private int ID;
   private String name;
   private Color mainColor;
   private long wealth;
   private long income;
   private ArrayList<Ship> ships;
   private ArrayList<Planet> planets;
   private int yearCounter;

   public Player(String name, long wealth) {
      this.name = name;
      this.wealth = wealth;
      Random gen = new Random();
      ID = 1000000 + gen.nextInt(8999999);
      ships = new ArrayList<>();
      planets = new ArrayList<>();
      int r = gen.nextInt(256);
      int g = gen.nextInt(256);
      int b = gen.nextInt(256);
      mainColor = new Color(r, g, b);
      income = 500;
      yearCounter = 600;
   }

   public Player(String name, Color mainColor, long wealth) {
      this.name = name;
      this.mainColor = mainColor;
      this.wealth = wealth;
      Random gen = new Random();
      ID = 1000000 + gen.nextInt(8999999);
      ships = new ArrayList<>();
      planets = new ArrayList<>();
      income = 500;
      yearCounter = 600;
   }

   // PLAYER INTO STRING
   public String getPlayerAsString() {
      String playerString = "";
      playerString = playerString + ID + "&";
      playerString = playerString + name + "&";
      playerString = playerString + mainColor.getRed() + "&";
      playerString = playerString + mainColor.getGreen() + "&";
      playerString = playerString + mainColor.getBlue() + "&";
      playerString = playerString + wealth + "&";
      playerString = playerString + yearCounter + "&";
      playerString = playerString + "SHIPS";
      for (Ship s : ships) {
         playerString = playerString + "|" + s.getType() + "@" + s.getShipAsString();
      }
      return playerString;
   }

   // STRING INTO PLAYER
   public void setPlayertFromString(String s) {
      String[] vars = s.split("&");
      ID = Integer.parseInt(vars[0]);
      name = vars[1];
      mainColor = new Color(Integer.parseInt(vars[2]), Integer.parseInt(vars[3]), Integer.parseInt(vars[4]));
      wealth = Long.parseLong(vars[5]);
      yearCounter = Integer.parseInt(vars[6]);

      String[] shipvars = vars[7].split("\\|");
      ships = new ArrayList<Ship>();
      for (int x = 1; x < shipvars.length; x++) {
         String[] shipdata = shipvars[x].split("@");
         int type = Integer.parseInt(shipdata[0]);
         Ship ship = null;
         if (type == 0) {
            ship = new Warship(0, 0, this);
         } else if (type == 1) {
            ship = new Fightership(0, 0, this);
         } else if (type == 2) {
            ship = new Mothership(0, 0, this);
         }
         ship.setShipFromString(shipdata[1]);
         ships.add(ship);
      }

   }

   public int getID() {
      return ID;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public Color getMainColor() {
      return mainColor;
   }

   public void setMainColor(Color mainColor) {
      this.mainColor = mainColor;
   }

   public long getWealth() {
      return wealth;
   }

   public void setWealth(long wealth) {
      this.wealth = wealth;
   }

   public ArrayList<Ship> getShips() {
      return ships;
   }

   public ArrayList<Planet> getPlanets() {
      return planets;
   }

   public long getIncome() {
      return income;
   }

   public int getYearCounter() {
      return yearCounter;
   }

   public void advanceTime() {
      yearCounter--;
      for (Ship s : ships) {
         s.moveShip();
      }
      if (yearCounter == 0) {
         wealth = wealth + income;
         yearCounter = 600;
      }
   }

   public void AImove() {
      Random gen = new Random();
      for (Ship s : ships) {
         if (s.getSpeed() == 0) {
            int x = gen.nextInt(7000) - 3500;
            int y = gen.nextInt(7000) - 3500;
            s.setDestination(new Point(x, y));;
         }
      }
      if (wealth > 6000) {
         int x = gen.nextInt(7000) - 3500;
         int y = gen.nextInt(7000) - 3500;
         buyShip(new Mothership(x, y, this));
      }
   }

   public void addPlanet(Planet planet, Ship s) {
      ((Warship) s).collectPlanet();
      planet.setOwner(this);
      planets.add(planet);
      income = income + planet.getIncome();
   }

   public void addPlanet(Planet planet) {
      planet.setOwner(this);
      planets.add(planet);
      income = income + planet.getIncome();
   }

   public void addShip(Ship s) {
      s.setOwner(this);
      ships.add(s);
   }

   public void buyShip(Ship s) {
      if (wealth >= s.getCost()) {
         wealth = wealth - s.getCost();
         s.setOwner(this);
         ships.add(s);
      }
   }

   public Planet releasePlanet(int id) {
      for (int x = 0; x < planets.size(); x++) {
         if (planets.get(x).getID() == id) {
            Planet p = planets.get(x);
            income = income - p.getIncome();
            p.setOwner(null);
            planets.remove(x);
            return p;
         }
      }
      return null;
   }

   public Ship releaseShip(int id) {
      for (int x = 0; x < ships.size(); x++) {
         if (ships.get(x).getID() == id) {
            Ship s = ships.get(x);
            s.desttroy();
            ships.remove(x);
            return null;
         }
      }
      return null;
   }

   public void checkDestroyedShip() {
      for (int x = 0; x < ships.size(); x++) {
         if (ships.get(x).isDestroyed()) {
            ships.remove(x);
            x--;
         }
      }
   }

   public void drawShips(Graphics g) {
      checkDestroyedShip();
      for (Ship s : ships) {
         s.drawShip(g);
      }
   }

   public void setZoom(int zoom) {
      for (Ship s : ships) {
         s.setZoomFactor(zoom);
      }
   }

   public void setOffset(Point offset) {
      for (Ship s : ships) {
         s.setOffset(offset);
      }
   }

   public Ship checkLocationOnShips(Point p) {
      for (Ship s : ships) {
         if (s.checkCoordinates(p)) {
            return s;
         }
      }
      return null;
   }

   public String getDesc() {
      return "Name:  " + name
              + "\nID:  " + ID
              + "\nWealth:  Ω " + wealth
              + "\nIncome:  Ω " + income
              + "\nFleet Size:  " + ships.size()
              + "\nPlanets:  " + planets.size();
   }

}
