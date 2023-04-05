package view;

import controller.CommandDesignOperations;
import controller.ImageProcessCallbacks;
import controller.commands.Load;
import model.Model;
import model.Operations;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class ImageProcessorUI extends JFrame implements ViewI {

    private final JButton uploadButton,saveButton,brightenButton;
    private JLabel imageLabel;
    private JFileChooser fileChooser;
    private Operations m;
    private ViewI view;
    JTextArea logTextArea;
    ImageProcessCallbacks callbacks;
    public ImageProcessorUI(ImageProcessCallbacks callbacks) {

        super();
        this.setTitle("Image Processor");
        this.setSize(10000, 10000);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.m = new Model();

        uploadButton = new JButton("Upload");
        saveButton = new JButton("Save");
        imageLabel = new JLabel();
        fileChooser = new JFileChooser();

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(uploadButton);
        buttonPanel.add(saveButton);

        add(buttonPanel, BorderLayout.NORTH);
        add(imageLabel, BorderLayout.CENTER);

        JPanel imagePanel = new JPanel(new BorderLayout());
        imagePanel.add(imageLabel, BorderLayout.CENTER);

        JPanel operationsPanel = new JPanel();
        operationsPanel.setLayout(new GridLayout(18, 2, 20, 20)); // 5 rows, 3 columns, gap of 5 pixels
        operationsPanel.setBorder(BorderFactory.createTitledBorder("OPERATIONS"));

        brightenButton = new JButton("Brighten");
        brightenButton.setActionCommand("Brighten button");
        this.add(brightenButton);
        brightenButton.addActionListener(e -> {
            try {
                callbacks.executeFeatures("brighten 10 koala koala-brighten");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            System.out.println("Success");
        });

        JButton valueButton = new JButton("Value");
        JButton intensityButton = new JButton("Intensity");
        JButton lumaButton = new JButton("Luma");
        JButton horizontalFlipButton = new JButton("Horizontal Flip");
        JButton verticalFlipButton = new JButton("Vertical Flip");
        JButton redButton = new JButton("Red");
        JButton blueButton = new JButton("Blue");
        JButton greenButton = new JButton("Green");
        JButton splitButton = new JButton("Split");
        JButton combineButton = new JButton("Combine");
        JButton sepiaButton = new JButton("Sepia");
        JButton ditherButton = new JButton("Dither");
        JButton sharpenButton = new JButton("Sharpen");
        JButton blurButton = new JButton("Blur");
        JButton greyscaleButton = new JButton("Greyscale");

        // Add the buttons to the operationsPanel
        operationsPanel.add(brightenButton);
        operationsPanel.add(valueButton);
        operationsPanel.add(intensityButton);
        operationsPanel.add(lumaButton);
        operationsPanel.add(horizontalFlipButton);
        operationsPanel.add(verticalFlipButton);
        operationsPanel.add(redButton);
        operationsPanel.add(blueButton);
        operationsPanel.add(greenButton);
        operationsPanel.add(splitButton);
        operationsPanel.add(combineButton);
        operationsPanel.add(sepiaButton);
        operationsPanel.add(ditherButton);
        operationsPanel.add(sharpenButton);
        operationsPanel.add(blurButton);
        operationsPanel.add(greyscaleButton);

        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.add(operationsPanel, BorderLayout.NORTH);
        add(rightPanel, BorderLayout.EAST);

        JPanel histogramPanel = new JPanel();
        histogramPanel.setBorder(BorderFactory.createTitledBorder("HISTOGRAM"));
        histogramPanel.setPreferredSize(new Dimension(0, 250));
        imagePanel.add(histogramPanel, BorderLayout.SOUTH);
        add(imagePanel, BorderLayout.CENTER);

        JPanel errorPanel = new JPanel();
        errorPanel.setBorder(BorderFactory.createTitledBorder("LOG"));
        errorPanel.setPreferredSize(new Dimension(0, 250));
        logTextArea = new JTextArea();
        logTextArea.setEditable(true);
        JScrollPane logScrollPane = new JScrollPane(logTextArea);
        errorPanel.add(logScrollPane, BorderLayout.CENTER);
        add(errorPanel, BorderLayout.CENTER);
        imagePanel.add(errorPanel, BorderLayout.SOUTH);
        add(imagePanel, BorderLayout.CENTER);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, histogramPanel, errorPanel);
        splitPane.setResizeWeight(0.5); // adjust the divider position
        splitPane.setDividerLocation(0.5);
        imagePanel.add(splitPane, BorderLayout.SOUTH);


        uploadButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int result = fileChooser.showOpenDialog(ImageProcessorUI.this);
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    String imagePath = selectedFile.getAbsolutePath();
                    String imageName = selectedFile.getName();
                    String[] loadCommand = {"load", imagePath, imageName};
                    CommandDesignOperations load = new Load(loadCommand);
                    try {
                        load.goCommand(m, view);
                    } catch (IOException | NoSuchFieldException | IllegalAccessException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Perform image processing operations here
            }
        });

        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }


    @Override
    public void printWelcomeMessage() {

    }

    @Override
    public void printOutput(String output) throws IOException {
        logTextArea.append(output + "\n");
    }

    @Override
    public void addFeatures(ImageProcessCallbacks callbacks) {
//        brightenButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                callbacks.executeFeatures("brighten koala koala-brighten");
//                try {
//                    printOutput("Success");
//                } catch (IOException ex) {
//                    throw new RuntimeException(ex);
//                }
//            }
//        });
    }

}
