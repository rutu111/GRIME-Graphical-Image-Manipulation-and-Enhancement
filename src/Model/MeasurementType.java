package Model;

public enum MeasurementType {
  value("value"),
  intensity("intensity"),
  luma("luma");

  private final String measure;

  MeasurementType(String measure) {
    this.measure = measure;
  }

  @Override
  public String toString() {
    return measure;
  }
}
