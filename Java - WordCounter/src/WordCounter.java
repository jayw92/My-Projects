/*
 ************************************************
 * NAME: Jay Wang    EMAIL: jayw.92@gmail.com
 *
 * DESCRIPTION: This is a program that takes in
 *    text input and outputs frequncies of all 
 *    distinct words in the text in alphabetical
 *    order and also the standard deviation for 
 *    the counts. This was made for the Skillz
 *    Java Groovy Engineer Take-Home Test.
 ************************************************
 */

import java.awt.*;
import java.text.BreakIterator;
import java.util.*;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class WordCounter extends JFrame {

   // Private class variables
   private final JTextArea InputTextArea;
   private final JTextArea OutputTextArea;

   // Java main function
   public static void main(String[] args) {
      new WordCounter();
   }

   // Initiates components in the Jframe
   public WordCounter() {

      // Window setup, layout, and behaviors
      setLocation(50, 50);
      setSize(800, 800);
      setMinimumSize(new Dimension(500, 500));
      setTitle("Word Counter - Skillz Exercise");
      setDefaultCloseOperation(EXIT_ON_CLOSE);
      Container content = getContentPane();
      content.setLayout(new BorderLayout());

      // Setup TextAreas and put them next to each other
      InputTextArea = new JTextArea();
      InputTextArea.setFont(new Font("Serif", Font.BOLD, 15));
      InputTextArea.setLineWrap(true);
      InputTextArea.setWrapStyleWord(true);
      InputTextArea.setBackground(Color.BLACK);
      InputTextArea.setForeground(Color.WHITE);

      // Add listener to InputTextArea so if changes are made the text will be
      //    analyzed immediately.
      InputTextArea.getDocument().addDocumentListener(new DocumentListener() {
         @Override
         public void removeUpdate(DocumentEvent e) {
            RunStatistics();
         }

         @Override
         public void insertUpdate(DocumentEvent e) {
            RunStatistics();
         }

         @Override
         public void changedUpdate(DocumentEvent e) {
            RunStatistics();
         }
      });

      OutputTextArea = new JTextArea();
      OutputTextArea.setEditable(false);
      OutputTextArea.setLineWrap(true);
      OutputTextArea.setFont(new Font("Serif", Font.PLAIN, 14));
      OutputTextArea.setBackground(Color.BLACK);
      OutputTextArea.setForeground(Color.GREEN);

      JScrollPane InputScrollPane = new JScrollPane(InputTextArea);
      JScrollPane OutputScrollPane = new JScrollPane(OutputTextArea);

      JPanel TextAreaPanels = new JPanel();
      TextAreaPanels.setLayout(new GridLayout(1, 2));
      TextAreaPanels.add(InputScrollPane);
      TextAreaPanels.add(OutputScrollPane);

      // Titles for the TextAreas
      JPanel TextAreaTitlePanels = new JPanel();
      TextAreaTitlePanels.setLayout(new GridLayout(1, 2));
      TextAreaTitlePanels.add(new JLabel("Input Text", javax.swing.SwingConstants.CENTER));
      TextAreaTitlePanels.add(new JLabel("Output Statistics", javax.swing.SwingConstants.CENTER));

      // Layout all panels onto main JFrame
      content.add(TextAreaTitlePanels, BorderLayout.NORTH);
      content.add(TextAreaPanels, BorderLayout.CENTER);

      // Display window
      setVisible(true);
   }

   // Analyze InputTextArea field and output to OutputTextArea
   // Enforces all letters are lowercase as per example
   private void RunStatistics() {
      String InputText = InputTextArea.getText().toLowerCase();

      // Create hashmap of words as key and count as value using HashmapWordsFromString()
      HashMap<String, Integer> hashmap = new HashMap<String, Integer>();
      HashmapWordsFromString(InputText, hashmap);

      // Sort the hashmap by alphabetical order
      TreeMap SortedHashMap = new TreeMap(hashmap);

      // Get the set of the sorted map and iterate through it
      Set set = SortedHashMap.entrySet();
      Iterator iterator = set.iterator();

      // Display to OutputTextArea
      OutputTextArea.setText("\nWord frequencies in alphabetical order (Word: # of times appeared)\n\n");
      while (iterator.hasNext()) {
         Map.Entry entry = (Map.Entry) iterator.next();
         OutputTextArea.append(entry.getKey() + ": " + entry.getValue() + "\n");
      }

      // Bonus part: Standard Deviation, also added sum and mean
      double[] stats = StatsOfCollection(hashmap);
      String statsText = "\nStatistics: "
              + "\nTotal word count: " + (int) stats[0]
              + "\nAverage word frequency: " + stats[1]
              + "\nStandard Deviation: " + stats[2];

      OutputTextArea.append(statsText);

   }

   // Gets the sum, mean, and std dev of a Hashmap of integers
   private double[] StatsOfCollection(HashMap<String, Integer> hashmap) {
      double[] stats = new double[3];
      int sum = 0;
      double stdSum = 0;
      double mean, stdDeviation;

      for (Integer x : hashmap.values()) {
         sum += x;
      }

      mean = sum / (hashmap.size() * 1.0);

      for (Integer i : hashmap.values()) {
         stdSum += Math.pow((i - mean), 2);
      }

      stdDeviation = Math.sqrt(stdSum / (hashmap.size() * 1.0));

      stats[0] = sum;
      stats[1] = mean;
      stats[2] = stdDeviation;

      return stats;
   }

   // Function to extract only words from a string using BreakIterator.getWordInstance
   private void HashmapWordsFromString(String input, HashMap<String, Integer> hashmap) {
      Locale currentLocale = new Locale("en", "US");
      BreakIterator wordIterator
              = BreakIterator.getWordInstance(currentLocale);

      // Add iterator to the input text
      wordIterator.setText(input);
      int start = wordIterator.first();
      int end = wordIterator.next();

      // Loop through string till finished
      while (end != BreakIterator.DONE) {
         String word = input.substring(start, end);

         // If there is a possible word
         if (Character.isLetterOrDigit(word.charAt(0))) {
            // Check if word is already keyed in hashmap then count word
            if (hashmap.containsKey(word)) {
               hashmap.put(word, hashmap.get(word) + 1);
            } else {
               hashmap.put(word, 1);
            }
         }
         start = end;
         end = wordIterator.next();
      }
   }
   
}
