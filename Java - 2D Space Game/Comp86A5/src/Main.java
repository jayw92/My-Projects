
/**
 * ******************************************************************
 * NAME: Jiayuan Wang (Jay) EMAIL: jayw.92@gmail.com
 *
 * DESCRIPTION: Main.java is the main class containing the window of the entire
 * project and has the map object displayed in the center.
 * ******************************************************************
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

public class Main extends JFrame implements KeyListener {

   private Map map;
   private final Color lightBlue180 = new Color(50, 100, 200, 180);

   public static void main(String[] args) {
      new Main();
   }

   public Main() {
      // SET UP THE WINDOW FOR THE PROGRAM
      setLocation(0, 0);
      setSize(800, 800);
      setMinimumSize(new Dimension(800, 800));
      setDefaultCloseOperation(EXIT_ON_CLOSE);

      Container content = getContentPane();
      content.setLayout(new BorderLayout());

      // GIVE THE TITLE FOR THE TOP BORDER OF THE FRAME
      setTitle("SpaceRaid v1.0");

      // ADD THE DRAWING CANVAS FOR THE SYSTEM
      map = new Map();
      map.setBorder(new LineBorder(lightBlue180, 5));
      content.add(map, BorderLayout.CENTER);

      // ONLY FOCUS ON THE MAIN FRAME AND WHETHER KEYS ARE PRESSED
      setFocusable(true);
      setAutoRequestFocus(true);

      // ADD KEY LISTNER
      addKeyListener(this);

      // DISPLAY WINDOW
      setVisible(true);
   }

   public void keyTyped(KeyEvent ke) {
   }

   // SEND KEY PRESSED KEYCODE TO MAP
   public void keyPressed(KeyEvent ke) {
      int keycode = ke.getKeyCode();
      map.keyAction(keycode);
   }

   public void keyReleased(KeyEvent ke) {

   }

}
