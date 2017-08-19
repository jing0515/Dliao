package com.bawei.lvwenjing.dliao.bean;

/**
 * Created by lenovo-pc on 2017/7/7.
 */

public class LoginBean {

    /**
     * result_message : success
     * data : {"area":"安徽省-安庆市-枞阳县","password":"698d51a19d8a121ce581499d7b701668","lasttime":1499740315647,"createtime":1499686282170,"gender":"男","lng":116.295966,"phone":"13645616851","introduce":"hhaha","imagePath":"http://qhb.2dyt.com/MyInterface/images/cf3abb90-040d-45dc-ad2c-7fcd11f348a9.jpg","nickname":"安徽","userId":153,"lat":40.037251}
     * result_code : 200
     */

    private String result_message;
    private DataBean data;
    private int result_code;

    public String getResult_message() {
        return result_message;
    }

    public void setResult_message(String result_message) {
        this.result_message = result_message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public int getResult_code() {
        return result_code;
    }

    public void setResult_code(int result_code) {
        this.result_code = result_code;
    }

    public static class DataBean {
        /**
         * area : 安徽省-安庆市-枞阳县
         * password : 698d51a19d8a121ce581499d7b701668
         * lasttime : 1499740315647
         * createtime : 1499686282170
         * gender : 男
         * lng : 116.295966
         * phone : 13645616851
         * introduce : hhaha
         * imagePath : http://qhb.2dyt.com/MyInterface/images/cf3abb90-040d-45dc-ad2c-7fcd11f348a9.jpg
         * nickname : 安徽
         * userId : 153
         * lat : 40.037251
         */

        private String area;
        private String password;
        private long lasttime;
        private long createtime;
        private String gender;
        private double lng;
        private String phone;
        private String introduce;
        private String imagePath;
        private String nickname;
        private int userId;
        private double lat;
        private String yxpassword;

        public String getYxpassword() {
            return yxpassword;
        }

        public void setYxpassword(String yxpassword) {
            this.yxpassword = yxpassword;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
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

        public long getCreatetime() {
            return createtime;
        }

        public void setCreatetime(long createtime) {
            this.createtime = createtime;
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

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
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

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }
    }
}
