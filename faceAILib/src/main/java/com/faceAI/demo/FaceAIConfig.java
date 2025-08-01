package com.faceAI.demo;

import android.content.Context;
import android.content.pm.ApplicationInfo;

import com.faceAI.demo.base.utils.VoicePlayer;

import java.io.File;

/**
 * 不要直接使用File Api 直接往文件目录插入图片，要使用SDK 提供的APi写入数据，图片还需要向量化，检测质量等操作
 */
public class FaceAIConfig {

    //不要直接使用File Api 直接往文件目录插入图片，要使用SDK 提供的APi写入数据，图片还需要向量化
    public static String CACHE_BASE_FACE_DIR;   //1：1 人脸识别人脸图片存储目录
    public static String CACHE_SEARCH_FACE_DIR; //1：N 人脸识别搜索人脸图片存储目录

    /**
     * 初始化人脸识别 人脸搜索存储目录
     */
    public static void init(Context context) {
        // 人脸图存储在App内部私有空间，SDK未做分区存储
        // Warming: 目前仅能存储在context.getCacheDir() 或者context.getFilesDir() 内部私有空间
        // https://developer.android.com/training/data-storage?hl=zh-cn
        CACHE_BASE_FACE_DIR = context.getCacheDir().getPath() + "/faceAIVerify/";    //1:1 人脸识别目录
        CACHE_SEARCH_FACE_DIR = context.getFilesDir().getPath() + "/faceAISearch/";  //人脸搜索人脸库目录

        //语音提示播报
        VoicePlayer.getInstance().init(context);
    }


    /**
     * 检测人脸ID是否存在
     * @param faceID ID，Key
     * @return 存在与否
     */
    public static boolean isFaceIDExist(String faceID) {
        File file = new File(CACHE_BASE_FACE_DIR + faceID);
        return file.exists();
    }


    /**
     * 删除本地人脸ID
     * @param faceID ID，Key
     * @return 是否删除成功
     */
    public static boolean deleteFaceID(String faceID) {
        File file = new File(CACHE_BASE_FACE_DIR + faceID);
        return file.delete();
    }


    /**
     * 检测App 是否调试模式
     *
     * @param  mContext
     * @return
     */
    public static boolean isDebugMode(Context mContext){
        if (0 != (mContext.getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE)){
            //Debug 模式是打开状态
            return true;
        }
        return false;
    }

}
