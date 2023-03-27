package Controller.Commands;

import Controller.CommandDesignOperations;
import Model.MeasurementType;
import Model.Operations;

public class ValueIntensityLuma implements CommandDesignOperations {

  private MeasurementType measure;
  private String imageName;

  private String updatedImageName;


  public ValueIntensityLuma(String imageName, String updatedImageName, MeasurementType measure) {
    this.imageName =  imageName;
    this.updatedImageName = updatedImageName;
    this.measure = measure;
  }
  @Override
  public void go(Operations m) {
    m.visualizeValueIntensityLuma(imageName, updatedImageName, measure);
  }
}
