package Controller.Commands;

import Controller.CommandDesignOperations;
import Model.Operations;

public class Combine implements CommandDesignOperations {

  private String imageName1;
  private String imageName2;
  private String imageName3;
  private String updatedimageName;

  public Combine(String imageName1, String imageName2, String imageName3, String updatedimageName){
    this.imageName1 = imageName1;
    this.imageName2 = imageName2;
    this.imageName3 = imageName3;
    this.updatedimageName = updatedimageName;
  }
  @Override
  public void go(Operations m)  {
    m.combineGreyScaleToRGB(imageName1, imageName2, imageName3, updatedimageName);
  }
}
