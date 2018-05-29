package com.example.lenovo.eastofbeijing.ui.mine;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lenovo.eastofbeijing.R;
import com.example.lenovo.eastofbeijing.component.DaggerHttpComponent;
import com.example.lenovo.eastofbeijing.ui.base.BaseActivity;
import com.example.lenovo.eastofbeijing.ui.login.LoginActivity;
import com.example.lenovo.eastofbeijing.ui.mine.contract.UpdateHeaderContract;
import com.example.lenovo.eastofbeijing.ui.mine.presenter.UpdatePresenter;
import com.example.lenovo.eastofbeijing.utils.SharedPreferencesUtils;

import java.io.File;

public class UserInfoActivity extends BaseActivity<UpdatePresenter> implements View.OnClickListener,
        UpdateHeaderContract.View {

    private ImageView mIv;
    private TextView mTv;
    /**
     * 退出登录
     */
    private Button mBt;
    private PopupWindow popupWindow;
    private LinearLayout ll;
    private String imgPath;
    private File imgFile;
    private static final int PHOTO_REQUEST_TAKEPHOTO = 1;// 拍照
    private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
    private static final int PHOTO_REQUEST_CUT = 3;// 结果
    private Bitmap photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imgPath = getExternalCacheDir() + File.separator + "header.jpg";
        imgFile = new File(imgPath);
        Log.e("UserInfoActivity", "imgPath:" + imgPath);
        initView();
        String name = (String) SharedPreferencesUtils.getParam(this, "name", "");
        String iconUrl = (String) SharedPreferencesUtils.getParam(this, "iconUrl", "");

        mTv.setText(name);
        Glide.with(this).load(iconUrl).into(mIv);
    }

    private void initView() {
        mIv = (ImageView) findViewById(R.id.iv);
        mTv = (TextView) findViewById(R.id.tv);
        mBt = (Button) findViewById(R.id.bt);
        ll = findViewById(R.id.ll);
        mBt.setOnClickListener(this);
        mIv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.bt:
                //清空SharedPreferences
                SharedPreferencesUtils.clear(UserInfoActivity.this);
                //回到登录页面
                Intent intent = new Intent(UserInfoActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                UserInfoActivity.this.finish();
                break;
            case R.id.iv:
                //在底部弹出PopupWindow
                initPopupWindow();
                popupWindow.showAtLocation(ll, Gravity.BOTTOM, 0, 0);
                break;
            case R.id.btn_cancel:
                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                }
                break;
            case R.id.btn_take_photo:
                //拍照
                takePhoto();
                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                }
                break;
            case R.id.btn_pick_photo:
                getLocalPic();
                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                }
                break;
        }
    }

    private void takePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imgFile));
        startActivityForResult(intent, PHOTO_REQUEST_TAKEPHOTO);
    }
    private void getLocalPic() {

        Intent intent = new Intent();

        intent.setAction(Intent.ACTION_PICK);

        intent.setType("image/*");

        startActivityForResult(intent, 3000);

    }
    private void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // crop为true是设置在开启的intent中设置显示的view可以剪裁
        intent.putExtra("crop", "true");

        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);

        // outputX,outputY 是剪裁图片的宽高
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("return-data", true);
        intent.putExtra("noFaceDetection", true);
        startActivityForResult(intent, PHOTO_REQUEST_CUT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PHOTO_REQUEST_TAKEPHOTO:
                if (resultCode == Activity.RESULT_OK) {
                    //截取图片
                    startPhotoZoom(Uri.fromFile(imgFile));
                }
                break;
            case PHOTO_REQUEST_CUT:
                //截图图片成功
                Bundle bundle = data.getExtras();
                if (bundle != null) {
                    photo = bundle.getParcelable("data");
                    //上传头像
                    mPresenter.updateHeader(getUid(), imgPath);

                }

                break;
        }
    }

    private void initPopupWindow() {
        View view = LayoutInflater.from(this).inflate(R.layout.pop_item, null);
        popupWindow = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout
                .LayoutParams.WRAP_CONTENT);
        //点击PopupWindow外部可以取消
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable());

        Button btn_take_photo = view.findViewById(R.id.btn_take_photo);
        Button btn_pick_photo = view.findViewById(R.id.btn_pick_photo);
        Button btn_cancel = view.findViewById(R.id.btn_cancel);

        btn_cancel.setOnClickListener(this);
        btn_pick_photo.setOnClickListener(this);
        btn_take_photo.setOnClickListener(this);
    }

    @Override
    public int getContentLayout() {
        return R.layout.activity_user_info;
    }

    @Override
    public void inject() {
        DaggerHttpComponent.builder()
                .build()
                .inject(this);
    }

    @Override
    public void updateSuccess(String code) {
        if ("0".equals(code) && photo != null) {
            toast("上传成功");
            //去设置头像
            mIv.setImageBitmap(photo);

        }
    }
}
