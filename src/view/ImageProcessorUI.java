package view;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.AWTEventListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.NoSuchElementException;

import controller.ImageProcessCallbacks;
import controller.commands.Load;
import controller.commands.Save;
import controller.commands.TransformGreyscale;
import model.Operations;
import model.TypeOfImage;
import model.TypeofImageObject;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;


public class ImageProcessorUI extends JFrame {

    private  String imageName;
    JPanel imagePanel;
    JPanel histogramPanel;
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

        int[] histogramData; // the histogram data
        int imageSize; // the size of the image

        uploadButton = new JButton("Load");
        saveButton = new JButton("Save");
        imageLabel = new JLabel();
        fileChooser = new JFileChooser();
        //JSlider slider = new JSlider(JSlider.HORIZONTAL, -100, 100, 0);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(uploadButton);
        buttonPanel.add(saveButton);

        //add(buttonPanel, BorderLayout.NORTH);
        //add(imageLabel, BorderLayout.CENTER);
        //imagePanel.setPreferredSize(new Dimension(800, 600));

        //JPanel imagePanel = new JPanel(new BorderLayout());
        //imagePanel.add(imageLabel, BorderLayout.CENTER);


        //ImagePanel
        imagePanel = new JPanel();
        imagePanel.setBorder(BorderFactory.createTitledBorder("Image Panel"));
        imagePanel.setLayout(new BorderLayout());
        this.add(imagePanel, BorderLayout.CENTER);

