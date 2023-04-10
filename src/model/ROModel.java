package model;

public interface ROModel {

    /**
     * This methodw gets the object by keyname from the hashmap if one exists.
     *
     * @param imageName name of the image.
     * @return TypeOfImage object.
     */
    TypeOfImage getObject(String imageName);

    /**
     * This methods outputs the number of images in the hashmap.
     *
     * @return number of images in hashmap.
     */
    int numberOfImagesInModel();


    /**
     * Method checks if requested key is in the hashmap. If yes, then returns
     * true. Else, returns
     * false.
     *
     * @param imageName key to look for.
     * @return boolean.
     */
    boolean checkKeyInHashmap(String imageName);
}
