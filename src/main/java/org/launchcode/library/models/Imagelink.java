package org.launchcode.library.models;

public class Imagelink {
    private String thumbnail;
    private String small;
    private String medium;

    public Imagelink(String thumbnail, String small, String medium) {
        this.thumbnail = thumbnail;
        this.small = small;
        this.medium = medium;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getSmall() {
        return small;
    }

    public void setSmall(String small) {
        this.small = small;
    }

    public String getMedium() {
        return medium;
    }

    public void setMedium(String medium) {
        this.medium = medium;
    }
}
