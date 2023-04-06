package controller;

import view.ViewI;
import model.Operations;

import java.io.IOException;

/**
 * This is an interface for the command design pattern.
 */
public interface CommandDesignOperations {

  /**
   * The following method calls the respective operation on
   * the object using the model instance.
   *
   * @param m    Model instance.
   * @param view View instance.
   * @throws NoSuchFieldException   if field not found.
   * @throws IllegalAccessException if illegal access.
   * @throws IOException            if IO exceptions.
   */
  void goCommand(Operations m, ViewI view) throws NoSuchFieldException, IllegalAccessException,
      IOException;

}
