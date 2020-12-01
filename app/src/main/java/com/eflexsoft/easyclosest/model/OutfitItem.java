package com.eflexsoft.easyclosest.model;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.eflexsoft.easyclosest.R;
import com.rishabhharit.roundedimageview.RoundedImageView;

public class OutfitItem {

    private long id;
    private String season;
    private String note;
    private String date;
    private String imageUrl1;
    private String imageUrl2;
    private String imageUrl3;
    private String imageUrl4;
    private String imageUrl5;
    private String imageUrl6;


    public OutfitItem() {
    }

    public OutfitItem(long id, String season, String note, String date, String imageUrl1, String imageUrl2, String imageUrl3, String imageUrl4, String imageUrl5, String imageUrl6) {
        this.id = id;
        this.season = season;
        this.note = note;
        this.date = date;
        this.imageUrl1 = imageUrl1;
        this.imageUrl2 = imageUrl2;
        this.imageUrl3 = imageUrl3;
        this.imageUrl4 = imageUrl4;
        this.imageUrl5 = imageUrl5;
        this.imageUrl6 = imageUrl6;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImageUrl1() {
        return imageUrl1;
    }

    public void setImageUrl1(String imageUrl1) {
        this.imageUrl1 = imageUrl1;
    }

    public String getImageUrl2() {
        return imageUrl2;
    }

    public void setImageUrl2(String imageUrl2) {
        this.imageUrl2 = imageUrl2;
    }

    public String getImageUrl3() {
        return imageUrl3;
    }

    public void setImageUrl3(String imageUrl3) {
        this.imageUrl3 = imageUrl3;
    }

    public String getImageUrl4() {
        return imageUrl4;
    }

    public void setImageUrl4(String imageUrl4) {
        this.imageUrl4 = imageUrl4;
    }

    public String getImageUrl5() {
        return imageUrl5;
    }

    public void setImageUrl5(String imageUrl5) {
        this.imageUrl5 = imageUrl5;
    }

    public String getImageUrl6() {
        return imageUrl6;
    }

    public void setImageUrl6(String imageUrl6) {
        this.imageUrl6 = imageUrl6;
    }
    @BindingAdapter("android:setItemImage")
    public static void setItemImage(ImageView imageView, String imageUrl){

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.color.brown);
        requestOptions.error(R.color.brown);

        Glide.with(imageView).load(imageUrl).apply(requestOptions).transition(DrawableTransitionOptions.withCrossFade()).into(imageView);

    }
}
