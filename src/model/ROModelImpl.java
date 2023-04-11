package model;

public class ROModelImpl implements ROModel{

    Operations model;

    public ROModelImpl(Operations Model) {
        this.model = Model;
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
