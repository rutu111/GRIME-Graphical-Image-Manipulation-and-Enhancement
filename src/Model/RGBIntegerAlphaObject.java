package Model;

import java.util.Objects;

public class RGBIntegerAlphaObject implements TypeofImageObject {

  protected final Integer red;
  protected final Integer green;
  protected final Integer blue;
  public final Integer alpha;


  /**
   * @param red   the value of the red component.
   * @param green value of the green component.
   * @param blue  value of the blue component.
   * @param alpha
   * @throws IllegalArgumentException throws if illegal values of RGB provided.
   */
  public RGBIntegerAlphaObject(Integer red, Integer green, Integer blue, Integer alpha)
      throws IllegalArgumentException {
    if (red < 0 | green < 0 | blue < 0) {
      throw new IllegalArgumentException("RGB values can't be less negative!");
    }
    if (red > 255 | green > 255 | blue > 255) {
      throw new IllegalArgumentException("RGB values can't be above 255!");
    }
    this.red = red;
    this.green = green;
    this.blue = blue;
    this.alpha = alpha;

  }


  @Override
  public Integer getChanne11() {
    return red;
  }

  @Override
  public Integer getChanne12() {
    return this.green;
  }

  @Override
  public Integer getChanne13() {
    return this.blue;
  }

  public Integer hasAlpha() {
    return this.alpha;
  }

  /**
   * Equals method checks if two RGBIntegerObject integer
   * objects are the same.
   *
   * @param o which is object we want to compare with.
   * @return true or false.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (!(o instanceof RGBIntegerAlphaObject)) {
      return false;
    }

    RGBIntegerAlphaObject other = (RGBIntegerAlphaObject) o;
    return Objects.equals(this.getChanne11(), other.getChanne11()) & Objects.equals(
        this.getChanne12(), other.getChanne12())
        & Objects.equals(this.getChanne13(), other.getChanne13()) &
        Objects.equals(this.alpha, other.alpha);
  }
}
