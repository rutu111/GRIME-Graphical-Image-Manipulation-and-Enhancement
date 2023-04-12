package model;

/**
 * Implementation of the ROmodel.
 * Takes in a model object - composition.
 */
public class ROModelImpl implements ROModel {

  Operations model;

  /**
   * Takes in the Model object as input.
   *
   * @param model model object.
   */
  public ROModelImpl(Operations model) {
    this.model = model;
  }

  @Override
  public TypeOfImage getObject(String imageName) {
    return model.getObject(imageName);
  }

  @Override
  public int numberOfImagesInModel() {
    return model.numberOfImagesInModel();
  }

  @Override
  public boolean checkKeyInHashmap(String imageName) {
    return model.checkKeyInHashmap(imageName);
  }
}
