package controller;
import view.ViewI;

import java.io.IOException;
import java.util.Map;
import java.util.function.Function;

public interface ImageProcessCallbacks {
   void executeFeatures(String[] actionCommands, ViewI view) throws IOException;

}
