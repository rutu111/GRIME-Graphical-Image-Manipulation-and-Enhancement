package view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;

/**
 * View class for thr GUI.
 * Takes in error panel object as ar argument
 * so that we can display  messages on the GUI.
 */

public class ViewGUI extends JFrame implements ViewI {

  private final JLabel label;

  /**
   * Constructor takes in an error panel object.
   * Purpose of doing this is to display logs on the GUI.
   *
   * @param errorPanel a panel to display log messages.
   */
  public ViewGUI(JPanel errorPanel) {
    label = new JLabel();
    errorPanel.add(label);
  }


  @Override
  public void printWelcomeMessage() {
  //its an empty method.
  }

  @Override
  public void printOutput(String output) {
    String outputx = "<html>" + output + "</html>";
    label.setText(outputx);

  }

}
