package view;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.DefaultDrawingSupplier;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYBarPainter;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.data.statistics.HistogramDataset;

import java.awt.Paint;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;

/**
 * The following class is usedto create histograms on the GUI.
 */
public class Histogram {
  private static final int BINS = 256;
  private final BufferedImage image;

  /**
   * The following constructor takes in a buffer image
   * as ar argument and sets it.
   *
   * @param image buffered image.
   */
  public Histogram(BufferedImage image) {
    this.image = image;
  }

  /**
   * This function creates a chart panel containing
   * a histogram with red, blue, green and intensity line graphs.
   *
   * @return a chart panel.
   */
  public ChartPanel createChartPanel() {
    // dataset
    HistogramDataset dataset = new HistogramDataset();
    Raster raster = image.getRaster();
    final int w = image.getWidth();
    final int h = image.getHeight();
    double[] r = new double[w * h];
    r = raster.getSamples(0, 0, w, h, 0, r);
    dataset.addSeries("Intensity", r, BINS);
    r = raster.getSamples(0, 0, w, h, 1, r);
    dataset.addSeries("Red", r, BINS);
    r = raster.getSamples(0, 0, w, h, 2, r);
    dataset.addSeries("Green", r, BINS);
    r = raster.getSamples(0, 0, w, h, 3, r);
    dataset.addSeries("Blue", r, BINS);
    // chart
    JFreeChart chart = ChartFactory.createHistogram("Histogram", "Value",
        "Count", dataset, PlotOrientation.VERTICAL, true, true, false);
    XYPlot plot = (XYPlot) chart.getPlot();
    XYBarRenderer renderer = (XYBarRenderer) plot.getRenderer();
    renderer.setBarPainter(new StandardXYBarPainter());
    // translucent red, green & blue
    Paint[] paintArray = {
        new Color(0x80FFFF00, true),
        new Color(0x80ff0000, true),
        new Color(0x8000ff00, true),
        new Color(0x800000ff, true)


    };
    plot.setDrawingSupplier(new DefaultDrawingSupplier(
        paintArray,
        DefaultDrawingSupplier.DEFAULT_FILL_PAINT_SEQUENCE,
        DefaultDrawingSupplier.DEFAULT_OUTLINE_PAINT_SEQUENCE,
        DefaultDrawingSupplier.DEFAULT_STROKE_SEQUENCE,
        DefaultDrawingSupplier.DEFAULT_OUTLINE_STROKE_SEQUENCE,
        DefaultDrawingSupplier.DEFAULT_SHAPE_SEQUENCE));
    ChartPanel panel = new ChartPanel(chart);
    panel.setMouseWheelEnabled(true);
    return panel;
  }
}