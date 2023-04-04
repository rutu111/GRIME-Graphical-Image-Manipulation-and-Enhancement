package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class ImageProcessorUI extends JFrame {

    private JButton uploadButton;
    private JButton saveButton;
    private JLabel imageLabel;
    private JFileChooser fileChooser;

    public ImageProcessorUI() {

        super();
        this.setTitle("Image Processor");
        this.setSize(10000, 10000);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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

        JButton brightenButton = new JButton("Brighten");
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
        imagePanel.add(errorPanel, BorderLayout.SOUTH);
        add(imagePanel, BorderLayout.CENTER);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, histogramPanel, errorPanel);
        splitPane.setResizeWeight(0.5); // adjust the divider position
        splitPane.setDividerLocation(0.5);
        imagePanel.add(splitPane, BorderLayout.SOUTH);


        uploadButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int result = fileChooser.showOpenDialog(ImageProcessorUI.this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    ImageIcon imageIcon = new ImageIcon(selectedFile.getPath());
                    imageLabel.setIcon(imageIcon);
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

    public static void main(String[] args) {
        new ImageProcessorUI();
    }


}
