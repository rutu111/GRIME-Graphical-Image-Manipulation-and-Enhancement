package model;

/**
 * The following interface represents a TypeofImageObject.
 * The image object could be have any number
 * of channels and each channel can be represented as any type:
 * int, bytes, double etc.
 */
public interface TypeofImageObject {

  /**
   * Gets the value held inside the first channel.
   *
   * @return value inside first channel;
   */
  Integer getChanne11();

  /**
   * Gets the value held inside the third channel.
   *
   * @return value inside second channel;
   */
  Integer getChanne12();

  /**
   * Gets the value held inside the third channel.
   *
   * @return value inside third channel;
   */
  Integer getChanne13();

  /**
   * Method returns the alpha value if object has one.
   *
   * @return alpha value if exists. Otherwise, return null.
   */
  Integer hasAlpha();

  /**
   * Hashcode method checks if two RGBIntegerObject integer
   * objects are the same.
   */
  int hashCode();
}
