package com.example.demo;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import com.faceAI.demo.FaceAIConfig;
import com.airbnb.lottie.LottieAnimationView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.faceAI.demo.FaceAINaviActivity;
import com.tencent.bugly.crashreport.CrashReport;

/**
 * 演示快速集成到你的主工程，人脸识别相关放到 FaceAILIb 里面
 * 先以子module 的形式配置到你的主工程跑起来后，再根据你的业务调整
 * <p>
 * 1.整体拷贝faceAILib 代码到你的主程一级目录
 * 2.settings.gradle 中 include ':faceAILib'
 * 3.调整工程一级目录root级build.gradle 的
 * <p>
 * <a href="https://github.com/FaceAISDK/FaceAISDK_Android">...</a>
 */
public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        //人脸图保存路径等初始化配置
        FaceAIConfig.init(this);

        setWelcomeAnimal();
    }


    /**
     * 设置欢迎动画
     */
    private void setWelcomeAnimal(){
        findViewById(R.id.goFaceAILib).setOnClickListener(view ->
                startActivity(new Intent(WelcomeActivity.this, FaceAINaviActivity.class)));

        LottieAnimationView startFaceAILib = findViewById(R.id.goFaceAILib);
        startFaceAILib.setAnimation(R.raw.face_ai_welcome);
        startFaceAILib.setSpeed(3f);
        startFaceAILib.playAnimation();

        startFaceAILib.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationEnd(@NonNull Animator animation) {
                //演示主工程调用Face AI Lib 中人脸识别功能
                startActivity(new Intent(WelcomeActivity.this, FaceAINaviActivity.class));
                WelcomeActivity.this.finish();
            }

            @Override
            public void onAnimationStart(@NonNull Animator animation) {
            }

            @Override
            public void onAnimationCancel(@NonNull Animator animation) {
            }

            @Override
            public void onAnimationRepeat(@NonNull Animator animation) {
            }
        });

        // 收集Crash,ANR 运行日志
        if (!BuildConfig.DEBUG) {
            CrashReport.initCrashReport(this, "36fade54d8", true);
        }
    }


}