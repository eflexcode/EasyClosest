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
    private boolean isFavourite;

    public ClosetItem() {
    }

    public ClosetItem(long id, String imageUrl, String category, String season, String note,boolean isFavourite) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.category = category;
        this.season = season;
        this.note = note;
        this.isFavourite = isFavourite;
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
        return isFavourite;
    }

    public void setFavourite(boolean favourite) {
        isFavourite = favourite;
    }


    @BindingAdapter("android:setItemImage")
    public static void setItemImage(RoundedImageView roundedImageView,String imageUrl){

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.color.brown2);
        requestOptions.error(R.color.brown2);
//        requestOptions.

        Glide.with(roundedImageView).load(imageUrl).apply(requestOptions).transition(DrawableTransitionOptions.withCrossFade()).into(roundedImageView);

    }

}
