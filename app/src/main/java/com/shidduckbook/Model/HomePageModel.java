package com.shidduckbook.Model;

/**
 * Created by Peter on 01-Jun-17.
 */

public class HomePageModel {

    String userId;
    String name;
    String age;
    String city;
    String profilepic;
    String status;
    String fav_status;
    String id;
    String pref_number;
    String archive_status;
    int num_Per;
    boolean chckStatus;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProfilepic() {
        return profilepic;
    }

    public void setProfilepic(String profilepic) {
        this.profilepic = profilepic;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFav_status() {
        return fav_status;
    }

    public void setFav_status(String fav_status) {
        this.fav_status = fav_status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isChckStatus() {
        return chckStatus;
    }

    public void setChckStatus(boolean chckStatus) {
        this.chckStatus = chckStatus;
    }

    public String getPref_number() {
        return pref_number;
    }

    public void setPref_number(String pref_number) {
        this.pref_number = pref_number;
    }

    public String getArchive_status() {
        return archive_status;
    }

    public void setArchive_status(String archive_status) {
        this.archive_status = archive_status;
    }

    public int getNum_Per() {
        return num_Per;
    }

    public void setNum_Per(int num_Per) {
        this.num_Per = num_Per;
    }

    @Override
    public String toString() {
        return "HomePageModel{" +
                "userId='" + userId + '\'' +
                ", name='" + name + '\'' +
                ", age='" + age + '\'' +
                ", city='" + city + '\'' +
                ", profilepic='" + profilepic + '\'' +
                ", status='" + status + '\'' +
                ", fav_status='" + fav_status + '\'' +
                ", id='" + id + '\'' +
                ", pref_number='" + pref_number + '\'' +
                ", archive_status='" + archive_status + '\'' +
                ", num_Per=" + num_Per +
                ", chckStatus=" + chckStatus +
                '}';
    }
}
