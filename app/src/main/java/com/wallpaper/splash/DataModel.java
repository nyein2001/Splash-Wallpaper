package com.wallpaper.splash;

public class DataModel {

    public String imageUrl;
    public String imageName;
    public String category;

    public DataModel(){


    }

    public DataModel(String imageUrl,String imageName,String category){
        this.imageUrl = imageUrl;
        this.imageName = imageName;
        this.category = category;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}

