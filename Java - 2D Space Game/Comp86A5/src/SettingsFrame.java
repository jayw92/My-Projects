
/**
 * ******************************************************************
 * NAME: Jiayuan Wang (Jay) EMAIL: jayw.92@gmail.com
 *
 * DESCRIPTION: This class implements the JFrame for the Settings frame and has
 * certain attributes that can be changed.
 * ******************************************************************
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.border.*;

public class SettingsFrame extends JFrame {

   private JButton chooseColorB, closeB, changeB;
   private JSlider startWealthS, numPlanetsS;
   private JLabel colorL, wealthL, planetsL;
   private JComboBox numEnemiesCB;
   private JTextField playerNameTF;
   private final int[] numberList = {3, 4, 5, 6, 7, 8};

   public SettingsFrame() {

      // SET UP THE WINDOW FOR SETTINGS
      setLocation(100, 100);
      setSize(200, 500);
      setResizable(false);
      setAlwaysOnTop(true);
      setAutoRequestFocus(true);
      setDefaultCloseOperation(HIDE_ON_CLOSE);
      setTitle("Settings");
      Container content = getContentPane();

      // SETUP
      JPanel allPanels = new JPanel();
      allPanels.setLayout(new BorderLayout());

      JPanel forms = new JPanel();
      forms.setLayout(new BorderLayout());

      JPanel playerPanel = new JPanel();
      playerPanel.setLayout(new GridLayout(4, 2));
      playerPanel.setBorder(BorderFactory.createTitledBorder(
              new LineBorder(Color.BLACK, 3, true),
              "Player Options", TitledBorder.DEFAULT_POSITION, TitledBorder.DEFAULT_POSITION,
              new Font("Serif", Font.PLAIN, 20), Color.BLACK));
      
      
      playerPanel.add(new JLabel("Player Name"));
      playerNameTF = new JTextField("player", 20);
      playerPanel.add(playerNameTF);

      colorL = new JLabel();
      colorL.setBackground(Color.yellow);
      colorL.setOpaque(true);
      chooseColorB = new JButton("Choose Player Color");
      chooseColorB.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            Color c = JColorChooser.showDialog(rootPane,
                    "Choose Player Color", colorL.getBackground());
            if (c != null) {
               colorL.setBackground(c);
            }
         }
      });
      chooseColorB.setEnabled(true);
      playerPanel.add(colorL);
      playerPanel.add(chooseColorB);

      playerPanel.add(new JLabel("Starting Wealth"));
      playerPanel.add(new JLabel(" "));

      wealthL = new JLabel("5000 Ω");
      startWealthS = new JSlider(5000, 50000, 5000);
      startWealthS.setMajorTickSpacing(5000);
      startWealthS.setPaintTicks(false);
      startWealthS.setSnapToTicks(false);
      startWealthS.addChangeListener(new ChangeListener() {
         public void stateChanged(ChangeEvent ce) {
            wealthL.setText(startWealthS.getValue() + " Ω");
         }
      });
      playerPanel.add(startWealthS);
      playerPanel.add(wealthL);

      JPanel optionsPanel = new JPanel();
      optionsPanel.setLayout(new GridLayout(3, 2));
      optionsPanel.setBorder(BorderFactory.createTitledBorder(
              new LineBorder(Color.BLACK, 3, true),
              "Game Options", TitledBorder.DEFAULT_POSITION, TitledBorder.DEFAULT_POSITION,
              new Font("Serif", Font.PLAIN, 20), Color.BLACK));

      
      String[] strlist = {"3", "4", "5", "6", "7", "8"};
      numEnemiesCB = new JComboBox(strlist);
      numEnemiesCB.setSelectedIndex(2);
      optionsPanel.add(new JLabel("Starting # of Enemies"));
      optionsPanel.add(numEnemiesCB);
      
      optionsPanel.add(new JLabel("Starting # of Planets"));
      optionsPanel.add(new JLabel(" "));

      planetsL = new JLabel("30 Planets");
      numPlanetsS = new JSlider(20, 40, 30);
      numPlanetsS.setMajorTickSpacing(1);
      numPlanetsS.setPaintTicks(false);
      numPlanetsS.setSnapToTicks(false);
      numPlanetsS.addChangeListener(new ChangeListener() {

         public void stateChanged(ChangeEvent ce) {
            planetsL.setText(numPlanetsS.getValue() + " Planets");
         }

      });
      optionsPanel.add(numPlanetsS);
      optionsPanel.add(planetsL);
      

      forms.add(playerPanel, BorderLayout.NORTH);
      forms.add(optionsPanel, BorderLayout.CENTER);

      JPanel bottomPanel = new JPanel();
      closeB = new JButton("Close");
      closeB.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            setVisible(false);
         }
      });
      bottomPanel.add(closeB);

      changeB = new JButton("Done");
      changeB.setEnabled(true);
      bottomPanel.add(changeB);

      JLabel title = new JLabel("Settings & Options");
      title.setHorizontalAlignment(SwingConstants.CENTER);
      title.setFont(new Font("Serif", Font.BOLD, 25));
      allPanels.add(title, BorderLayout.NORTH);
      allPanels.add(forms, BorderLayout.CENTER);
      allPanels.add(bottomPanel, BorderLayout.SOUTH);

      // ADD FORM TO CONTENT PANE
      content.add(allPanels);
      pack();
      setVisible(true);
   }
   
   // SEND SETTINGS DATA
   public ArrayList getData(){
      ArrayList data = new ArrayList();
      data.add(playerNameTF.getText());
      data.add(colorL.getBackground());
      data.add(startWealthS.getValue());
      data.add(numberList[numEnemiesCB.getSelectedIndex()]);
      data.add(numPlanetsS.getValue());
      return data;
   }

   public JButton getDone() {
      return changeB;
   }

}
