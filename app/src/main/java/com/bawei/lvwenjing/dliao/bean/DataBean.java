package com.bawei.lvwenjing.dliao.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Property;

@Entity(nameInDb = "tab2")
public  class DataBean {
    /**
     * area : 河南省 焦作市 孟州市
     * picWidth : 720
     * createtime : 1499686589192
     * picHeight : 728
     * gender : 男
     * lng : 116.294021
     * introduce : 我就是我不一样的烟火！
     * imagePath : http://qhb.2dyt.com/MyInterface/images/4071e656-18e9-416f-8713-b222005b0fea.jpg
     * userId : 156
     * yxpassword : Crc5zsM2
     * password :
     * lasttime : 1499686589192
     * phone : 15313095207
     * nickname : Reinhardt
     * age : 20
     * lat : 40.039519
     */
    @Id(autoincrement = true)
    private Long id;
    @NotNull
    @Property
    private String area;
    @NotNull
    @Property
    private int   relation;
    @NotNull
    @Property
    private int picWidth;
    @NotNull
    @Property
    private long createtime;
    @NotNull
    @Property
    private int picHeight;
    @NotNull
    @Property
    private String gender;
    @NotNull
    @Property
    private double lng;
    @NotNull
    @Property
    private String introduce;
    @NotNull
    @Property
    private String imagePath;
    @NotNull
    @Property
    private int userId;
    @NotNull
    @Property
    private String yxpassword;
    @NotNull
    @Property
    private String password;
    @NotNull
    @Property
    private long lasttime;
    @NotNull
    @Property
    private String phone;
    @NotNull
    @Property
    private String nickname;
    @NotNull
    @Property
    private String age;


    @NotNull
    @Property
    private double lat;

    @Generated(hash = 2122355253)
    public DataBean(Long id, @NotNull String area, int relation, int picWidth, long createtime, int picHeight,
                    @NotNull String gender, double lng, @NotNull String introduce, @NotNull String imagePath, int userId,
                    @NotNull String yxpassword, @NotNull String password, long lasttime, @NotNull String phone,
                    @NotNull String nickname, @NotNull String age, double lat) {
        this.id = id;
        this.area = area;
        this.relation = relation;
        this.picWidth = picWidth;
        this.createtime = createtime;
        this.picHeight = picHeight;
        this.gender = gender;
        this.lng = lng;
        this.introduce = introduce;
        this.imagePath = imagePath;
        this.userId = userId;
        this.yxpassword = yxpassword;
        this.password = password;
        this.lasttime = lasttime;
        this.phone = phone;
        this.nickname = nickname;
        this.age = age;
        this.lat = lat;
    }

    @Generated(hash = 908697775)
    public DataBean() {
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public int getPicWidth() {
        return picWidth;
    }

    public void setPicWidth(int picWidth) {
        this.picWidth = picWidth;
    }

    public long getCreatetime() {
        return createtime;
    }

    public void setCreatetime(long createtime) {
        this.createtime = createtime;
    }

    public int getPicHeight() {
        return picHeight;
    }

    public void setPicHeight(int picHeight) {
        this.picHeight = picHeight;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getYxpassword() {
        return yxpassword;
    }

    public void setYxpassword(String yxpassword) {
        this.yxpassword = yxpassword;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getLasttime() {
        return lasttime;
    }

    public void setLasttime(long lasttime) {
        this.lasttime = lasttime;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getRelation() {
        return this.relation;
    }

    public void setRelation(int relation) {
        this.relation = relation;
    }
}