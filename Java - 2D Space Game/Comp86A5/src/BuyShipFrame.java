
/**
 * ******************************************************************
 * NAME: Jiayuan Wang (Jay) EMAIL: jayw.92@gmail.com
 *
 * DESCRIPTION: This class implements the frame for buying new ship(s).
 *
 * ******************************************************************
 */
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.Point;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class BuyShipFrame extends JFrame {

   private JComboBox shipCB, planetsCB;
   private JSlider numberofshipsS;
   private JCheckBox whereBox;
   private JTextField xTF, yTF;
   private JButton closeB, registerB;
   private final String[] typeStrings = {"Warship", "Fightership", "Mothership"};
   private Player player;
   private JLabel costJLabel, shipstobuy;
   final int[] costs = {20000, 1000, 4000};

   public BuyShipFrame(Player p) {
      this.player = p;

      // SET UP THE WINDOW FOR BUYING SHIPS
      setLocation(100, 100);
      setSize(300, 200);
      setResizable(false);
      setAlwaysOnTop(true);
      setAutoRequestFocus(true);
      setDefaultCloseOperation(HIDE_ON_CLOSE);
      setTitle("Buy A Ship");
      Container content = getContentPane();

      // SETUP THE FORMS FOR THE DIFFERENT SHIPS
      JPanel form = new JPanel();
      form.setLayout(new GridLayout(9, 2));

      form.add(new JLabel("Player Wealth"));
      form.add(new JLabel("Ω " + p.getWealth()));

      shipCB = new JComboBox(typeStrings);
      shipCB.setSelectedIndex(1);
      shipCB.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent ae) {
            costJLabel.setText("Ω " + costs[shipCB.getSelectedIndex()]);
            int max = (int) (player.getWealth() / costs[shipCB.getSelectedIndex()]);
            numberofshipsS.setMaximum(max);
         }
      });
      form.add(new JLabel("Ship Type: "));
      form.add(shipCB);

      xTF = new JTextField("0", 4);
      form.add(new JLabel("X Position: "));
      form.add(xTF);

      yTF = new JTextField("0", 4);
      form.add(new JLabel("Y Position: "));
      form.add(yTF);

      planetsCB = new JComboBox();
      String[] planetlist = new String[player.getPlanets().size()];
      for (int x = 0; x < player.getPlanets().size(); x++) {
         planetlist[x] = player.getPlanets().get(x).getName();
      }
      if (player.getPlanets().size() > 0) {
         planetsCB = new JComboBox(planetlist);
      }
      planetsCB.setEnabled(false);
      form.add(new JLabel("Planets: "));
      form.add(planetsCB);

      form.add(new JLabel("Near your planet? "));
      whereBox = new JCheckBox();
      whereBox.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent ae) {
            if (whereBox.isSelected()) {
               planetsCB.setEnabled(true);
               xTF.setEnabled(false);
               yTF.setEnabled(false);
            } else {
               planetsCB.setEnabled(false);
               xTF.setEnabled(true);
               yTF.setEnabled(true);
            }
         }
      });
      if (player.getPlanets().size() > 0) {
         whereBox.setEnabled(true);
      } else {
         whereBox.setEnabled(false);
      }
      form.add(whereBox);

      int max = (int) (player.getWealth() / costs[shipCB.getSelectedIndex()]);
      shipstobuy = new JLabel("Ships to buy: " + 1);
      numberofshipsS = new JSlider(0, max, 1);
      numberofshipsS.setMajorTickSpacing(1);
      numberofshipsS.setPaintTicks(false);
      numberofshipsS.setSnapToTicks(true);
      numberofshipsS.addChangeListener(new ChangeListener() {
         public void stateChanged(ChangeEvent ce) {
            shipstobuy.setText("Ships to buy: " + numberofshipsS.getValue());
         }
      });
      form.add(shipstobuy);
      form.add(numberofshipsS);

      costJLabel = new JLabel("Ω " + costs[shipCB.getSelectedIndex()]);
      form.add(new JLabel("Cost: "));
      form.add(costJLabel);

      closeB = new JButton("Close");
      form.add(closeB);

      registerB = new JButton("Buy Ship");
      form.add(registerB);

      // ADD FORM TO CONTENT PANE
      content.add(form);
      pack();
      setVisible(true);
   }

   public JButton getRegister() {
      return registerB;
   }

   public JButton getCloseB() {
      return closeB;
   }
   
   // CREATE THE SHIP(s)
   public Ship[] registerShip() {
      int n = numberofshipsS.getValue();
      if (n == 0) {
         return null;
      } else {
         Ship[] s = new Ship[n];
         int x = shipCB.getSelectedIndex();
         int xpos = Integer.parseInt(xTF.getText());
         int ypos = Integer.parseInt(yTF.getText());
         if (whereBox.isSelected()) {
            xpos = player.getPlanets().get(planetsCB.getSelectedIndex()).getPlanetCenter().x;
            ypos = player.getPlanets().get(planetsCB.getSelectedIndex()).getPlanetCenter().y;
         }
         for (int i = 0; i < n; i++) {
            if (player.getWealth() >= costs[x]) {
               if (x == 0) {
                  s[i] = new Warship(xpos, ypos, player);
               } else if (x == 1) {
                  s[i] = new Fightership(xpos, ypos, player);
               } else if (x == 2) {
                  s[i] = new Mothership(xpos, ypos, player);
               }
               s[i].setDestination(new Point(xpos, ypos));
            }
         }
         return s;
      }
   }

}
