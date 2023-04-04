package controller.Commands;

import controller.CommandDesignOperations;
import model.Operations;
import view.ViewI;

import java.io.IOException;

/**
 * This class is for the command design pattern.
 */
public class RGBSplit implements CommandDesignOperations {

    String imageName;
    String updatedimageName1;
    String updatedimageName2;
    String updatedimageName3;

    /**
     * This constructor takes in a list a commands.
     *
     * @param commands a list of commands, typed by the user.
     * @throws IllegalArgumentException if number of arguments not as expected.
     */
    public RGBSplit(String[] commands) throws IllegalArgumentException {
        if (commands.length != 5) {
            throw new IllegalArgumentException("Invalid command format.");
        }
        this.imageName = commands[1];
        this.updatedimageName1 = commands[2];
        this.updatedimageName2 = commands[3];
        this.updatedimageName3 = commands[4];
    }

    @Override
    public void goCommand(Operations m, ViewI view)
            throws NoSuchFieldException, IllegalAccessException, IOException {
        m.splitInto3Images(imageName, updatedimageName1, updatedimageName2,
                updatedimageName3);
        view.printOutput(
                "Image '" + imageName + "' has been split into greyscale images: '"
                        + updatedimageName1 + "'" + updatedimageName2 + "' and " + updatedimageName3 +
                        "'" + "\n");

    }
}
