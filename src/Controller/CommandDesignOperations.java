package Controller;


import Model.Operations;

public interface CommandDesignOperations {
  void go(Operations m) throws NoSuchFieldException, IllegalAccessException;

}
