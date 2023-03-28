package Controller;

import View.ViewI;
import Model.Operations;
import java.io.IOException;

public interface CommandDesignOperations {
  void go(Operations m, ViewI view) throws NoSuchFieldException, IllegalAccessException, IOException;

}