        //ImageViewPanel
        JPanel imageViewPanel = new JPanel();
        imageViewPanel.setLayout(new BorderLayout());
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        imageLabel.setVerticalAlignment(JLabel.CENTER);
        imageViewPanel.add(imageLabel, BorderLayout.CENTER);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(imageViewPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        imagePanel.add(scrollPane, BorderLayout.CENTER);


        //scrollPane.add(imageViewPanel, BorderLayout.CENTER);
        //imagePanel.add(scrollPane);


// Add buttonPanel to the center of a new panel
        JPanel newPanel = new JPanel(new BorderLayout());
        newPanel.add(buttonPanel, BorderLayout.NORTH);

// Add newPanel to the top of buttonPanel2 with spacing
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        mainPanel.add(newPanel, BorderLayout.NORTH);
        mainPanel.add(Box.createVerticalStrut(50)); // Add spacing

// Add all buttons to buttonPanel2 below the new panel with spacing
        JPanel buttonPanel2 = new JPanel(new GridLayout(18, 1, 10, 10));
        buttonPanel2.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 50));
        buttonPanel2.setBorder(BorderFactory.createTitledBorder("OPERATIONS"));

        JButton BrightenButton = new JButton("Brighten");
        JButton DarkenButton = new JButton("Darken");
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

        buttonPanel2.add(mainPanel); // Add the new panel with spacing
        buttonPanel2.add(BrightenButton);
        buttonPanel2.add(DarkenButton);
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

        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.add(buttonPanel2, BorderLayout.NORTH);
        add(rightPanel, BorderLayout.EAST);

        // Create the button panel and add the buttons


        histogramPanel = new JPanel();
        histogramPanel.setBorder(BorderFactory.createTitledBorder("HISTOGRAM"));
        histogramPanel.setPreferredSize(new Dimension(0, 375));
        histogramPanel.setLayout(new GridLayout(1, 0, 10, 10));
        imagePanel.add(histogramPanel, BorderLayout.SOUTH);
        add(imagePanel, BorderLayout.CENTER);

        JPanel errorPanel = new JPanel();
        errorPanel.setBorder(BorderFactory.createTitledBorder("LOG"));
        errorPanel.setPreferredSize(new Dimension(0, 375));




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
                    if (model.checkKeyInHashmap(imageName)) {
                        TypeOfImage test = model.getObject(imageName);
                        loadImageOnscreen(test);
                        histogram(test);
                    }

                }
            }
        });

        BrightenButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String[] commands = new String[4];
                if (model.checkKeyInHashmap(imageName)) {
                    //what about darken?
                    commands[0] = "brighten";
                    commands[1] = "10";
                    commands[2] = imageName;
                    System.out.println(imageName);
                    imageName = "loadedImage-brighten";
                    commands[3] = imageName;
                    try {
                        callbacks.executeFeatures(commands, view);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    TypeOfImage testgrey = model.getObject(imageName);
                    loadImageOnscreen(testgrey);
                    histogram(testgrey);
                }
            }
        });

        DarkenButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (model.checkKeyInHashmap(imageName)) {
                    String[] commands = new String[4];
                    //what about darken?
                    commands[0] = "brighten";
                    commands[1] = "-10";
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
                    histogram(testgrey);
                }
            }
        });

        ValueButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (model.checkKeyInHashmap(imageName)) {
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
                    histogram(testgrey);
                }
            }
        });

        intensityButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (model.checkKeyInHashmap(imageName)) {
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
                    histogram(testgrey);
                }
            }
        });

        LumaButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (model.checkKeyInHashmap(imageName)) {
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
                    histogram(testgrey);
                }
            }
        });

        horizontalFlipButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (model.checkKeyInHashmap(imageName)) {
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
                    histogram(testgrey);
                }
            }
        });

        verticalFlipButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (model.checkKeyInHashmap(imageName)) {
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
                    histogram(testgrey);
                }
            }
        });

        redButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (model.checkKeyInHashmap(imageName)) {
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
                    histogram(testgrey);
                }
            }
        });

        blueButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (model.checkKeyInHashmap(imageName)) {
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
                    histogram(testgrey);
                }
            }
        });

        greenButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (model.checkKeyInHashmap(imageName)) {
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
                    histogram(testgrey);
                }
            }
        });

        splitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (model.checkKeyInHashmap(imageName)) {
                    String[] commands = new String[5];

                    commands[0] = "rgb-split";
                    commands[1] = imageName;
                    imageName = "loadedImage-red";
                    commands[2] = imageName;
                    commands[3] = "loadedImage-green";
                    commands[4] = "loadedImage-blue";
                    System.out.println(imageName);
                    try {
                        callbacks.executeFeatures(commands, view);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    TypeOfImage testgrey = model.getObject(imageName);
                    loadImageOnscreen(testgrey);
                    histogram(testgrey);

                    new ImageProcessorUI(model, callbacks, "loadedImage-green");
                    new ImageProcessorUI(model, callbacks, "loadedImage-blue");
                }
            }
        });

        combineButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (model.checkKeyInHashmap(imageName)) {
                    String[] commands = new String[5];
                    int result = fileChooser.showOpenDialog(ImageProcessorUI.this);
                    if (result == JFileChooser.APPROVE_OPTION) {
                        File selectedFile = fileChooser.getSelectedFile();
                        String path = selectedFile.getPath();
                        String[] commands1 = new String[3];
                        commands1[0] = "load";
                        commands1[1] = path;
                        commands1[2] = "loadedImage2";
                        try {
                            callbacks.executeFeatures(commands1, view);
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    }

                    int result2 = fileChooser.showOpenDialog(ImageProcessorUI.this);
                    if (result2 == JFileChooser.APPROVE_OPTION) {
                        File selectedFile = fileChooser.getSelectedFile();
                        String path = selectedFile.getPath();
                        String[] commands2 = new String[3];
                        commands2[0] = "load";
                        commands2[1] = path;
                        commands2[2] = "loadedImage3";
                        try {
                            System.out.println();
                            callbacks.executeFeatures(commands2, view);
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }

                    }
                    if (model.checkKeyInHashmap(imageName) & model.checkKeyInHashmap("loadedImage2") & model.checkKeyInHashmap("loadedImage3")) {
                        commands[0] = "rgb-combine";
                        commands[2] = imageName;
                        commands[3] = "loadedImage2";
                        commands[4] = "loadedImage3";
                        imageName = "loadedImage-combine";
                        commands[1] = imageName;

                        try {
                            callbacks.executeFeatures(commands, view);
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }

                        TypeOfImage testgrey = model.getObject(imageName);
                        loadImageOnscreen(testgrey);
                        histogram(testgrey);
                }
            }
            }
        });

        SepiaButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (model.checkKeyInHashmap(imageName)) {
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
                    histogram(testgrey);
                }
            }
        });

        DitherButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (model.checkKeyInHashmap(imageName)) {
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
                    histogram(testgrey);
                }

            }
        });

        SharpenButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (model.checkKeyInHashmap(imageName)) {
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
                    histogram(testgrey);
                }
            }
        });

        BlurButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (model.checkKeyInHashmap(imageName)) {
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
                    histogram(testgrey);
                }
            }
        });

        greyscaleButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (model.checkKeyInHashmap(imageName)) {
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
                    histogram(testgrey);
                }
            }
        });

        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (model.checkKeyInHashmap(imageName)) {
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
            }
        });

        if (!imageName.isEmpty()) {
            TypeOfImage testgrey = model.getObject(imageName);
            loadImageOnscreen(testgrey);
            histogram(testgrey);
        }

        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

    }


    public void histogram(TypeOfImage image) {

        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage imageBuffer;

        imageBuffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                TypeofImageObject pixel = image.getPixels()[x][y];
                if (pixel != null) {
                    int red = pixel.getChanne11();
                    int green = pixel.getChanne12();
                    int blue = pixel.getChanne13();
                    int intensity = (int) (red + green + blue) / 3;
                    int argb = (intensity << 24) | (red << 16) | (green << 8) | blue;
                    imageBuffer.setRGB(x, y, argb);
                }
            }
        }

        //make sure intensity is also there where creating the histogram.

        Histogram histogramObject = new Histogram(imageBuffer);
        ChartPanel chartPanel = histogramObject.createChartPanel();
        histogramPanel.removeAll();
        histogramPanel.add(chartPanel);

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