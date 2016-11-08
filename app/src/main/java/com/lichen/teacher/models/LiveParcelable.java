package com.lichen.teacher.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by xiaowu on 2016/8/3.
 */
public class LiveParcelable implements Parcelable {

    private String imageUrl;
    private String title;
    private String teacher;
    private String date;
    private String startTime;
    private String endTime;
    private float price;
    private String type;
    private String summary;
    private int watchCount;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(imageUrl);
        dest.writeString(title);
        dest.writeString(teacher);
        dest.writeString(date);
        dest.writeString(startTime);
        dest.writeString(endTime);
        dest.writeFloat(price);
        dest.writeString(type);
        dest.writeString(summary);
        dest.writeInt(watchCount);
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public int getWatchCount() {
        return watchCount;
    }

    public void setWatchCount(int watchCount) {
        this.watchCount = watchCount;
    }

    public LiveParcelable() {
        super();
    }

    public LiveParcelable(Parcel source) {
        super();
        this.imageUrl = source.readString();
        this.title = source.readString();
        this.teacher = source.readString();
        this.date = source.readString();
        this.startTime = source.readString();
        this.endTime = source.readString();
        this.price = source.readFloat();
        this.type = source.readString();
        this.summary = source.readString();
        this.watchCount = source.readInt();
    }

    public static final Creator<LiveParcelable> CREATOR =
            new Creator<LiveParcelable>() {

        @Override
        public LiveParcelable createFromParcel(Parcel source) {
            return new LiveParcelable(source);
        }

        @Override
        public LiveParcelable[] newArray(int size) {
            return new LiveParcelable[size];
        }
    };

}
