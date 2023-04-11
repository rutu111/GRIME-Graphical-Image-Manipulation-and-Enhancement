package controller;

import view.ViewI;

import java.io.IOException;
import java.util.Map;
import java.util.function.Function;

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
