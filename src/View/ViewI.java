package View;

import java.io.IOException;

public interface ViewI {

  void printWelcomeMessage();

  void printOutput(String output) throws IOException;

  void printError(String message) throws IOException;
}
