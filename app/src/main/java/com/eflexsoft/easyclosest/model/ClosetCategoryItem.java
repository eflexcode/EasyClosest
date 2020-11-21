package com.eflexsoft.easyclosest.model;

import java.util.List;

public class ClosetCategoryItem {

    private String categoryName;
    private String categorySize;
//    private List<ClosetItem> closetItem;

    public ClosetCategoryItem(String categoryName, String categorySize) {
        this.categoryName = categoryName;
        this.categorySize = categorySize;
//        this.closetItem = closetItem;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategorySize() {
        return categorySize;
    }

    public void setCategorySize(String categorySize) {
        this.categorySize = categorySize;
    }

//    public List<ClosetItem> getClosetItem() {
//        return closetItem;
//    }
//
//    public void setClosetItem(List<ClosetItem> closetItem) {
//        this.closetItem = closetItem;
//    }
}
