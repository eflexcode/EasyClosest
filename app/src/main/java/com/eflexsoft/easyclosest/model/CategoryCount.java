package com.eflexsoft.easyclosest.model;

public class CategoryCount {

    private long Shirts;
    private long Trousers;
    private long Harts;
    private long Caps;
    private long Headwarmer;
    private long Belts;
    private long Sweaters;
    private long Coats;
    private long Glasses;
    private long Jewries;
    private long Accessories;
    private long Shoes;
    private long Underwear;

    public CategoryCount() {
    }

    public CategoryCount(long Shirts, long Trousers, long Coats, long Harts, long Glasses, long Caps, long Headwarmer, long Belts, long Sweaters, long Jewries, long Accessories, long Shoes, long Underwear) {
        this.Shirts = Shirts;
        this.Trousers = Trousers;
        this.Harts = Harts;
        this.Caps = Caps;
        this.Headwarmer = Headwarmer;
        this.Belts = Belts;
        this.Sweaters = Sweaters;
        this.Jewries = Jewries;
        this.Accessories = Accessories;
        this.Shoes = Shoes;
        this.Underwear = Underwear;
        this.Coats = Coats;
        this.Glasses = Glasses;
    }

    public long getShirts() {
        return Shirts;
    }

    public void setShirts(long Shirts) {
        this.Shirts = Shirts;
    }

    public long getTrousers() {
        return Trousers;
    }

    public void setTrousers(long Trousers) {
        this.Trousers = Trousers;
    }

    public long getHarts() {
        return Harts;
    }

    public void setHarts(long Harts) {
        this.Harts = Harts;
    }

    public long getCaps() {
        return Caps;
    }

    public void setCaps(long Caps) {
        this.Caps = Caps;
    }

    public long getHeadwarmer() {
        return Headwarmer;
    }

    public void setHeadwarmer(String headwarmer) {
        this.Headwarmer = Headwarmer;
    }

    public long getBelts() {
        return Belts;
    }

    public void setBelts(long Belts) {
        this.Belts = Belts;
    }

    public long getSweaters() {
        return Sweaters;
    }

    public void setSweaters(long Sweaters) {
        this.Sweaters = Sweaters;
    }

    public long getJewries() {
        return Jewries;
    }

    public void setJewries(long Jewries) {
        this.Jewries = Jewries;
    }

    public long getAccessories() {
        return Accessories;
    }

    public void setAccessories(long Accessories) {
        this.Accessories = Accessories;
    }

    public long getShoes() {
        return Shoes;
    }

    public void setShoes(long Shoes) {
        this.Shoes = Shoes;
    }

    public long getUnderwear() {
        return Underwear;
    }

    public long getCoats() {
        return Coats;
    }

    public void setCoats(long Coats) {
        this.Coats = Coats;
    }

    public long getGlasses() {
        return Glasses;
    }

    public void setGlasses(long Glasses) {
        this.Glasses = Glasses;
    }
}
