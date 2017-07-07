package com.bawei.lvwenjing.dliao.activitys;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bawei.lvwenjing.dliao.R;
import com.bawei.lvwenjing.dliao.base.IApplication;
import com.bawei.lvwenjing.dliao.bean.UploadPhotoBean;
import com.bawei.lvwenjing.dliao.core.JNICore;
import com.bawei.lvwenjing.dliao.core.SortUtils;
import com.bawei.lvwenjing.dliao.network.BaseObServer;
import com.bawei.lvwenjing.dliao.network.RetrofitFactory;
import com.bawei.lvwenjing.dliao.utils.Constants;
import com.bawei.lvwenjing.dliao.utils.ImageResizeUtils;
import com.bawei.lvwenjing.dliao.utils.SDCardUtils;
import com.bawei.lvwenjing.dliao.widget.BitmapToRound_Util;
import com.bawei.lvwenjing.dliao.widget.MyToast;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static com.bawei.lvwenjing.dliao.R.id.photo_pic_button;
import static com.bawei.lvwenjing.dliao.utils.ImageResizeUtils.copyStream;

public class PhotoAcitvity extends Activity {

    @BindView(R.id.photo_finsh)
    ImageView photoFinsh;
    @BindView(R.id.photo_title_photo)
    ImageView phototitlephoto;
    @BindView(R.id.photo_button)
    Button photoButton;
    @BindView(photo_pic_button)
    Button photoPicButton;
    @BindView(R.id.activity_photo_acitvity)
    LinearLayout activityPhotoAcitvity;
    private Button bt;
    private BitmapToRound_Util round_util;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_acitvity);
        ButterKnife.bind(this);
        round_util = new BitmapToRound_Util();
        bt = (Button) findViewById(R.id.photo_button);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(PhotoAcitvity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    //尝试开启权限
                    //权限发生了改变 true  //  false 小米
                    if (ActivityCompat.shouldShowRequestPermissionRationale(PhotoAcitvity.this, Manifest.permission.CAMERA)) {


                        new AlertDialog.Builder(PhotoAcitvity.this).setTitle("title")
                                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        // 请求授权
                                        ActivityCompat.requestPermissions(PhotoAcitvity.this, new String[]{Manifest.permission.CAMERA}, 1);

                                    }
                                }).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).create().show();
                    } else {
                        ActivityCompat.requestPermissions(PhotoAcitvity.this, new String[]{Manifest.permission.CAMERA}, 1);

                    }
                } else {
                    camear();
                }

            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1) {
            // camear 权限回调

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                // 表示用户授权
                Toast.makeText(this, " user Permission", Toast.LENGTH_SHORT).show();

                camear();


            } else {

                //用户拒绝权限
                Toast.makeText(this, " no Permission", Toast.LENGTH_SHORT).show();

            }
        }
    }

    static final int INTENTFORCAMERA = 1;
    static final int INTENTFORPHOTO = 2;


    public String LocalPhotoName;

    public String createLocalPhotoName() {
        LocalPhotoName = System.currentTimeMillis() + "face.jpg";
        return LocalPhotoName;
    }

    @OnClick(photo_pic_button)
    public void photoPic() {
        toPhoto();
    }

    //开启照相机
    public void camear() {
        try {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, INTENTFORCAMERA);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void toPhoto() {
        try {
            createLocalPhotoName();
            Intent getAlbum = new Intent(Intent.ACTION_GET_CONTENT);
            getAlbum.setType("image/*");
            startActivityForResult(getAlbum, INTENTFORPHOTO);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case INTENTFORPHOTO:
                //相册
                try {
                    // 必须这样处理，不然在4.4.2手机上会出问题
                    Uri originalUri = data.getData();
                    File f = null;
                    if (originalUri != null) {
                        f = new File(SDCardUtils.photoCacheDir, LocalPhotoName);
                        String[] proj = {MediaStore.Images.Media.DATA};
                        Cursor actualimagecursor = this.getContentResolver().query(originalUri, proj, null, null, null);
                        if (null == actualimagecursor) {
                            if (originalUri.toString().startsWith("file:")) {
                                File file = new File(originalUri.toString().substring(7, originalUri.toString().length()));
                                if (!file.exists()) {
                                    //地址包含中文编码的地址做utf-8编码
                                    originalUri = Uri.parse(URLDecoder.decode(originalUri.toString(), "UTF-8"));
                                    file = new File(originalUri.toString().substring(7, originalUri.toString().length()));
                                }
                                FileInputStream inputStream = new FileInputStream(file);
                                FileOutputStream outputStream = new FileOutputStream(f);
                                copyStream(inputStream, outputStream);
                            }
                        } else {
                            // 系统图库
                            int actual_image_column_index = actualimagecursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                            actualimagecursor.moveToFirst();
                            String img_path = actualimagecursor.getString(actual_image_column_index);
                            if (img_path == null) {
                                InputStream inputStream = this.getContentResolver().openInputStream(originalUri);
                                FileOutputStream outputStream = new FileOutputStream(f);
                                copyStream(inputStream, outputStream);
                            } else {
                                File file = new File(img_path);
                                FileInputStream inputStream = new FileInputStream(file);
                                FileOutputStream outputStream = new FileOutputStream(f);
                                copyStream(inputStream, outputStream);
                            }

                        }
                        Bitmap bitmap = ImageResizeUtils.resizeImage(f.getAbsolutePath(), Constants.RESIZE_PIC);
                        bitmap = round_util.toRoundBitmap(bitmap);
                        phototitlephoto.setImageBitmap(bitmap);
                        FileOutputStream fos = new FileOutputStream(f.getAbsolutePath());
                        if (bitmap != null) {
                            if (bitmap.compress(Bitmap.CompressFormat.JPEG, 85, fos)) {
                                fos.close();
                                fos.flush();
                            }
                            if (!bitmap.isRecycled()) {
                                bitmap.isRecycled();
                            }

                            uploadFile(f);

                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();

                }


                break;
            case INTENTFORCAMERA:
//                相机
                try {

                    //file 就是拍照完 得到的原始照片
                    File file = new File(SDCardUtils.photoCacheDir, LocalPhotoName);
                    Bitmap bitmap = ImageResizeUtils.resizeImage(file.getAbsolutePath(), Constants.RESIZE_PIC);
                    bitmap = round_util.toRoundBitmap(bitmap);
                    phototitlephoto.setImageBitmap(bitmap);

                    FileOutputStream fos = new FileOutputStream(file.getAbsolutePath());
                    if (bitmap != null) {
                        if (bitmap.compress(Bitmap.CompressFormat.JPEG, 85, fos)) {
                            fos.close();
                            fos.flush();
                        }
                        if (!bitmap.isRecycled()) {
                            //通知系统 回收bitmap
                            bitmap.isRecycled();
                        }
                        uploadFile(file);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }


                break;
        }


    }


    public void uploadFile(File file) {


        if (!file.exists()) {
            MyToast.makeText(this, " 照片不存在", Toast.LENGTH_SHORT);
            return;
        }
        String[] arr = file.getAbsolutePath().split("/");

        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);

        long ctimer = System.currentTimeMillis();
        Map<String, String> map = new HashMap<String, String>();
        map.put("user.currenttimer", ctimer + "");
        String sign = JNICore.getSign(SortUtils.getMapResult(SortUtils.sortString(map)));
        map.put("user.sign", sign);


        MultipartBody body = new MultipartBody.Builder()
                .addFormDataPart("image", arr[arr.length - 1], requestFile)
                .build();



        RetrofitFactory.uploadPhoto(body, map, new BaseObServer() {
            @Override
            public void onSuccess(String result) {

                try {
                    Gson gson = new Gson();
                    UploadPhotoBean bean = gson.fromJson(result, UploadPhotoBean.class);
                    if (bean.getResult_code() == 200) {
                        MyToast.makeText(IApplication.getApplication(), "上传成功", Toast.LENGTH_SHORT);
                    }
                } catch (Exception e1) {
                    e1.printStackTrace();
                }

            }

            @Override
            public void onFailed(int code) {
                MyToast.makeText(IApplication.getApplication(), "" + code, Toast.LENGTH_SHORT);

            }
        });


    }

}