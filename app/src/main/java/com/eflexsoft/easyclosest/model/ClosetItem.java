package com.eflexsoft.easyclosest.model;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.eflexsoft.easyclosest.R;
import com.rishabhharit.roundedimageview.RoundedImageView;

public class ClosetItem {

    private long id;
    private String imageUrl;
    private String category;
    private String season;
    private String note;
    private String userId;
    private boolean favourite;

    public ClosetItem() {
    }

    public ClosetItem(long id, String imageUrl, String category, String season, String note,boolean favourite,String userId) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.category = category;
        this.season = season;
        this.note = note;
        this.favourite = favourite;
        this.userId = userId;
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

    public boolean isFavourite() {
        return favourite;
    }

    public void setFavourite(boolean favourite) {
        this.favourite = favourite;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @BindingAdapter("android:setItemImage")
    public static void setItemImage(RoundedImageView roundedImageView,String imageUrl){

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.color.brown);
        requestOptions.error(R.color.brown);
//        requestOptions.

        Glide.with(roundedImageView).load(imageUrl).apply(requestOptions).transition(DrawableTransitionOptions.withCrossFade()).into(roundedImageView);

    }

}
