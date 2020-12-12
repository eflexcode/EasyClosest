package com.eflexsoft.easyclosest.model;

public class UpdateImage {

    private String name;
    private String url;
    private int position;
    private long id;

    public UpdateImage(String name, String url, int position, long id) {
        this.name = name;
        this.url = url;
        this.position = position;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
