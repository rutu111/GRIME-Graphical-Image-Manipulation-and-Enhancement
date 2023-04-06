package view;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import controller.ImageProcessCallbacks;
import controller.commands.Load;
import controller.commands.Save;
import controller.commands.TransformGreyscale;
import model.Operations;
import model.TypeOfImage;
import model.TypeofImageObject;

public class ImageProcessorUI extends JFrame {

    private static String imageName;
    private JButton uploadButton;
    private JButton saveButton;
    private JLabel imageLabel;
    private JFileChooser fileChooser;


    public ImageProcessorUI(Operations model, ImageProcessCallbacks callbacks, String imageNameX) {
        super();
        imageName = imageNameX;

        this.setTitle("Image Processor");
        this.setSize(10000, 10000);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        uploadButton = new JButton("Load");
        saveButton = new JButton("Save");
        imageLabel = new JLabel();
        fileChooser = new JFileChooser();
        JSlider slider = new JSlider(JSlider.HORIZONTAL, -100, 100, 0);


        JPanel buttonPanel = new JPanel();
        buttonPanel.add(uploadButton);
        buttonPanel.add(saveButton);

        add(buttonPanel, BorderLayout.NORTH);
        add(imageLabel, BorderLayout.CENTER);

        JPanel imagePanel = new JPanel(new BorderLayout());
        imagePanel.add(imageLabel, BorderLayout.CENTER);


        // Create the button panel and add the buttons
        JPanel buttonPanel2 = new JPanel(new GridLayout(16, 1, 10, 10));
        buttonPanel2.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 50));
        buttonPanel2.setBorder(BorderFactory.createTitledBorder("OPERATIONS"));

        //operations
        JButton BrightenButton = new JButton("Brighten");
        JButton ValueButton = new JButton("Value");
        JButton intensityButton = new JButton("Intensity");
        JButton LumaButton = new JButton("Luma");
        JButton horizontalFlipButton = new JButton("Horizontal Flip");
        JButton verticalFlipButton = new JButton("Vertical Flip");
        JButton redButton = new JButton("Red");
        JButton blueButton = new JButton("Blue");
        JButton greenButton = new JButton("Green");
        JButton splitButton = new JButton("Split");
        JButton combineButton = new JButton("Combine");
        JButton SepiaButton = new JButton("Sepia");
        JButton DitherButton = new JButton("Dither");
        JButton SharpenButton = new JButton("Sharpen");
        JButton BlurButton = new JButton("Blur");
        JButton greyscaleButton = new JButton("Greyscale");
        buttonPanel2.add(BrightenButton);
        buttonPanel2.add(ValueButton);
        buttonPanel2.add(intensityButton);
        buttonPanel2.add(LumaButton);
        buttonPanel2.add(horizontalFlipButton);
        buttonPanel2.add(verticalFlipButton);
        buttonPanel2.add(redButton);
        buttonPanel2.add(blueButton);
        buttonPanel2.add(greenButton);
        buttonPanel2.add(splitButton);
        buttonPanel2.add(combineButton);
        buttonPanel2.add(SepiaButton);
        buttonPanel2.add(DitherButton);
        buttonPanel2.add(SharpenButton);
        buttonPanel2.add(BlurButton);
        buttonPanel2.add(greyscaleButton);
        buttonPanel2.add(slider);

        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.add(buttonPanel2, BorderLayout.NORTH);
        add(rightPanel, BorderLayout.EAST);

        JPanel histogramPanel = new JPanel();
        histogramPanel.setBorder(BorderFactory.createTitledBorder("HISTOGRAM"));
        histogramPanel.setPreferredSize(new Dimension(0, 250));
        imagePanel.add(histogramPanel, BorderLayout.SOUTH);
        add(imagePanel, BorderLayout.CENTER);

        JPanel errorPanel = new JPanel();
        errorPanel.setBorder(BorderFactory.createTitledBorder("LOG"));
        errorPanel.setPreferredSize(new Dimension(0, 250));
        imagePanel.add(errorPanel, BorderLayout.SOUTH);
        add(imagePanel, BorderLayout.CENTER);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, histogramPanel, errorPanel);
        splitPane.setResizeWeight(0.5); // adjust the divider position
        splitPane.setDividerLocation(0.5);
        imagePanel.add(splitPane, BorderLayout.SOUTH);

        add(buttonPanel2, BorderLayout.EAST);

        ViewI view = new ViewGUI(errorPanel);

        uploadButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int result = fileChooser.showOpenDialog(ImageProcessorUI.this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    imageName = "loadedImage";
                    File selectedFile = fileChooser.getSelectedFile();
                    String path = selectedFile.getPath();
                    String[] commands = new String[3];
                    commands[0] = "load";
                    commands[1] = path;
                    commands[2] = imageName;
                    try {
                        callbacks.executeFeatures(commands, view);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    TypeOfImage test = model.getObject(imageName);
                    loadImageOnscreen(test);

                }
            }
        });

        BrightenButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String[] commands = new String[4];
                //what about darken?
                commands[0] = "brighten";
                commands[1] = "10";
                commands[2] = imageName;
                imageName = "loadedImage-brighten";
                commands[3] = imageName;
                try {
                    callbacks.executeFeatures(commands, view);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                TypeOfImage testgrey = model.getObject(imageName);
                loadImageOnscreen(testgrey);
            }
        });

        ValueButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String[] commands = new String[4];
                commands[0] = "greyscale";
                commands[1] = "component-value";
                commands[2] = imageName;
                imageName = "loadedImage-Value";
                commands[3] = imageName;
                try {
                    callbacks.executeFeatures(commands, view);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                TypeOfImage testgrey = model.getObject(imageName);
                loadImageOnscreen(testgrey);
            }
        });

        intensityButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String[] commands = new String[4];
                commands[0] = "greyscale";
                commands[1] = "component-intensity";
                commands[2] = imageName;
                imageName = "loadedImage-intensity";
                commands[3] = imageName;
                try {
                    callbacks.executeFeatures(commands, view);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                TypeOfImage testgrey = model.getObject(imageName);
                loadImageOnscreen(testgrey);
            }
        });

        LumaButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String[] commands = new String[4];
                commands[0] = "greyscale";
                commands[1] = "component-luma";
                commands[2] = imageName;
                imageName = "loadedImage-luma";
                commands[3] = imageName;
                try {
                    callbacks.executeFeatures(commands, view);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                TypeOfImage testgrey = model.getObject(imageName);
                loadImageOnscreen(testgrey);
            }
        });

        horizontalFlipButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String[] commands = new String[3];
                commands[0] = "horizontal-flip";
                commands[1] = imageName;
                imageName = "loadedImage-HorizontalFlip";
                commands[2] = imageName;
                try {
                    callbacks.executeFeatures(commands, view);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                TypeOfImage testgrey = model.getObject(imageName);
                loadImageOnscreen(testgrey);
            }
        });

        verticalFlipButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String[] commands = new String[3];
                commands[0] = "vertical-flip";
                commands[1] = imageName;
                imageName = "loadedImage-VerticalFlip";
                commands[2] = imageName;
                try {
                    callbacks.executeFeatures(commands, view);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                TypeOfImage testgrey = model.getObject(imageName);
                loadImageOnscreen(testgrey);
            }
        });

        redButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String[] commands = new String[4];
                commands[0] = "greyscale";
                commands[1] = "component-red";
                commands[2] = imageName;
                imageName = "loadedImage-red";
                commands[3] = imageName;
                try {
                    callbacks.executeFeatures(commands, view);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                TypeOfImage testgrey = model.getObject(imageName);
                loadImageOnscreen(testgrey);
            }
        });

        blueButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String[] commands = new String[4];
                commands[0] = "greyscale";
                commands[1] = "component-blue";
                commands[2] = imageName;
                imageName = "loadedImage-blue";
                commands[3] = imageName;
                try {
                    callbacks.executeFeatures(commands, view);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                TypeOfImage testgrey = model.getObject(imageName);
                loadImageOnscreen(testgrey);
            }
        });

        greenButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String[] commands = new String[4];
                commands[0] = "greyscale";
                commands[1] = "component-green";
                commands[2] = imageName;
                imageName = "loadedImage-green";
                commands[3] = imageName;
                try {
                    callbacks.executeFeatures(commands, view);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                TypeOfImage testgrey = model.getObject(imageName);
                loadImageOnscreen(testgrey);
            }
        });

        splitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String[] commands = new String[5];
                //need more number of commands here
                //show 3 files on screen?
                //how to manipultae each
                //how to save each?

                commands[0] = "rgb-split";
                commands[1] = imageName;
                imageName = "loadedImage-red";
                commands[2] = imageName;
                commands[3] = "loadedImage-green";
                commands[4] = "loadedImage-blue";
                try {
                    callbacks.executeFeatures(commands, view);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                TypeOfImage testgrey = model.getObject(imageName);
                loadImageOnscreen(testgrey);

                new ImageProcessorUI(model, callbacks, "loadedImage-green");
                new ImageProcessorUI(model, callbacks, "loadedImage-blue");
            }
        });

        combineButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String[] commands = new String[3];
                //ask for 3 file names for load
                //when when user clickc on combine, ask them for input 2 more files.
                commands[0] = "Combine";
                commands[1] = imageName;
                imageName = "loadedImage-combine";
                commands[2] = imageName;
                try {
                    callbacks.executeFeatures(commands, view);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                TypeOfImage testgrey = model.getObject(imageName);
                loadImageOnscreen(testgrey);
            }
        });

        SepiaButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String[] commands = new String[3];
                commands[0] = "transform-sepia";
                commands[1] = imageName;
                imageName = "loadedImage-sepia";
                commands[2] = imageName;
                try {
                    callbacks.executeFeatures(commands, view);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                TypeOfImage testgrey = model.getObject(imageName);
                loadImageOnscreen(testgrey);
            }
        });

        DitherButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String[] commands = new String[3];
                commands[0] = "dither";
                commands[1] = imageName;
                imageName = "loadedImage-dither";
                commands[2] = imageName;
                try {
                    callbacks.executeFeatures(commands, view);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                TypeOfImage testgrey = model.getObject(imageName);
                loadImageOnscreen(testgrey);
            }
        });

        SharpenButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String[] commands = new String[3];
                commands[0] = "filter-sharpen";
                commands[1] = imageName;
                imageName = "loadedImage-sharpen";
                commands[2] = imageName;
                try {
                    callbacks.executeFeatures(commands, view);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                TypeOfImage testgrey = model.getObject(imageName);
                loadImageOnscreen(testgrey);
            }
        });

        BlurButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String[] commands = new String[3];
                commands[0] = "filter-blur";
                commands[1] = imageName;
                imageName = "loadedImage-blur";
                commands[2] = imageName;
                try {
                    callbacks.executeFeatures(commands, view);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                TypeOfImage testgrey = model.getObject(imageName);
                loadImageOnscreen(testgrey);
            }
        });

        greyscaleButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String[] commands = new String[3];
                commands[0] = "transform-greyscale";
                commands[1] = imageName;
                imageName = "loadedImage-greyscale";
                commands[2] = imageName;
                try {
                    callbacks.executeFeatures(commands, view);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                TypeOfImage testgrey = model.getObject(imageName);
                loadImageOnscreen(testgrey);
            }
        });

        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Choose a directory to save the image");
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int result = fileChooser.showSaveDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    String filePath = fileChooser.getSelectedFile().getAbsolutePath();
                    String[] commands = new String[3];
                    commands[0] = "save";
                    commands[1] = filePath;
                    commands[2] = imageName;
                    try {
                        callbacks.executeFeatures(commands, view);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });

        slider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                int value = slider.getValue();
                String[] commands = new String[4];
                //what about darken?
                commands[0] = "brighten";
                commands[1] = Integer.toString(value);
                commands[2] = imageName;
                imageName = "loadedImage-brighten";
                commands[3] = imageName;
                try {
                    callbacks.executeFeatures(commands, view);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                TypeOfImage testgrey = model.getObject(imageName);
                loadImageOnscreen(testgrey);
            }
        });

        if (!imageName.isEmpty()) {
            System.out.print(imageName);
            TypeOfImage testgrey = model.getObject(imageName);
            System.out.print(testgrey);
            loadImageOnscreen(testgrey);
        }

        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }


    public void loadImageOnscreen(TypeOfImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage imageBuffer;

        if (image.getPixels()[0][0].hasAlpha() == null) {
            imageBuffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            for (int y = 0; y < image.getHeight(); y++) {
                for (int x = 0; x < image.getWidth(); x++) {
                    TypeofImageObject pixel = image.getPixels()[x][y];
                    if (pixel != null) {
                        int red = pixel.getChanne11();
                        int green = pixel.getChanne12();
                        int blue = pixel.getChanne13();
                        int rgb = (red << 16) | (green << 8) | blue;

                        imageBuffer.setRGB(x, y, rgb);
                    }
                }
            }
        } else {
            imageBuffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            //saving for image with alpha channel
            for (int y = 0; y < image.getHeight(); y++) {
                for (int x = 0; x < image.getWidth(); x++) {
                    TypeofImageObject pixel = image.getPixels()[x][y];
                    if (pixel != null) {
                        int alpha = 255; // set alpha to 255 (fully opaque)
                        int red = pixel.getChanne11();
                        int green = pixel.getChanne12();
                        int blue = pixel.getChanne13();
                        int argb = (alpha << 24) | (red << 16) | (green << 8) | blue;
                        imageBuffer.setRGB(x, y, argb);
                    }
                }
            }
        }
        ImageIcon icon = new ImageIcon(imageBuffer);
        imageLabel.setIcon(icon);

    }

}