package com.bawei.lvwenjing.dliao.bean;

import java.util.List;

public class FFindBean {
    /**
     * pageCount : 共10条记录
     * data : [{"area":"河南省 焦作市 孟州市","picWidth":720,"createtime":1499686589192,"picHeight":728,"gender":"男","lng":116.294021,"introduce":"我就是我不一样的烟火！","imagePath":"http://qhb.2dyt.com/MyInterface/images/4071e656-18e9-416f-8713-b222005b0fea.jpg","userId":156,"yxpassword":"Crc5zsM2","password":"","lasttime":1499686589192,"phone":"15313095207","nickname":"Reinhardt","age":"20","lat":40.039519},{"area":"安徽省  安庆市   枞阳县","picWidth":720,"createtime":1499686628164,"picHeight":1280,"gender":"男","lng":0,"introduce":"金龙","imagePath":"http://qhb.2dyt.com/MyInterface/images/ef11abe9-097c-4dc1-a21e-8529b2fa7221.jpg","userId":157,"yxpassword":"7r4b5k46","password":"","lasttime":1499686628164,"phone":"15609909090","nickname":"我是我","age":"20","lat":0},{"area":"北京市 北京市 海淀区","picWidth":700,"createtime":1499686707753,"picHeight":742,"gender":"女","lng":34,"introduce":"耶","imagePath":"http://dyt-pict.oss-cn-beijing.aliyuncs.com/dliao/default_woman.jpg","userId":158,"yxpassword":"8913CuX6","password":"","lasttime":1499686707753,"phone":"13111111114","nickname":"超级帅","age":"27","lat":40.039183},{"area":"北京市 北京市 海淀区","picWidth":720,"createtime":1499686809778,"picHeight":1281,"gender":"女","lng":34,"introduce":"4155","imagePath":"http://qhb.2dyt.com/MyInterface/images/a312495f-ceb3-4c08-90d0-0d0f1aaf581a.jpg","userId":159,"yxpassword":"DO628Bw5","password":"","lasttime":1499686809778,"phone":"13111111115","nickname":"酷酷的","age":"27","lat":40.039183},{"area":"安徽省-安庆市-枞阳县","picWidth":700,"createtime":1499686821106,"picHeight":742,"gender":"女","lng":0,"introduce":"123456","imagePath":"http://dyt-pict.oss-cn-beijing.aliyuncs.com/dliao/default_woman.jpg","userId":160,"yxpassword":"57731Q54","password":"","lasttime":1499686845682,"phone":"15010082411","nickname":"好难","age":"24","lat":0},{"area":"安徽省安庆市枞阳县","picWidth":720,"createtime":1499687207064,"picHeight":960,"gender":"男","lng":116.299698,"introduce":"123466","imagePath":"http://qhb.2dyt.com/MyInterface/images/2e770bbb-07ec-4a7c-8ab7-cf38e9a37d66.jpg","userId":161,"yxpassword":"8j9729OK","password":"","lasttime":1499690361135,"phone":"13934722946","nickname":"整理","age":"51","lat":40.040442},{"area":"安徽省-安庆市-枞阳县","picWidth":700,"createtime":1499687263024,"picHeight":742,"gender":"女","lng":0,"introduce":"123456789","imagePath":"http://dyt-pict.oss-cn-beijing.aliyuncs.com/dliao/default_woman.jpg","userId":162,"yxpassword":"76m52UUy","password":"","lasttime":1499687263024,"phone":"15010082413","nickname":"123456789","age":"22","lat":0},{"area":"安徽省-安庆市-枞阳县","picWidth":720,"createtime":1499687500331,"picHeight":960,"gender":"女","lng":0,"introduce":"看见了","imagePath":"http://qhb.2dyt.com/MyInterface/images/bf27b238-cc68-4b31-aa72-9929fd1edbd7.jpg","userId":163,"yxpassword":"8476KZ17","password":"","lasttime":1499687500331,"phone":"15953808885","nickname":"进去了","age":"25","lat":0},{"area":"北京 东城区","picWidth":510,"createtime":1499687509158,"picHeight":507,"gender":"男","lng":0,"introduce":"meow","imagePath":"http://dyt-pict.oss-cn-beijing.aliyuncs.com/dliao/default_man.jpg","userId":164,"yxpassword":"N1l5G3MA","password":"","lasttime":1499687509158,"phone":"13513654545","nickname":"ion用","age":"24","lat":0},{"area":"安徽省-安庆市-枞阳县","picWidth":720,"createtime":1499687792864,"picHeight":960,"gender":"女","lng":0,"introduce":"路上","imagePath":"http://qhb.2dyt.com/MyInterface/images/8ad11e9a-1d2a-4793-86d2-66d683738fde.jpg","userId":165,"yxpassword":"4vR4940t","password":"","lasttime":1499687792864,"phone":"15953068766","nickname":"填","age":"24","lat":0}]
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


}
