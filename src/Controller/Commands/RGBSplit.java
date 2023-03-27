package Controller.Commands;

import Controller.CommandDesignOperations;
import Model.Operations;

public class RGBSplit implements CommandDesignOperations {

  String imageName;

  String updatedimageName1;

  String updatedimageName2;
  String updatedimageName3;

  public RGBSplit(String imageName, String updatedimageName1, String updatedimageName2, String updatedimageName3) {
    this.imageName = imageName;
    this.updatedimageName1 = updatedimageName1;
    this.updatedimageName2 = updatedimageName2;
    this.updatedimageName3 = updatedimageName3;
  }
  @Override
  public void go(Operations m) throws NoSuchFieldException, IllegalAccessException {
    m.splitInto3Images(imageName, updatedimageName1, updatedimageName2,
        updatedimageName3);
  }
}
