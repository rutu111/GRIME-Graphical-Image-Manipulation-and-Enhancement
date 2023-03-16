package Model;

import java.util.Objects;

public class RGBIntegerObject implements TypeofImageObject {

  protected final Integer red;
  protected final Integer green;
  protected final Integer blue;

  public RGBIntegerObject(Integer red, Integer green, Integer blue) throws IllegalArgumentException {
    if (red < 0 | green < 0 | blue < 0) {
      throw new IllegalArgumentException("RGB values can't be less negative!");
    }
    if (red > 255 | green > 255 | blue > 255) {
      throw new IllegalArgumentException("RGB values can't be above 255!");
    }
    this.red = red;
    this.green = green;
    this.blue = blue;
  }


  @Override
  public Integer getChanne11() {
    return this.red;
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
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (!(o instanceof RGBIntegerObject)) {
      return false;
    }

    RGBIntegerObject other = (RGBIntegerObject) o;
    return Objects.equals(this.getChanne11(), other.getChanne11()) & Objects.equals(
        this.getChanne12(), other.getChanne12())
        & Objects.equals(this.getChanne13(), other.getChanne13());
  }
}
