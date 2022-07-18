package com.example.disasterpreventionmanagement.safetytips;

public class Item {

    private int imageResourse;
    private String title;
    private String desc;
    private boolean isShrink = true;
    private String link;

    public Item() {
    }

    public Item(int imageResourse, String title, String desc, String link) {
        this.imageResourse = imageResourse;
        this.title = title;
        this.desc = desc;
        this.link = link;
    }

    public int getImageResourse() {
        return imageResourse;
    }

    public void setImageResourse(int imageResourse) {
        this.imageResourse = imageResourse;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public boolean isShrink() {
        return isShrink;
    }

    public void setShrink(boolean shrink) {
        isShrink = shrink;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
