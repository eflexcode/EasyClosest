package com.eflexsoft.easyclosest.model;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.eflexsoft.easyclosest.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class User {

    private String username;
    private String profilePictureUlr;
    private String age;
    private String id;
    private String email;

    public User() {
    }

    public User(String username, String profilePictureUlr, String age, String id, String email) {
        this.username = username;
        this.profilePictureUlr = profilePictureUlr;
        this.age = age;
        this.id = id;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProfilePictureUlr() {
        return profilePictureUlr;
    }

    public void setProfilePictureUlr(String profilePictureUlr) {
        this.profilePictureUlr = profilePictureUlr;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    @BindingAdapter("android:setProPic")
    public static void setProPic(CircleImageView circleImageView, String url) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.color.brown);
        requestOptions.error(R.drawable.no_p);

        Glide.with(circleImageView).load(url).apply(requestOptions).into(circleImageView);
    }
}
