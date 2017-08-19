package com.bawei.lvwenjing.dliao.bean;

import java.util.List;

/**
 * Created by lenovo-pc on 2017/7/10.
 */

public class Fragment_Frist_Bean {

    /**
     * pageCount : 共1条记录
     * data : [{"area":"安徽省-安庆市-枞阳县","picWidth":700,"createtime":1499685282051,"picHeight":742,"gender":"女","lng":0,"introduce":"好好好好好好好好好","imagePath":"http://dyt-pict.oss-cn-beijing.aliyuncs.com/dliao/default_woman.jpg","userId":146,"yxpassword":"G32s7320","password":"","lasttime":1499687222682,"phone":"15010082410","nickname":"余复好","age":"24","lat":0}]
     * result_message : 查询成功
     * result_code : 200
     */

    private String pageCount;
    private String result_message;
    private int result_code;
    private List<DataBean> data;

    public String getPageCount() {
        return pageCount;
    }

    public void setPageCount(String pageCount) {
        this.pageCount = pageCount;
    }

    public String getResult_message() {
        return result_message;
    }

    public void setResult_message(String result_message) {
        this.result_message = result_message;
    }

    public int getResult_code() {
        return result_code;
    }

    public void setResult_code(int result_code) {
        this.result_code = result_code;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * area : 安徽省-安庆市-枞阳县
         * picWidth : 700
         * createtime : 1499685282051
         * picHeight : 742
         * gender : 女
         * lng : 0
         * introduce : 好好好好好好好好好
         * imagePath : http://dyt-pict.oss-cn-beijing.aliyuncs.com/dliao/default_woman.jpg
         * userId : 146
         * yxpassword : G32s7320
         * password :
         * lasttime : 1499687222682
         * phone : 15010082410
         * nickname : 余复好
         * age : 24
         * lat : 0
         */

        private String area;
        private int picWidth;
        private long createtime;
        private int picHeight;
        private String gender;
        private int lng;
        private String introduce;
        private String imagePath;
        private int userId;
        private String yxpassword;
        private String password;
        private long lasttime;
        private String phone;
        private String nickname;
        private String age;
        private int lat;

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

        public int getLng() {
            return lng;
        }

        public void setLng(int lng) {
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

        public int getLat() {
            return lat;
        }

        public void setLat(int lat) {
            this.lat = lat;
        }
    }
}
