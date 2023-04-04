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

        uploadButton = new JButton("Load");
        saveButton = new JButton("Save");
        imageLabel = new JLabel();
        fileChooser = new JFileChooser();


        JPanel buttonPanel = new JPanel();
        buttonPanel.add(uploadButton);
        buttonPanel.add(saveButton);

        // Create the button panel and add the buttons
        JPanel buttonPanel2 = new JPanel(new GridLayout(16, 1, 10, 10));
        buttonPanel2.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 50));

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

        add(buttonPanel, BorderLayout.NORTH);
        add(imageLabel, BorderLayout.CENTER);
        add(buttonPanel2, BorderLayout.EAST);



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
