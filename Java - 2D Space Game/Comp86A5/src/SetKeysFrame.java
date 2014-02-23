
/**
 * ******************************************************************
 * NAME: Jiayuan Wang (Jay) EMAIL: jayw.92@gmail.com
 *
 * DESCRIPTION: This class implements the frame for setting keys for certain
 * actions. Keys can only be used once.
 *
 * ******************************************************************
 */
import java.awt.Container;
import java.awt.GridLayout;
import javax.swing.*;
import java.awt.event.*;
import java.util.Enumeration;

public class SetKeysFrame extends JFrame implements KeyListener {

   private JToggleButton[] toggles;
   private JToggleButton button;
   private final String[] keyActions = {"Deselect Object", "Buy Ship", "Delete Ship",
      "Colonize Planet", "Move Map Left", "Move Map Up", "Move Map Right", "Move Map Down",
      "Speed Up", "Slow Down", "Pause/Go", "Zoom Out", "Zoom In"};
   private int[] keyCodes;
   private JButton closeB, applyB;
   private int keyPressed;
   private char charPressed;
   private ButtonGroup group;

   public SetKeysFrame(int[] keys) {
      keyCodes = keys;
      toggles = new JToggleButton[keyActions.length];
      
      setLocation(100, 100);
      setSize(300, 200);
      setResizable(false);
      setAlwaysOnTop(true);
      setFocusable(true);
      setAutoRequestFocus(true);
      setDefaultCloseOperation(HIDE_ON_CLOSE);
      setTitle("Set Keys");
      Container content = getContentPane();

      // USE TOGGLE BUTTONS FOR CHANGING KEYS
      JPanel form = new JPanel();
      form.setLayout(new GridLayout(keyActions.length + 2, 2));
      group = new ButtonGroup();
      form.add(new JLabel("ACTIONS"));
      form.add(new JLabel("KEYS"));
      for (int x = 0; x < keyActions.length; x++) {
         char c = (char) keyCodes[x];
         String s = c + "";
         if (" ".equals(s)) {
            s = "SPACE";
         }
         button = new JToggleButton(s);
         button.setFocusable(false);
         toggles[x] = button;
         group.add((AbstractButton) toggles[x]);
         form.add(new JLabel(keyActions[x]));
         form.add(toggles[x]);
      }

      closeB = new JButton("Close");
      form.add(closeB);

      applyB = new JButton("Apply");
      form.add(applyB);

      // ADD FORM TO CONTENT PANE
      content.add(form);
      addKeyListener(this);
      pack();
      setVisible(true);
   }

   public JButton getApplyB() {
      return applyB;
   }

   public JButton getCloseB() {
      return closeB;
   }

   // SENT KEY CODES
   public int[] getKeyCodes() {
      for (int x = 0; x < keyActions.length; x++) {
         if ("SPACE".equals(toggles[x].getText())) {
            keyCodes[x] = 32;
         } else {
            keyCodes[x] = toggles[x].getText().charAt(0);
         }
      }
      return keyCodes;
   }

   public void keyTyped(KeyEvent ke) {
   }

   // RECORD KEY PRESSED
   public void keyPressed(KeyEvent ke) {
      charPressed = ke.getKeyChar();
      keyPressed = ke.getKeyCode();
      keyCodes = getKeyCodes();
      int x = 0;
      for (int i = 0; i < keyCodes.length; i++) {
         if (keyCodes[i] == keyPressed) {
            x++;
         }
      }
      if (x == 0) {
         Enumeration elements = group.getElements();
         while (elements.hasMoreElements()) {
            AbstractButton b = (AbstractButton) elements.nextElement();
            if (b.isSelected()) {
               String s = "" + charPressed;

               if (" ".equals(s)) {
                  s = "SPACE";
               }
               b.setText(s.toUpperCase());
            }
         }
      }
   }

   public void keyReleased(KeyEvent ke) {
   }

}
