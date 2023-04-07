package view;

import javax.swing.*;
import java.awt.*;

public class Histogram extends JPanel {
    private int[] histogramData; // the histogram data
    private int imageSize; // the size of the image

    public Histogram(int[] histogramData, int imageSize) {
        this.histogramData = histogramData;
        this.imageSize = imageSize;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        int histogramWidth = getWidth() - 10;
        int histogramHeight = getHeight() - 10;
        int barWidth = histogramWidth / histogramData.length;
        int maxValue = 0;

        // find the maximum frequency in the histogram
        for (int i = 0; i < histogramData.length; i++) {
            if (histogramData[i] > maxValue) {
                maxValue = histogramData[i];
            }
        }

        // draw the bars of the histogram
        g.setColor(Color.BLACK);
        for (int i = 0; i < histogramData.length; i++) {
            int barHeight = (int) (((double) histogramData[i] / (double) maxValue) * histogramHeight);
            int x = i * barWidth + 5;
            int y = histogramHeight - barHeight + 5;
            g.fillRect(x, y, barWidth, barHeight);
        }
    }
}
