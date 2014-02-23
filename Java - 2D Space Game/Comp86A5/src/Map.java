
/**
 * ******************************************************************
 * NAME: Jiayuan Wang (Jay) EMAIL: jayw.92@gmail.com
 *
 * DESCRIPTION: Map.java contains the map for the ships to be drawn on. Map also
 * includes a clock for timing function calls and animating the drawing canvas.
 * Also includes functions needed to modify the game and draw functions for the
 * game as well.
 *
 * ******************************************************************
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.FontMetrics;
import java.awt.Polygon;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Line2D;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ConcurrentModificationException;
import java.util.Random;
import java.util.Scanner;
import javax.imageio.ImageIO;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Map extends JComponent implements MouseListener {

   // ARRAYS AND OBJECTS
   private ArrayList<Player> enemies;
   private Player player;
   private ArrayList<Planet> Planets;
   // SELECTED ITEMS
   private Ship selectedShip;
   private Planet selectedPlanet;
   // TIMER OBJECT VARS
   private Timer clock;
   int interval;
   private boolean finishedTask;
   // MAP AREA
   private Rectangle mapViewport;
   private String info;
   private Point offset;
   private int offsetInt;
   private Line2D lineoftravel;
   private int zoomFactor;
   private boolean displayMenu;
   private boolean startofgame;
   private boolean settingsNotShown;
   //MENU ITEMS
   private Polygon playOption, instructOption, resetGameB;
   private Polygon buyshipB, deleteshipB, takeplanetB, setKeysB, saveB, loadB;
   // IMAGES
   Image bgImg;
   // PRESET COLORS
   private final Color lightBlue180 = new Color(50, 100, 200, 150);
   private final Color lightBlueComp = new Color(200, 150, 50);
   private final Color lightTeal = new Color(50, 170, 170);
   private final Color darkBlue200 = new Color(10, 30, 100, 200);
   private final Color green220 = new Color(20, 100, 20, 220);
   // FRAMES
   private SettingsFrame settingsFrame;
   private BuyShipFrame buyframe;
   private SetKeysFrame setkeyframe;
   // SETTING VARS
   private Color playerColor;
   private String playerName;
   private long startingWealth;
   private int numPlanets;
   private int numEnemies;
   private int speed;
   private int[] keyCodes;
   private final String[] keyActions = {"Deselect Object", "Buy Ship", "Delete Ship",
      "Colonize Planet", "Move Map Left", "Move Map Up", "Move Map Right", "Move Map Down",
      "Speed Up", "Slow Down", "Pause/Go", "Zoom Out", "Zoom In"};
   private final FileNameExtensionFilter gamefileFilter = new FileNameExtensionFilter("SPACE GAME SAVES", "SGS", "Save");
   private boolean win;

   public Map() {

      // MOUSE LISTENER
      addMouseListener(this);

      // ADDS LISTENER FOR WINDOW RESIZING
      this.addComponentListener(new ComponentAdapter() {

         @Override
         public void componentResized(ComponentEvent e) {
            repaint();
         }
      });

      initVars();
   }

   // SAVE CURRENT STATE TO FILE
   private void saveMapToFile() {
      String mapString = saveMap();
      JFileChooser saveFileChooser = new JFileChooser();

      saveFileChooser.setCurrentDirectory(new java.io.File("."));
      saveFileChooser.setFileFilter(gamefileFilter);
      saveFileChooser.setApproveButtonText("Save Game");
      saveFileChooser.setApproveButtonToolTipText("Save the current state of the game.");
      saveFileChooser.setDialogTitle("Save Game State");
      if (saveFileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
         File fileToBeSaved = saveFileChooser.getSelectedFile();

         if (!saveFileChooser.getSelectedFile().getAbsolutePath().endsWith(".SGS")) {
            fileToBeSaved = new File(saveFileChooser.getSelectedFile() + ".SGS");
         }
         try {
            //write converted json data to a file named "file.json"
            FileWriter writer = new FileWriter(fileToBeSaved);
            writer.write(mapString);
            writer.close();

         } catch (IOException e) {
            e.printStackTrace();
         }
      }

   }

   // LOAD THE GAME AS A STATE
   private void loadMapFromFile() {

      JFileChooser loadFileChooser = new JFileChooser();

      loadFileChooser.setDialogType(javax.swing.JFileChooser.OPEN_DIALOG);
      loadFileChooser.setApproveButtonText("Load Game");
      loadFileChooser.setApproveButtonToolTipText("Load the saved game state from file.");
      loadFileChooser.setDialogTitle("Load Game");
      loadFileChooser.setCurrentDirectory(new java.io.File("."));
      loadFileChooser.setFileFilter(gamefileFilter);

      if (loadFileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
         try {
            String filecontents = new Scanner(loadFileChooser.getSelectedFile()).useDelimiter("\\A").next();
            loadFile(filecontents);

         } catch (IOException e) {
            e.printStackTrace();
         }
      }
   }

   // SAVE FUNCTION
   public String saveMap() {
      String saveContents = "MAP VARS$";
      saveContents = saveContents + zoomFactor + "$";
      saveContents = saveContents + offsetInt + "$";
      saveContents = saveContents + speed + "$";
      saveContents = saveContents + info + "$";
      saveContents = saveContents + offset.x + "$";
      saveContents = saveContents + offset.y + "$";
      saveContents = saveContents + displayMenu + "$";
      saveContents = saveContents + startofgame + "$";
      saveContents = saveContents + settingsNotShown + "$";
      saveContents = saveContents + startingWealth + "\n>";

      saveContents = saveContents + "MAP KEYS";
      for (int x = 0; x < keyCodes.length; x++) {
         saveContents = saveContents + "$" + keyCodes[x];
      }
      saveContents = saveContents + "\n>";

      saveContents = saveContents + "PLANETS";
      for (Planet p : Planets) {
         saveContents = saveContents + "$" + p.getPlanetAsString();
      }
      saveContents = saveContents + "\n>";

      saveContents = saveContents + "ENEMIES";
      for (Player p : enemies) {
         saveContents = saveContents + "$" + p.getPlayerAsString();
      }
      saveContents = saveContents + "\n>";

      saveContents = saveContents + "PLAYER";
      saveContents = saveContents + "$" + player.getPlayerAsString();
      return saveContents;
   }

   // LOAD FUNCTION
   public void loadFile(String s) {
      String[] contents = s.replaceAll("\n", "").split(">");
      String[] mapvars = contents[0].split("\\$");
      zoomFactor = Integer.parseInt(mapvars[1]);
      offsetInt = Integer.parseInt(mapvars[2]);
      speed = Integer.parseInt(mapvars[3]);
      info = mapvars[4];
      offset = new Point(Integer.parseInt(mapvars[5]), Integer.parseInt(mapvars[6]));
      displayMenu = Boolean.parseBoolean(mapvars[7]);
      startofgame = Boolean.parseBoolean(mapvars[8]);
      settingsNotShown = Boolean.parseBoolean(mapvars[9]);
      startingWealth = Integer.parseInt(mapvars[10]);

      String[] mapkeys = contents[1].split("\\$");
      keyCodes = new int[mapkeys.length - 1];
      for (int x = 1; x < mapkeys.length; x++) {
         keyCodes[x - 1] = Integer.parseInt(mapkeys[x]);
      }

      String[] planetvars = contents[2].split("\\$");
      Planets = new ArrayList<Planet>();
      for (int x = 1; x < planetvars.length; x++) {
         Planet p = new Planet(0, 0);
         p.setPlanetFromString(planetvars[x]);
         Planets.add(p);
      }

      String[] enemyvars = contents[3].split("\\$");
      enemies = new ArrayList<Player>();
      for (int x = 1; x < enemyvars.length; x++) {
         Player p = new Player("", startingWealth);
         p.setPlayertFromString(enemyvars[x]);
         enemies.add(p);
      }

      String[] playervars = contents[4].split("\\$");
      player = new Player("", startingWealth);
      player.setPlayertFromString(playervars[1]);

      for (Planet p : Planets) {
         if (p.getOwnerID() == player.getID()) {
            player.addPlanet(p);
         }
         for (Player e : enemies) {
            if (p.getOwnerID() == e.getID()) {
               e.addPlanet(p);
            }
         }
      }

      zoomMap(zoomFactor);
      offsetMap();
   }

   // INIT VARIABLES ALSO USED FOR RESET
   private void initVars() {
      zoomFactor = 7;
      offsetInt = 50;
      speed = 1;
      info = "";
      selectedShip = null;
      selectedPlanet = null;
      lineoftravel = null;
      offset = new Point(0, 0);
      enemies = new ArrayList<>();
      Planets = new ArrayList<>();
      displayMenu = false;
      startofgame = true;
      settingsNotShown = true;
      int[] keys = {32, 90, 88, 67, 65, 87, 68, 83, 82, 70, 71, 81, 69};
      this.keyCodes = keys;
   }

   // SET OPTIONS FOR PLAYER
   private void setOptions(ArrayList data) {
      playerName = (String) data.get(0);
      playerColor = (Color) data.get(1);
      startingWealth = (int) data.get(2);
      numEnemies = (int) data.get(3);
      numPlanets = (int) data.get(4);
   }

   // CREATE PLANETS
   private void createPlanets() {
      for (int i = 0; i < numPlanets; i++) {
         Random gen = new Random();
         int range = 8000;
         int a = gen.nextInt(range) - range / 2;
         int b = gen.nextInt(range) - range / 2;
         Planet planet = new Planet(a, b);
         boolean overlap = false;
         for (int g = 0; g < Planets.size(); g++) {
            if (!overlap) {
               overlap = Planets.get(g).checkZonePlanet(planet);
            }
         }
         if (overlap) {
            i--;
         } else {
            Planets.add(planet);
         }
      }
   }

   // CREATE PLAYERS INCLUDING ENEMIES
   private void createPlayers() {
      player = new Player(playerName, playerColor, startingWealth);
      player.addShip(new Warship(0, 0, player));
      player.getShips().get(0).setDestination(new Point(0, 0));
      player.addShip(new Fightership(100, 0, player));
      player.getShips().get(0).setDestination(new Point(0, 0));
      for (int i = 0; i < numEnemies; i++) {
         Random gen = new Random();
         int range = 8000;
         int x = gen.nextInt(range / 2) + range / 2;
         int y = gen.nextInt(range / 2) + range / 2;
         Player p = new Player("Enemy # " + i, startingWealth);
         p.addShip(new Fightership(x, y, player));
         p.getShips().get(0).setDestination(new Point(0, 0));
         p.addShip(new Warship(x, y, player));
         p.getShips().get(0).setDestination(new Point(0, 0));
         enemies.add(p);
      }
   }

   // CLOCK & MAP PLAYBACK
   public void startClock() {
      finishedTask = false;
      clock = new Timer();
      TimerTask animate = new TimerTask() {

         @Override
         public void run() {
            if (finishedTask == true) {
               clock.cancel();
            } else {
               player.advanceTime();
               for (Player e : enemies) {
                  e.AImove();
                  e.advanceTime();
                  e.setZoom(zoomFactor);
                  e.setOffset(offset);
               }
               checkZones();
               checkWin();
               repaint();
            }
         }
      };
      interval = 1000 / (speed * 10);
      clock.schedule(animate, 0, interval);
   }

   // STOP CURRENT CLOCK
   public void stopClock() {
      finishedTask = true;
   }

   // SPEED UP
   private void speedUp() {
      clock.cancel();
      if (speed < 6) {
         speed += 2;
      }
      startClock();
   }

   // SPEED DOWN
   private void slowDown() {
      clock.cancel();
      if (speed > 2) {
         speed -= 2;
      }
      startClock();
   }

   // ZOOM OUT
   private void zoomOut() {
      if (zoomFactor < 12) {
         int z = zoomFactor + 3;
         zoomMap(z);
      }
   }

   // ZOOM IN
   private void zoomIn() {
      if (zoomFactor > 3) {
         int z = zoomFactor - 3;
         zoomMap(z);
      }
   }

   // RESET FUNCTION FOR THE MAP
   public void resetMap() {
      stopClock();
      initVars();
      createPlanets();
      createPlayers();
      if (mapViewport != null) {
         offset = new Point(mapViewport.width / 2, mapViewport.height / 2);
      }
      selectedShip = null;

      zoomMap(zoomFactor);
      startClock();
   }

   // ADD SHIP FOR THE PLAYER
   public void buyShip(Ship s) {
      player.buyShip(s);
      repaint();
   }

   // DELETE SELECTED SHIP
   public void deleteShip() {
      if (selectedShip != null) {
         selectedShip = player.releaseShip(selectedShip.getID());
         selectedShip = null;
      }
      repaint();
   }

   // MAP DRAW CALLBACK
   public void paintComponent(Graphics g) {
      mapViewport = super.getVisibleRect();              // GET MAPVIEW SIZE

      if (startofgame) {
         drawMainMenu(g);
      } else {
         if (win) {
            drawWINsplash(g);
         } else {
            // Draw Background
            drawBackground((Graphics2D) g);

            lineoftravel = null;
            if (selectedShip != null && !selectedShip.isDestroyed()) {
               lineoftravel = new Line2D.Float(selectedShip.getShipCenter(),
                       selectedShip.getDestination());
               centerMapToObject(selectedShip);
            } else {
               if (selectedPlanet != null) {
                  centerMapToObject(selectedPlanet);
               }
            }

            // DRAW PLANETS
            for (Planet p : Planets) {
               p.drawPlanet(g);
            }

            // DRAW TRAVEL PATH
            if (lineoftravel != null) {
               drawPath(g);
            }

            // DRAW SHIPS
            drawShips(g);

            if (finishedTask && !displayMenu) {
               g.setColor(Color.WHITE);
               Font f = new Font("Orator Std", 1, 14);
               g.setFont(f);
               g.drawString("PAUSED - '" + ((char) keyCodes[10] + "").toUpperCase()
                       + "' to continue", mapViewport.width / 2 - 100, 300);
            }

            // DRAW STATUS BOX IF THERE IS A STATUS
            info = "";
            if (selectedShip != null) {
               if (!selectedShip.isDestroyed()) {
                  info = selectedShip.getDescription();
                  drawOwnerInfo(g, getPlayerByID(selectedShip.getOwnerID()));
                  drawStatusInfo(g, "SHIP INFO");
               }
            } else if (selectedPlanet != null) {
               info = selectedPlanet.getDescription();
               if (selectedPlanet.getOwnerID() != 0) {

                  drawOwnerInfo(g, getPlayerByID(selectedPlanet.getOwnerID()));
               }
               drawStatusInfo(g, "PLANET INFO");
            }

            // DRAW HUD
            drawPlayerInfo(g);
            drawGameInfo(g);
            drawUserOptions(g);

            // DRAW IN GAME MENU
            if (displayMenu) {
               drawInGameMenu(g);
            }
         }
      }
   }

   // DRAW WIN GRAPHICS
   private void drawWINsplash(Graphics g) {
      stopClock();
      Graphics2D g2d = (Graphics2D) g;
      drawWinBG((Graphics2D) g);
      try {
         bgImg = ImageIO.read(getClass().getResourceAsStream("/sprites/winbg.jpg"));
      } catch (IOException ex) {
         System.out.println("Caught ImageIO exception: \n\t" + ex);
      }
      int cX = mapViewport.width / 2;
      int cY = mapViewport.height / 2;
      g2d.setColor(darkBlue200);
      g2d.fillRoundRect(cX - 200, cY - 200, 400, 400, 40, 40);
      makeInGameButtons(cX - 200, cY - 200);
      g2d.setFont(new Font("Orator Std", 1, 60));
      g2d.setColor(lightBlueComp);
      g2d.drawString("VICTORY!", cX - 150, cY);
   }

   // GET PLAYER BY ID
   private Player getPlayerByID(int ID) {
      if (player.getID() == ID) {
         return player;
      } else {
         for (Player e : enemies) {
            if (e.getID() == ID) {
               return e;
            }
         }
      }
      return null;
   }

   // DRAW PLAYER INFO
   private void drawPlayerInfo(Graphics g) {
      Font f = new Font("Orator Std", 1, 15);
      String[] strs = {player.getName(), "Ω " + player.getWealth(),
         "FLEET: " + player.getShips().size(),
         "PLANETS: " + player.getPlanets().size(),
         "INCOME: Ω " + player.getIncome(),
         "New Year In: " + player.getYearCounter() / 10};

      drawHorizontalList(g, strs, f, 10, 10, lightBlue180, lightBlueComp);
   }

   // DRAW GAME INFO
   private void drawGameInfo(Graphics g) {
      Font f = new Font("Orator Std", 1, 15);
      String[] strs = {"SPEED:  " + speed, "ZOOM:  " + zoomFactor};

      drawHorizontalList(g, strs, f, 10, 45, lightBlue180, lightBlueComp);
   }

   // DRAW A LIST OF STRINGS HORIZONTAL
   private void drawHorizontalList(Graphics g, String[] strs, Font f,
           int startX, int startY, Color bgcolor, Color txtcolor) {
      Graphics2D g2d = (Graphics2D) g;

      int[] widths = new int[strs.length + 1];
      widths[0] = 0;
      int height = strGraphicsData(strs[0], f, g)[2];
      for (int x = 1; x <= strs.length; x++) {
         widths[x] = strGraphicsData(strs[x - 1], f, g)[1];
      }

      g2d.setColor(bgcolor);
      int padding = 10;
      int prevW = 0;
      for (int x = 1; x <= strs.length; x++) {
         prevW = prevW + widths[x - 1];
         g2d.fill(edgedPolygon(startX + padding * x + prevW, startY, widths[x],
                 (int) (height * 1.8), (int) (height * 0.4)));
      }
      g2d.setColor(txtcolor);
      g2d.setFont(f);
      prevW = 0;
      for (int x = 1; x <= strs.length; x++) {
         prevW = prevW + widths[x - 1];
         g2d.drawString(strs[x - 1], startX + padding * x + prevW + 10,
                 startY + (int) (height * 1.3));
      }
   }

   // DRAW LIST OF STRINGS VERTICAL
   private void drawVerticalList(Graphics g, String[] strs, Font f1, Font f2,
           int startX, int startY, Color bgcolor, Color txtcolor) {
      Graphics2D g2d = (Graphics2D) g;
      int[] data = strGraphicsData(strs[1], f2, g);

      int numberOfLines = data[0];
      int w = data[1];
      int height = data[2];
      int padding = 10;

      g2d.setColor(bgcolor);
      g2d.fill(edgedPolygon(startX, startY, w, (int) (height * 2), (int) (height * 0.4)));
      g2d.fillRect(startX, startY + (int) (height * 1.7) + padding, w, height * numberOfLines + padding);

      g2d.setColor(txtcolor);
      g2d.setFont(f1);
      g2d.drawString(strs[0], startX + padding, startY + (int) (height * 1.3));
      g2d.setFont(f2);
      int l = 1;
      for (String line : strs[1].split("\n")) {
         g2d.drawString(line, 15, startY + (int) (height * 1.8) + padding + (height * l));
         l++;
      }
   }

   // GET NUMBER OF LINES OF DRAW STRING
   private int[] strGraphicsData(String str, Font f, Graphics g) {
      int maxwidth = 0;
      int numberOfLines = 0;
      g.setFont(f);
      FontMetrics metrics = g.getFontMetrics();
      int heightoffont = metrics.getHeight();
      if (!str.equals("")) {
         for (String line : str.split("\n")) {
            if (maxwidth < metrics.stringWidth(line) + 30) {
               maxwidth = metrics.stringWidth(line) + 30;
            }
            numberOfLines++;
         }
      }
      int[] data = {numberOfLines, maxwidth, heightoffont};
      return data;
   }

   // DRAW INFO PANEL
   private void drawOwnerInfo(Graphics g, Player owner) {
      Font f1 = new Font("Orator Std", 1, 15);
      Font f2 = new Font("Orator Std", 1, 13);
      String[] strs = {"OWNER INFO", owner.getDesc()};

      drawVerticalList(g, strs, f1, f2, 10, 100, lightBlue180, lightBlueComp);
   }

   // DRAWING FOR THE STATUS BOX ON THE MAP
   private void drawStatusInfo(Graphics g, String title) {
      if (info != "") {
         Font f1 = new Font("Orator Std", 1, 15);
         Font f2 = new Font("Orator Std", 1, 13);
         String[] strs = {title, info};
         int[] data = strGraphicsData(info, f2, g);

         int h = mapViewport.height;
         int numberOfLines = data[0];
         int height = data[2];

         drawVerticalList(g, strs, f1, f2, 10, h - (height * numberOfLines) - 50, lightBlue180, lightBlueComp);
      }
   }

   // DRAW IN GAME USER/SHIP OPTIONS
   private void drawUserOptions(Graphics g) {
      Graphics2D g2d = (Graphics2D) g;
      int w = mapViewport.width;
      Font f = new Font("Orator Std", 1, 14);
      int[] data = strGraphicsData("Colonize Planet 'C'", f, g);
      int height = data[2];
      int width = data[1];
      int[] ys = new int[10];
      ys[0] = 100;
      for (int x = 1; x < 10; x++) {
         ys[x] = ys[x - 1] + (int) (height * 1.8) + 10;
      }
      buyshipB = edgedPolygon(w - width - 10, ys[0], width,
              (int) (height * 1.8), (int) (height * 0.4));
      deleteshipB = edgedPolygon(w - width - 10, ys[1], width,
              (int) (height * 1.8), (int) (height * 0.4));
      takeplanetB = edgedPolygon(w - width - 10, ys[2], width,
              (int) (height * 1.8), (int) (height * 0.4));
      g2d.setColor(green220);
      g2d.fill(buyshipB);
      g2d.setColor(Color.WHITE);
      g2d.drawString("Buy Ship '" + ((char) keyCodes[1] + "").toUpperCase()
              + "'", w - width, ys[0] + (int) (height * 1.3));
      if (selectedShip != null && selectedShip.getOwnerID() == player.getID()) {
         g2d.setColor(green220);
         g2d.fill(deleteshipB);
         g2d.setColor(Color.WHITE);
         g2d.drawString("Destroy Ship '" + ((char) keyCodes[2] + "").toUpperCase()
                 + "'", w - width, ys[1] + (int) (height * 1.3));
         if (selectedShip.getType() == 0) {
            Planet p = selectedShip.isNearPlanet(Planets);
            if (p != null && ((Warship) selectedShip).getColonize() > 0
                    && p.getOwnerID() != player.getID()) {
               g2d.setColor(green220);
               g2d.fill(takeplanetB);
               g2d.setColor(Color.WHITE);
               g2d.drawString("Colonize Planet '" + ((char) keyCodes[3] + "").toUpperCase()
                       + "'", w - width, ys[2] + (int) (height * 1.3));
            }
         }
      }
      g2d.setColor(Color.WHITE);
      g2d.drawString("'ESC' for Menu", w - width, 85);

   }

   // DRAW IN GAME MENU
   private void drawInGameMenu(Graphics g) {
      stopClock();
      Graphics2D g2d = (Graphics2D) g;
      int cX = mapViewport.width / 2;
      int cY = mapViewport.height / 2;
      g2d.setColor(darkBlue200);
      g2d.fillRoundRect(cX - 200, cY - 200, 400, 400, 40, 40);
      makeInGameButtons(cX - 200, cY - 200);
      g2d.setFont(new Font("Orator Std", 1, 36));
      g2d.setColor(lightBlueComp);
      g2d.drawString("Space Raid", cX - 150, cY - 130);
      g2d.setFont(new Font("Orator Std", 1, 18));
      g2d.drawString("v1.0", cX + 100, cY - 130);
      g2d.setColor(lightBlue180);
      g2d.fill(setKeysB);
      g2d.fill(saveB);
      g2d.fill(loadB);
      g2d.fill(resetGameB);
      g2d.setColor(lightBlueComp);
      g2d.setFont(new Font("Orator Std", 1, 22));
      g2d.drawString("CHANGE KEYS", cX - 70, cY - 73);
      g2d.drawString("SAVE GAME", cX - 55, cY - 23);
      g2d.drawString("LOAD GAME", cX - 55, cY + 26);
      g2d.drawString("RESET GAME", cX - 60, cY + 76);
      g2d.setFont(new Font("Orator Std", 1, 15));
      g2d.drawString("Press 'ESC' to Quit Menu", cX - 105, cY + 180);
   }

   // MAKE IN GAME BUTTONS
   private void makeInGameButtons(int x, int y) {
      setKeysB = edgedPolygon(x + 50, y + 100, 300, 40, 15);
      saveB = edgedPolygon(x + 50, y + 150, 300, 40, 15);
      loadB = edgedPolygon(x + 50, y + 200, 300, 40, 15);
      resetGameB = edgedPolygon(x + 50, y + 250, 300, 40, 15);
   }

   // RETURN POLYGON SHAPE WITH CUT CORNERS
   private Polygon edgedPolygon(int x, int y, int w, int h, int cut) {
      Polygon p = new Polygon();
      p.addPoint(x, y + cut);
      p.addPoint(x + cut, y);
      p.addPoint(x + w, y);
      p.addPoint(x + w, y + h - cut);
      p.addPoint(x + w - cut, y + h);
      p.addPoint(x, y + h);
      return p;
   }

   // DRAW THE MAIN MENU
   private void drawMainMenu(Graphics g) {
      stopClock();
      Graphics2D g2d = (Graphics2D) g;
      drawTitleBG((Graphics2D) g);
      int cX = mapViewport.width / 2;
      int cY = mapViewport.height / 2;
      makeMenuItems(cX - 200, cY - 200);
      g2d.setColor(darkBlue200);
      g2d.fillRoundRect(cX - 200, cY - 200, 400, 400, 40, 40);
      g2d.setFont(new Font("Orator Std", 1, 36));
      g2d.setColor(lightBlueComp);
      g2d.drawString("Space Raid", cX - 150, cY - 130);
      g2d.setFont(new Font("Orator Std", 1, 18));
      g2d.drawString("v1.0", cX + 100, cY - 130);
      g2d.setColor(lightBlue180);
      g2d.fill(playOption);
      g2d.fill(instructOption);
      g2d.setColor(lightBlueComp);
      g2d.setFont(new Font("Orator Std", 1, 25));
      g2d.drawString("PLAY", cX - 30, cY - 45);
      g2d.drawString("INSTRUCTIONS", cX - 90, cY + 75);
   }

   // MAKE MENU SHAPES
   private void makeMenuItems(int x, int y) {
      playOption = edgedPolygon(x + 50, y + 120, 300, 50, 15);
      instructOption = edgedPolygon(x + 50, y + 240, 300, 50, 15);
   }

   // DRAW TRAVEL PATH OF SHIP
   private void drawPath(Graphics g) {
      Graphics2D g2d = (Graphics2D) g;
      g2d.setColor(lightTeal);
      int x1 = (int) (lineoftravel.getX1() / zoomFactor + offset.x);
      int x2 = (int) (lineoftravel.getX2() / zoomFactor + offset.x);
      int y1 = (int) (lineoftravel.getY1() / zoomFactor + offset.y);
      int y2 = (int) (lineoftravel.getY2() / zoomFactor + offset.y);

      g2d.draw(new Line2D.Float(x1, y1, x2, y2));
   }

   // DRAW SHIPS
   private void drawShips(Graphics g) {
      if (player != null) {
         try {
            player.drawShips(g);
         } catch (ConcurrentModificationException ex) {
            System.out.println("Caught arraylist exception:\n\t" + ex);
         }
      }
      for (Player e : enemies) {
         try {
            e.drawShips(g);
         } catch (ConcurrentModificationException ex) {
            System.out.println("Caught arraylist exception:\n\t" + ex);
         }
      }
   }

   // DRAW GAME BACKGROUND
   private void drawBackground(Graphics2D g2d) {
      // LOAD BACKGROUND IMAGE
      try {
         bgImg = ImageIO.read(getClass().getResourceAsStream("/sprites/bg.png"));
      } catch (IOException ex) {
         System.out.println("Caught ImageIO exception: \n\t" + ex);
      }
      if (mapViewport.width > 2000 || mapViewport.height > 2000) {
         g2d.drawImage(bgImg, 0, 0, mapViewport.width, mapViewport.height, null);
      } else {
         g2d.drawImage(bgImg, 0, 0, null);
      }
   }

   // DRAW GAME TITLE BACKGROUND
   private void drawTitleBG(Graphics2D g2d) {
      // LOAD BACKGROUND IMAGE
      try {
         bgImg = ImageIO.read(getClass().getResourceAsStream("/sprites/titlescreen.jpg"));
      } catch (IOException ex) {
         System.out.println("Caught ImageIO exception: \n\t" + ex);
      }
      g2d.drawImage(bgImg, 0, 0, mapViewport.width, mapViewport.height, null);
   }

   // DRAW GAME TITLE BACKGROUND
   private void drawWinBG(Graphics2D g2d) {
      // LOAD BACKGROUND IMAGE
      try {
         bgImg = ImageIO.read(getClass().getResourceAsStream("/sprites/winbg.jpg"));
      } catch (IOException ex) {
         System.out.println("Caught ImageIO exception: \n\t" + ex);
      }
      g2d.drawImage(bgImg, 0, 0, mapViewport.width, mapViewport.height, null);
   }

   // ZOOM CONTROLS
   public void zoomMap(int z) {
      int w = mapViewport.width;
      int h = mapViewport.height;
      Point screenCenter = new Point(w / 2, h / 2);
      Point spaceCenter = getSpaceLocation(screenCenter);
      this.zoomFactor = z;

      if (player != null) {
         player.setZoom(zoomFactor);
      }
      for (Player e : enemies) {
         e.setZoom(zoomFactor);
      }

      for (int i = 0; i < Planets.size(); i++) {
         Planet obj = Planets.get(i);
         obj.setZoomFactor(zoomFactor);
      }
      centerMap(spaceCenter);
      repaint();
   }

   // OFFSET CONTROLS
   private void offsetMap() {
      if (player != null) {
         player.setOffset(offset);
      }
      for (Player e : enemies) {
         e.setOffset(offset);
      }

      for (int i = 0; i < Planets.size(); i++) {
         Planet obj = Planets.get(i);
         obj.setOffset(offset);
      }
   }

   // CHECK FOR COLLISIONS
   private void checkZones() {
      for (Player e : enemies) {
         checkZonesOfPlayer(e);
      }
   }

   private void checkZonesOfPlayer(Player p) {
      int size1 = player.getShips().size();
      for (int x1 = 0; x1 < size1; x1++) {
         Ship s = player.getShips().get(x1);
         int size2 = p.getShips().size();
         for (int x = 0; x < size2; x++) {
            Ship s2 = p.getShips().get(x);
            if (s.checkZoneOfShip(s2)) {
               stopClock();
               JOptionPane.showMessageDialog(this, s.getLogs(), "SHIP LOGS", JOptionPane.WARNING_MESSAGE);
               if (s.isDestroyed()) {
                  size1--;
                  x1--;
               }
               x = size2;
            }
         }
      }
   }

   // MOVE VIEW FUNCTION
   private void moveMapOffet(int i) {
      if (i == 0) {
         offset = new Point(offset.x + offsetInt, offset.y);   // LEFT
      }
      if (i == 1) {
         offset = new Point(offset.x, offset.y + offsetInt);   // UP
      }
      if (i == 2) {
         offset = new Point(offset.x - offsetInt, offset.y);   // RIGHT
      }
      if (i == 3) {
         offset = new Point(offset.x, offset.y - offsetInt);   // DOWN
      }
      offsetMap();
      repaint();
   }

   // SET SHIP TO MOVE TO MOUSE LOCATIONS
   public void setSelectedDestination(Point des) {
      selectedShip.setDestination(des);
   }

   // SELECTION FUNCTIONS
   private void deselectShip() {
      if (selectedShip != null) {
         selectedShip.setIsSelected(false);
         selectedShip = null;
      }
   }

   // DESELECT ALL PLANETS
   private void deselectPlanet() {
      if (selectedPlanet != null) {
         selectedPlanet.setIsSelected(false);
         selectedPlanet = null;
      }
   }

   // MAP CENTERING FUNCTION
   private void centerMapToObject(Ship s) {
      if (s != null) {
         centerMap(s.getShipCenter());
      }
   }

   private void centerMapToObject(Planet p) {
      if (p != null) {
         centerMap(p.getPlanetCenter());
      }
   }

   // POINT p IS IN SPACE POSITION NOT VIEW POINT
   private void centerMap(Point p) {
      if (p != null) {
         int w = mapViewport.width;
         int h = mapViewport.height;
         int x = p.x / zoomFactor;
         int y = p.y / zoomFactor;
         offset = new Point(w / 2 - x, h / 2 - y);
         offsetMap();
      }
   }

   // GET THE CONVERTED SPACE LOCATION OF THE MOUSE LOCATION
   private Point getSpaceLocation(Point screenLocation) {
      int sX = zoomFactor * (screenLocation.x - offset.x);
      int sY = zoomFactor * (screenLocation.y - offset.y);
      return new Point(sX, sY);
   }

   // CHECK IF PLANETS ARE AT THE POINT p
   private Planet checkPlanets(Point p) {
      for (Planet planet : Planets) {
         if (planet.checkCoordinates(p)) {
            return planet;
         }
      }
      return null;
   }

   // CHECK IF SHIPS ARE AT THE POINT p
   private Ship checkShips(Point p) {
      Ship s = player.checkLocationOnShips(p);
      for (int i = 0; i < enemies.size(); i++) {
         if (s == null) {
            s = enemies.get(i).checkLocationOnShips(p);
         }
      }
      if (s != null) {
         s.setIsSelected(true);
         return s;
      } else {
         return null;
      }
   }

   // GET KEYBOARD SET OF CODES
   public int[] getKeyCodes() {
      return keyCodes;
   }

   // MAP KEYBOARD ACTIONS
   public void keyAction(int keycode) {
      if (keycode == 27 && !startofgame) {        // ESC
         displayMenu = !displayMenu;
         if (!displayMenu) {
            startClock();
         }
      }
      if (!displayMenu && !startofgame) {
         if (keycode == keyCodes[0]) {                               // SPACE
            deselectShip();
            if (selectedPlanet != null) {
               deselectPlanet();
            }
         } else if (keycode == keyCodes[1]) {                       // Z
            showBuyFrame();
         } else if (keycode == keyCodes[2]) {                       // X
            if (selectedShip != null && selectedShip.getOwnerID() == player.getID()) {
               deleteShip();
            }
         } else if (keycode == keyCodes[3]) {                       // C
            if (selectedShip != null && selectedShip.getOwnerID() == player.getID()) {
               if (selectedShip.getType() == 0) {
                  Planet p = selectedShip.isNearPlanet(Planets);
                  if (p != null && ((Warship) selectedShip).getColonize() > 0
                          && p.getOwnerID() != player.getID()) {
                     takePlanet();
                  }
               }
            }
         }
         if (keycode == keyCodes[4]) {                           // A
            moveMapOffet(0);
         } else if (keycode == keyCodes[5]) {                           // W
            moveMapOffet(1);
         } else if (keycode == keyCodes[6]) {                           // D
            moveMapOffet(2);
         } else if (keycode == keyCodes[7]) {                           // S
            moveMapOffet(3);
         } else if (keycode == keyCodes[8]) {                         // R
            speedUp();
         } else if (keycode == keyCodes[9]) {                         // F
            slowDown();
         } else if (keycode == keyCodes[10]) {                         // G
            finishedTask = !finishedTask;
            if (!finishedTask) {
               startClock();
            }
         } else if (keycode == keyCodes[11]) {                         // Q
            zoomOut();
         } else if (keycode == keyCodes[12]) {                         // E
            zoomIn();
         }
      }
      repaint();
   }

   // DISPLAY KEY CHANGE FRAME
   private void showKeyFrame() {
      stopClock();
      setkeyframe = new SetKeysFrame(keyCodes);
      setkeyframe.getCloseB().addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent ae) {
            setkeyframe.setVisible(false);
            repaint();
         }
      });
      setkeyframe.getApplyB().addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent ae) {
            keyCodes = setkeyframe.getKeyCodes();
            setkeyframe.setVisible(false);
            repaint();
         }
      });
   }

   // DISPLAY BUY FRAME
   private void showBuyFrame() {
      stopClock();
      buyframe = new BuyShipFrame(player);
      buyframe.getRegister().addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent ae) {
            Ship[] ss = buyframe.registerShip();
            if (ss != null) {
               for (Ship s : ss) {
                  s.setZoomFactor(zoomFactor);
                  s.setOffset(offset);
                  player.buyShip(s);
               }
            }
            buyframe.setVisible(false);
            repaint();
            startClock();
         }
      });
      buyframe.getCloseB().addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent ae) {
            buyframe.setVisible(false);
            repaint();
            startClock();
         }
      });
   }

   // COLONIZE A PLANET
   private void takePlanet() {
      Planet p = selectedShip.isNearPlanet(Planets);
      if (p != null) {
         stopClock();
         String str = (String) JOptionPane.showInputDialog(this,
                 "Give a name to this new planet",
                 "Name Planet",
                 JOptionPane.QUESTION_MESSAGE);
         if (str != null) {
            p.setName(str);
         }
         player.addPlanet(p, selectedShip);
         startClock();
      }
   }

   // CHECK FOR WIN
   private void checkWin() {
      if (player.getPlanets().size() == numPlanets) {
         win = true;
      } else {
         win = false;
      }
   }

   // MOUSE ACTION LISTENERS
   public void mousePressed(MouseEvent event) {
   }

   public void mouseClicked(MouseEvent event) {
   }

   public void mouseReleased(MouseEvent event) {
      Point mouseLocation = event.getPoint();
      if (startofgame) {
         if (playOption.contains(mouseLocation)) {
            if (startofgame && settingsNotShown) {
               settingsFrame = new SettingsFrame();
               settingsFrame.addComponentListener(new ComponentAdapter() {
                  public void componentHidden(ComponentEvent evt) {
                  }
               });
               settingsFrame.getDone().addActionListener(
                       new ActionListener() {
                          @Override
                          public void actionPerformed(ActionEvent ae) {
                             settingsFrame.setVisible(false);
                             setOptions(settingsFrame.getData());
                             resetMap();
                             startofgame = false;
                             settingsNotShown = false;
                          }
                       });
            } else {
               startClock();
            }
            repaint();
         } else if (instructOption.contains(mouseLocation)) {
            String info = "-GAME FEATURES-\n"
                    + "-This game is a space race game to colonize all the possible planets.\n"
                    + "-The player can set their own name, color, and starting wealth, along with\n"
                    + "number of planets, and number of enemies.\n"
                    + "-Player wins by colonizing all planets.\n"
                    + "-There are three types of ships.\n"
                    + "   -Fighter ships: fast, fights well, and cost the least, can destroy other\n"
                    + "      fighter ships.\n"
                    + "   -Mother ships: mediums speed, doesn't fight well, can help give shield to\n"
                    + "      other friendly ships it passes by.\n"
                    + "   -Warships: largest ships, slow, has lots of damage, can colonize 2 planets.\n"
                    + "-Players start with 1 warship and 1 fighter ship.\n"
                    + "-Time on the status shown on the top gives when the year will advance and\n"
                    + "   when the income will be collected.\n"
                    + "\n"
                    + "-MAP FEATURES-\n"
                    + "-Selecting a ship or planet will highlight them. Deselect by clicking anywhere.\n"
                    + "-Selected ships will have a line showing where they are headed.\n"
                    + "-Selecting the player's ships will allow for mouse movement on the map,\n"
                    + "   click anywhere on the map and the ship will set its destination to there.\n"
                    + "-Ships have a zone of detection that is colored in a circular shape, ships\n"
                    + "   that come into contact with that zone will be engaged.\n"
                    + "-Zoom and speed can be changed with keys.\n"
                    + "-Object info is displayed on the bottom left when selected.\n"
                    + "-Owner info of selected object is shown if that object has an owner.\n"
                    + "-Map can be moved with AWSD keys when nothing is selected.\n"
                    + "\n"
                    + "-IN GAME MENU-\n"
                    + "-Players can press 'ESC' for in game menu.\n"
                    + "-Changing keys allows user to see all the keys mapped to certain actions, \n"
                    + "   and they can change them to other keys, ones that are not already mapped.\n"
                    + "-Warships with colonize left can go on planets and the colonize option will\n"
                    + "   show up with the other green option buttons.\n"
                    + "-Players can delete their ships only.\n"
                    + "-Players can buy ships and set them to spawn anywhere, even on their planets\n"
                    + "   for easier transitions.\n"
                    + "-Players can save game states and load game states. File format is SGS.\n"
                    + "\n"
                    + "-AI PLAYERS-\n"
                    + "-AI moves ships randomly when ships reach destinations.\n"
                    + "-AI also buys Motherships randomly when there is enough money.";
            JOptionPane.showMessageDialog(this, info, "Game Info", JOptionPane.INFORMATION_MESSAGE);
         }
      }
      if (displayMenu) {
         if (resetGameB.contains(mouseLocation)) {
            startofgame = true;
            displayMenu = false;
            settingsNotShown = true;
            repaint();
         } else if (setKeysB.contains(mouseLocation)) {
            showKeyFrame();
         } else if (saveB.contains(mouseLocation)) {
            saveMapToFile();
         } else if (loadB.contains(mouseLocation)) {
            loadMapFromFile();
         }
      }
      if (!displayMenu && !startofgame) {
         if (buyshipB.contains(mouseLocation)) {
            showBuyFrame();
         } else if (deleteshipB.contains(mouseLocation)) {
            deleteShip();
         } else if (takeplanetB.contains(mouseLocation)) {
            takePlanet();
         } else {
            // IF A SHIP IS ALREADY SELECTED, SET DESTINATION
            if (selectedShip != null && !selectedShip.isDestroyed()
                    && selectedShip.getOwnerID() == player.getID()) {
               setSelectedDestination(getSpaceLocation(mouseLocation));
               centerMapToObject(selectedShip);
            } else {
               // IF A SHIP ISNT SELECTED, AND A POINT IS CLICKED
               deselectPlanet();
               deselectShip();
               selectedShip = checkShips(mouseLocation);
               centerMapToObject(selectedShip);
               if (selectedShip == null) {
                  selectedPlanet = checkPlanets(mouseLocation);
                  if (selectedPlanet != null) {
                     selectedPlanet.setIsSelected(true);
                     centerMapToObject(selectedPlanet);
                  } else {

                  }
               }
            }
            repaint();
         }
      }
   }

   public void mouseEntered(MouseEvent event) {

   }

   public void mouseExited(MouseEvent event) {
   }

}
