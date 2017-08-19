package com.bawei.lvwenjing.dliao.activitys;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bawei.lvwenjing.dliao.R;
import com.bawei.lvwenjing.dliao.base.AppManager;
import com.bawei.lvwenjing.dliao.base.IApplication;
import com.bawei.lvwenjing.dliao.quan.RecyclerViewExampleActivity;
import com.bawei.lvwenjing.dliao.quan.model.NineGridTestModel;
import com.bawei.lvwenjing.dliao.utils.PreferencesUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

public class MyInforActivity extends Fragment {
    private View inflate;
    private SimpleDraweeView draweeView;
    private LinearLayout fragmentfourthsheikanwo;


    private List<NineGridTestModel> mList = new ArrayList<>();
    private String[] mUrls = new String[]{"http://d.hiphotos.baidu.com/image/h%3D200/sign=201258cbcd80653864eaa313a7dca115/ca1349540923dd54e54f7aedd609b3de9c824873.jpg",
            "http://img3.fengniao.com/forum/attachpics/537/165/21472986.jpg",
            "http://d.hiphotos.baidu.com/image/h%3D200/sign=ea218b2c5566d01661199928a729d498/a08b87d6277f9e2fd4f215e91830e924b999f308.jpg",
            "http://imgsrc.baidu.com/image/c0%3Dshijue1%2C0%2C0%2C294%2C40/sign=60aeee5da74bd11310c0bf7132c6ce7a/72f082025aafa40fe3c0c4f3a164034f78f0199d.jpg",
            "http://img3.redocn.com/tupian/20150430/mantenghuawenmodianshiliangbeijing_3924704.jpg",
            "http://img5.imgtn.bdimg.com/it/u=4267222417,1017407570&fm=200&gp=0.jpg",
            "http://img4.imgtn.bdimg.com/it/u=882039601,2636712663&fm=21&gp=0.jpg",
            "http://img4.imgtn.bdimg.com/it/u=4119861953,350096499&fm=21&gp=0.jpg",
            "http://img1.imgtn.bdimg.com/it/u=1902468542,2120439953&fm=200&gp=0.jpg",
            "http://imgsrc.baidu.com/image/c0%3Dshijue1%2C0%2C0%2C294%2C40/sign=ae4e87268d94a4c21e2eef68669d71a0/7c1ed21b0ef41bd5d5a88edd5bda81cb39db3d1b.jpg",
            "http://pic35.nipic.com/20131121/2531170_145358633000_2.jpg",
            "http://pic.58pic.com/58pic/16/62/63/97m58PICyWM_1024.jpg",
            "http://pic.58pic.com/58pic/16/62/63/97m58PICyWM_1024.jpg"};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        inflate = inflater.inflate(R.layout.activity_my_infor, container, false);
        initListData();
        fragmentfourthsheikanwo = (LinearLayout) inflate.findViewById(R.id.fragment_fourth_sheikanwo);




        fragmentfourthsheikanwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             RecyclerViewExampleActivity.startActivity(IApplication.getApplication(), mList);
                           }
        });


        draweeView = (SimpleDraweeView) inflate.findViewById(R.id.my_image_view);
        RoundingParams params = RoundingParams.asCircle();
        GenericDraweeHierarchyBuilder builder = new GenericDraweeHierarchyBuilder(getResources());
        GenericDraweeHierarchy draweeHierarchy = builder.setFadeDuration(300)
                .setFailureImage(R.mipmap.bat_vs_super_bg)
                .setPlaceholderImage(R.mipmap.bat_vs_supper)
                .setRoundingParams(params)
                .build();
        String imager = PreferencesUtils.getValueByKey(IApplication.getApplication(), "imager", "");
        Uri uri = Uri.parse(imager);
        draweeView.getHierarchy();
        draweeView.setHierarchy(draweeHierarchy);
        draweeView.setImageURI(uri);
        String valueByKey = PreferencesUtils.getValueByKey(IApplication.getApplication(), "nickname", "a");
        TextView tv = (TextView) inflate.findViewById(R.id.my_infor_name);
        tv.setText(valueByKey);
        LinearLayout shezhi = (LinearLayout) inflate.findViewById(R.id.fragment_fourth_shezhi);
        shezhi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(IApplication.getApplication(), Zhanghaotuichu.class));
                AppManager.getAppManager().finishActivity(getActivity());
            }
        });


        LinearLayout fragmentfourthmyphoto = (LinearLayout) inflate.findViewById(R.id.fragment_fourth_my_photo);
        LinearLayout fragmentfourthmyingot = (LinearLayout) inflate.findViewById(R.id.fragment_fourth_infor);

        fragmentfourthmyingot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(IApplication.getApplication(), FridentActivity.class));
            }
        });
        fragmentfourthmyphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(IApplication.getApplication(), ShowPhoto.class));
            }
        });

        return inflate;
    }


    private void initListData() {
        NineGridTestModel model1 = new NineGridTestModel();
        model1.urlList.add(mUrls[0]);
        mList.add(model1);

        NineGridTestModel model2 = new NineGridTestModel();
        model2.urlList.add(mUrls[4]);
        mList.add(model2);
//
//        NineGridTestModel model3 = new NineGridTestModel();
//        model3.urlList.add(mUrls[2]);
//        mList.add(model3);

        NineGridTestModel model4 = new NineGridTestModel();
        for (int i = 0; i < mUrls.length; i++) {
            model4.urlList.add(mUrls[i]);
        }
        model4.isShowAll = false;
        mList.add(model4);

        NineGridTestModel model5 = new NineGridTestModel();
        for (int i = 0; i < mUrls.length; i++) {
            model5.urlList.add(mUrls[i]);
        }
        model5.isShowAll = true;//显示全部图片
        mList.add(model5);

        NineGridTestModel model6 = new NineGridTestModel();
        for (int i = 0; i < 9; i++) {
            model6.urlList.add(mUrls[i]);
        }
        mList.add(model6);

        NineGridTestModel model7 = new NineGridTestModel();
        for (int i = 3; i < 7; i++) {
            model7.urlList.add(mUrls[i]);
        }
        mList.add(model7);

        NineGridTestModel model8 = new NineGridTestModel();
        for (int i = 3; i < 6; i++) {
            model8.urlList.add(mUrls[i]);
        }
        mList.add(model8);
    }

}