package com.jzsec.broker.ui.test;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jzsec.broker.R;
import com.jzsec.broker.base.BaseLazyFragment;
import com.jzsec.broker.utils.Zlog;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import me.yokeyword.fragmentation.SupportFragment;
import rx.functions.Action1;

/**
 * Created by zhaopan on 2017/8/28.
 */

public class FileProviderFragment extends BaseLazyFragment {

    private static final int REQUEST_CODE_TAKE_PHOTO = 0x110;
    private static final String TAG = "FileProviderFragment";
    private String mCurrentPhotoPath;
    @BindView(R.id.id_iv)
    ImageView imgIV;

    public static SupportFragment newInstance() {
        Bundle args = new Bundle();
        SupportFragment fragment = new FileProviderFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_test_fileprovider, container, false);
        return view;
    }

    @Override
    protected void initLazyView(@Nullable Bundle savedInstanceState) {
        _click(R.id.btn_takePhotoNoCompress, new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                takePhotoNoCompress(null);
            }
        });
        _click(R.id.btn_installApk, new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                installApk(null);
            }
        });
    }

public void takePhotoNoCompress(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(_mActivity.getPackageManager()) != null) {

            String filename = new SimpleDateFormat("yyyyMMdd-HHmmss", Locale.CHINA)
                    .format(new Date()) + ".png";
            File file = new File(Environment.getExternalStorageDirectory(), filename);
            mCurrentPhotoPath = file.getAbsolutePath();

            Uri fileUri = cn.kq.fitandroid7.FileProvider7.getUriForFile(getContext(), file);
            Zlog.e(TAG, "mCurrentPhotoPaht:"+mCurrentPhotoPath+", fileUri="+fileUri.toString());

            List<ResolveInfo> resInfoList = _mActivity.getPackageManager()
                    .queryIntentActivities(takePictureIntent, PackageManager.MATCH_DEFAULT_ONLY);
            for (ResolveInfo resolveInfo : resInfoList) {
                String packageName = resolveInfo.activityInfo.packageName;
                _mActivity.grantUriPermission(packageName, fileUri, Intent.FLAG_GRANT_READ_URI_PERMISSION
                        | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            }

            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
            startActivityForResult(takePictureIntent, REQUEST_CODE_TAKE_PHOTO);
        }
    }

    public void installApk(View view){

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_TAKE_PHOTO) {
            imgIV.setImageBitmap(BitmapFactory.decodeFile(mCurrentPhotoPath));
        }
        // else tip?

    }
}
