package controller.commands;

import controller.CommandDesignOperations;
import model.Operations;
import view.ViewI;

import java.io.IOException;

/**
 * This class is for the command design pattern.
 */
public class Sepia implements CommandDesignOperations {

    private String imageName;

    private String updatedImageName;

    /**
     * This constructor takes in a list a commands.
     *
     * @param commands a list of commands, typed by the user.
     * @throws IllegalArgumentException if number of arguments not as expected.
     */
    public Sepia(String[] commands) {
        if (commands.length != 3) {
            throw new IllegalArgumentException("Invalid command format.");
        }
        this.imageName = commands[1];
        this.updatedImageName = commands[2];
    }

    @Override
    public void goCommand(Operations m, ViewI view) throws IOException {
        m.colorTransformationSepia(imageName,
                updatedImageName);
        view.printOutput(
                "Image has been provided with sepia tone '" + imageName + "' stored as '"
                        + updatedImageName + "'" +
                        "\n");
    }
}
