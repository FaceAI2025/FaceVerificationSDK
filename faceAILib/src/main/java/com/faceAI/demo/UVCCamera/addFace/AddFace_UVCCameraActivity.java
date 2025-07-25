package com.faceAI.demo.UVCCamera.addFace;

import android.os.Bundle;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.faceAI.demo.R;
import com.faceAI.demo.base.BaseActivity;


/**
 * 使用UVC RGB摄像头录入人脸
 *
 */
public class AddFace_UVCCameraActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uvc_camera_faceai_activity);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        AddFace_UVCCameraFragment binocularUVCCameraFragment = new AddFace_UVCCameraFragment();
        fragmentTransaction.replace(R.id.fragment_container, binocularUVCCameraFragment);

        fragmentTransaction.commit();
    }


}