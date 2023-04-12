package view;

import java.io.IOException;

/**
 * This interface is for the command line view.
 */
public interface ViewI {

  /**
   * The following method prints the welcome message
   * which informs the user what operations they can do.
   */
  void printWelcomeMessage();

  /**
   * The following method prints the output method from
   * the controller. The output can be a success message
   * or an error.
   *
   * @param output the string to output.
   * @throws IOException if there is an IO exception..
   */
  void printOutput(String output) throws IOException;



}
