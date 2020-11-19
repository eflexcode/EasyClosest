package com.eflexsoft.easyclosest.model;

public class ClosetItem {

    private long id;
    private String imageUrl;
    private String category;
    private String season;
    private String note;

    public ClosetItem() {
    }

    public ClosetItem(long id, String imageUrl, String category, String season, String note) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.category = category;
        this.season = season;
        this.note = note;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
