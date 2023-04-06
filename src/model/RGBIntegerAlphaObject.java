package model;

import java.util.Objects;

/**
 * The following class represents an RGBIntegerAlphaObject.
 */
public class RGBIntegerAlphaObject implements TypeofImageObject {

  public final Integer alpha;
  protected final Integer red;
  protected final Integer green;
  protected final Integer blue;


  /**
   * This constructor creates a new RGNAlpha object.
   *
   * @param red   the value of the red component.
   * @param green value of the green component.
   * @param blue  value of the blue component.
   * @param alpha value of the alpha channel.
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

  @Override
  public Integer hasAlpha() {
    return this.alpha;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  /**
   * Equals method checks if two RGBIntegerAlpha objects are the same.
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
        & Objects.equals(this.getChanne13(), other.getChanne13())
        & Objects.equals(this.alpha, other.alpha);
  }
}
