package com.getcodly.codly;

public class UploadProfileImage {
    private String mName;
    private String mImageUrl;

    public UploadProfileImage(){

    }

    public UploadProfileImage(String name, String imageUrl){
        if (name.trim().equals("")){
            name = "unnamed";
        }

        mName = name;
        mImageUrl = imageUrl;
    }

    public String getName(){
        return mName;
    }

    public void setName(String name){
        mName = name;
    }

    public String getImageUrl(){
        return mImageUrl;
    }

    public void setImageUrl(String imageUrl){
        mImageUrl = imageUrl;
    }
}
