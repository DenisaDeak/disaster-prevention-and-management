package com.example.disasterpreventionmanagement.travel;

public class disasterData  {

    private String dTitle;
    private String dLink;

    public disasterData(String title, String link) {

        this.dTitle = title;
        this.dLink = link;
    }

    public String getdTitle() {
        return dTitle;
    }

    public void setdTitle(String dTitle) {
        this.dTitle = dTitle;
    }

    public String getdLink() {
        return dLink;
    }

    public void setdLink(String dLink) {
        this.dLink = dLink;
    }

    @Override
    public String toString() {

        return this.dTitle;
    }
}
