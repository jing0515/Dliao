package com.bawei.lvwenjing.dliao.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/7/12.
 */

public class PersonBean {


    /**
     * result_message : success
     * data : {"area":"安徽省  安庆市   枞阳县","lasttime":1499845183516,"createtime":1499828541161,"gender":"男","introduce":"jm","imagePath":"http://qhb.2dyt.com/MyInterface/images/5bde0a3a-aae1-477a-8c95-ad744bd0ced1.jpg","nickname":"我是我","userId":3,"photolist":[{"picWidth":720,"timer":1499844751582,"picHeight":720,"imagePath":"http://qhb.2dyt.com/MyInterface/images/8e8cadaf-503b-4a19-b0b5-2936585a05f4.jpg","albumId":10,"userId":3},{"picWidth":720,"timer":1499844756732,"picHeight":960,"imagePath":"http://qhb.2dyt.com/MyInterface/images/d9ed1f2d-d3cc-4b85-9c94-2d37916d8ee2.jpg","albumId":11,"userId":3}]}
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
         * area : 安徽省  安庆市   枞阳县
         * lasttime : 1499845183516
         * createtime : 1499828541161
         * gender : 男
         * introduce : jm
         * imagePath : http://qhb.2dyt.com/MyInterface/images/5bde0a3a-aae1-477a-8c95-ad744bd0ced1.jpg
         * nickname : 我是我
         * userId : 3
         * photolist : [{"picWidth":720,"timer":1499844751582,"picHeight":720,"imagePath":"http://qhb.2dyt.com/MyInterface/images/8e8cadaf-503b-4a19-b0b5-2936585a05f4.jpg","albumId":10,"userId":3},{"picWidth":720,"timer":1499844756732,"picHeight":960,"imagePath":"http://qhb.2dyt.com/MyInterface/images/d9ed1f2d-d3cc-4b85-9c94-2d37916d8ee2.jpg","albumId":11,"userId":3}]
         */

        private String area;
        private long lasttime;
        private long createtime;
        private String gender;
        private String introduce;
        private String imagePath;
        private String nickname;
        private int userId;
        private List<PhotolistBean> photolist;

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
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

        public List<PhotolistBean> getPhotolist() {
            return photolist;
        }

        public void setPhotolist(List<PhotolistBean> photolist) {
            this.photolist = photolist;
        }

        public static class PhotolistBean {
            /**
             * picWidth : 720
             * timer : 1499844751582
             * picHeight : 720
             * imagePath : http://qhb.2dyt.com/MyInterface/images/8e8cadaf-503b-4a19-b0b5-2936585a05f4.jpg
             * albumId : 10
             * userId : 3
             */

            private int picWidth;
            private long timer;
            private int picHeight;
            private String imagePath;
            private int albumId;
            private int userId;

            public int getPicWidth() {
                return picWidth;
            }

            public void setPicWidth(int picWidth) {
                this.picWidth = picWidth;
            }

            public long getTimer() {
                return timer;
            }

            public void setTimer(long timer) {
                this.timer = timer;
            }

            public int getPicHeight() {
                return picHeight;
            }

            public void setPicHeight(int picHeight) {
                this.picHeight = picHeight;
            }

            public String getImagePath() {
                return imagePath;
            }

            public void setImagePath(String imagePath) {
                this.imagePath = imagePath;
            }

            public int getAlbumId() {
                return albumId;
            }

            public void setAlbumId(int albumId) {
                this.albumId = albumId;
            }

            public int getUserId() {
                return userId;
            }

            public void setUserId(int userId) {
                this.userId = userId;
            }
        }
    }
}
