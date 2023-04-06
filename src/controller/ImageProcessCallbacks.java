package controller;
import view.ViewI;

import java.io.IOException;

public interface ImageProcessCallbacks {
   void executeFeatures(String[] actionCommands, ViewI view) throws IOException;

}
