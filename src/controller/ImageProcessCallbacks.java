package controller;

import view.ViewI;

import java.io.IOException;


/**
 * This interface si for callbacks.
 */
public interface ImageProcessCallbacks {

  /**
   * The following method sends input from the GUI to the controller.
   *
   * @param actionCommands list of commands mimiciking user input.
   * @param view           viewGUI object.
   * @throws IOException if one occurs.
   */
  void executeFeatures(String[] actionCommands, ViewI view) throws IOException;
}
