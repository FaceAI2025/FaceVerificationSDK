package com.ai.face

import android.Manifest
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ai.face.MyFaceApplication.BASE_FACE_DIR_KEY
import com.ai.face.MyFaceApplication.CACHE_BASE_FACE_DIR
import com.ai.face.MyFaceApplication.USER_FACE_ID_KEY
import com.ai.face.addFaceImage.AddFaceImageActivity
import com.ai.face.databinding.ActivityNaviBinding
import com.ai.face.faceVerify.verify.VerifyUtils
import com.ai.face.search.SearchNaviActivity
import com.ai.face.utils.VoicePlayer
import com.ai.face.verify.FaceVerificationActivity
import com.tencent.bugly.crashreport.CrashReport
import pub.devrel.easypermissions.EasyPermissions
import pub.devrel.easypermissions.EasyPermissions.PermissionCallbacks

/**
 * SDK 接入演示Demo，请先熟悉本Demo跑通住流程后再集成到你的主工程验证业务
 *
 *
 */
class NaviActivity : AppCompatActivity(), PermissionCallbacks {
    private lateinit var viewBinding: ActivityNaviBinding

    private var yourUniQueFaceId = "18707611416"  //用户人脸ID，Face ID（unique）eg account

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityNaviBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        checkNeededPermission()


        //测试两张静态人脸图是否同一人
        val value = VerifyUtils.evaluateFaceSimi(
            baseContext,
            VerifyUtils.getBitmapFromAssert(baseContext, "model_1.png"),
            VerifyUtils.getBitmapFromAssert(baseContext, "model.jpg")
        )

        Log.d("VerifyUtils", "two face similarity value:  $value")


        viewBinding.faceVerifyCard.setOnClickListener {
            //1:1 人脸识别先录一张人脸底片
            var baseFacePath=CACHE_BASE_FACE_DIR+yourUniQueFaceId
            if (BitmapFactory.decodeFile(baseFacePath) != null) {
                startActivity(
                    Intent(this@NaviActivity, FaceVerificationActivity::class.java)
                        .putExtra(USER_FACE_ID_KEY, yourUniQueFaceId)     //1:1 底片人脸ID
                        .putExtra(BASE_FACE_DIR_KEY, CACHE_BASE_FACE_DIR) //保存目录
                )
            } else {
                Toast.makeText(this@NaviActivity, R.string.add_a_face_image, Toast.LENGTH_LONG).show()
            }
        }


        //添加1：1人脸识别底片
        viewBinding.updateBaseImage.setOnClickListener {
            startActivity(
                Intent(this@NaviActivity, AddFaceImageActivity::class.java)
                    .putExtra(USER_FACE_ID_KEY, yourUniQueFaceId)
                    .putExtra(BASE_FACE_DIR_KEY, CACHE_BASE_FACE_DIR)
            )
        }

        viewBinding.livenessDetection.setOnClickListener {
//            startActivity(Intent(this@NaviActivity, LivenessDetectionActivity::class.java))
        }

        viewBinding.faceSearch1N.setOnClickListener {
            startActivity(Intent(this@NaviActivity, SearchNaviActivity::class.java))
        }


        viewBinding.moreAboutMe.setOnClickListener {
            startActivity(Intent(this@NaviActivity, AboutFaceAppActivity::class.java))
        }


        viewBinding.changeCamera.setOnClickListener {
            val sharedPref = getSharedPreferences(
                "faceVerify", Context.MODE_PRIVATE
            )

            if (sharedPref.getInt("cameraFlag", 0) == 1) {
                sharedPref.edit().putInt("cameraFlag", 0).apply()
                Toast.makeText(
                    baseContext,
                    "Front camera",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                sharedPref.edit().putInt("cameraFlag", 1).apply()
                Toast.makeText(
                    baseContext,
                    "Back/USB Camera",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        VoicePlayer.getInstance().init(this)


        //Crash 收集，仅仅是Demo 需要。这不是SDK 的一部分
        CrashReport.initCrashReport(this, "36fade54d8", true)
    }


    /**
     * 统一全局的拦截权限请求，给提示
     *
     */
    private fun checkNeededPermission() {
        val perms = arrayOf(Manifest.permission.CAMERA)

        if (!EasyPermissions.hasPermissions(this, *perms)) {
            EasyPermissions.requestPermissions(
                this,
                "SDK Demo 相机和读取相册都仅仅是为了完成人脸识别所必需，请授权！",
                11,
                *perms
            )
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onPermissionsGranted(requestCode: Int, perms: List<String>) {

    }


    /**
     * 当用户点击了不再提醒的时候的处理方式
     */
    override fun onPermissionsDenied(requestCode: Int, perms: List<String>) {
        Toast.makeText(this, "Please Oauth Permission,请授权才能正常演示", Toast.LENGTH_SHORT).show()
    }

}