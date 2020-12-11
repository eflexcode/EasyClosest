package com.eflexsoft.easyclosest.model;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.eflexsoft.easyclosest.R;
import com.rishabhharit.roundedimageview.RoundedImageView;

public class ImageItem2 {

    private String url;
    private String name;
    private long id;

    public ImageItem2(String url, String name,long id) {
        this.url = url;
        this.name = name;
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @BindingAdapter("android:setItemImage2")
    public static void setItemImage(RoundedImageView roundedImageView, String imageUrl) {

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.color.brown);
        requestOptions.error(R.color.brown);
//        requestOptions.

        Glide.with(roundedImageView).load(imageUrl).apply(requestOptions).transition(DrawableTransitionOptions.withCrossFade()).into(roundedImageView);

    }
}
