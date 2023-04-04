package view;

import java.io.IOException;

/**
 * This is the view class where the output is shown from the command line interface.
 */
public class View implements ViewI {

    final Appendable out;

    /**
     * This is the view class constructor.
     * @param out The output string
     */
    public View(Appendable out) {
        this.out = out;
    }


    /**
     * Prints the welcome message and the list of available commands.
     */
    public void printWelcomeMessage() {
        System.out.println("Welcome to image processing with PPM files! You can enter commands here. "
                + "Type exit to exit the program anytime.\n");
        System.out.println("You can use the following commands to perform the following operations:\n");
        System.out.println("LOAD: load filepath imageName\n");
        System.out.println("SAVE: save filepath imageName\n");
        System.out.println("BRIGHTEN: brighten increment-value imageName newimageName\n");
        System.out.println("HORIZONTAL-FLIP: horizontal-flip imageName newimageName\n");
        System.out.println("VERTICAL-FLIP: vertical-flip imageName newimageName\n");
        System.out.println("GREYSCALE component: greyscale component-type imageName newimageName\n");
        System.out.println("RGB TO GREYSCALE: rgb-split imageName newimageNameR newimageNameG "
                + "newimageNameB\n");
        System.out.println("GREYSCALE TO RGB: rgb-combine newimageName imageNameR imageNameG imageNameB"
                + "\n");
        System.out.println("Blur: filter-blur imageName newimageName\n");
        System.out.println("Dither: dither imageName newimageName\n");
        System.out.println("Sharpen: filter-sharpen imageName newimageName\n");
        System.out.println("Greyscale Transformation: transform-greyscale fimageName newimageName\n");
        System.out.println("Sepia Transformation: transform-sepia fimageName newimageName\n");

    }


    @Override
    public void printOutput(String output) throws IOException {
        this.out.append(output);
    }
}