public enum MeasurementType {
  Value("value"),
  Intensity("intensity"),
  Luma("luma");

  private final String measure;

  MeasurementType(String measure) {
    this.measure = measure;
  }

  @Override
  public String toString() {
    return measure;
  }
}
