package com.example.sj.stylestockprojects.Recommend;

import android.graphics.drawable.Drawable;

import com.bumptech.glide.load.resource.transcode.GifDrawableBytesTranscoder;

public class MyItem {
    private Drawable icon;
    private String name;
    private String contents;

    public String getImgTest() {
        return imgTest;
    }

    public void setImgTest(String imgTest) {
        this.imgTest = imgTest;
    }

    private String imgTest;

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }


}
