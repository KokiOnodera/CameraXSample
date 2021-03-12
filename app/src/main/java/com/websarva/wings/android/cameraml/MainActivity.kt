package com.websarva.wings.android.cameraml

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraManager
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.util.SparseIntArray
import android.view.Surface
import android.view.View
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.common.FirebaseVisionImageMetadata
import com.google.firebase.ml.vision.face.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
    //private lateinit var firebaseAnalytics: FirebaseAnalytics

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        //カメラアプリからの戻り値でかつ撮影成功の場合
        if (resultCode == RESULT_OK) {
            //撮影された画像のビットマップデータを取得。
            val bitmap = data?.getParcelableExtra<Bitmap>("data")
            //画像を表示するImageViewを取得
            val ivCamera = findViewById<ImageView>(R.id.ivCamera)
            //撮影された画像をImageViewに設定
            ivCamera.setImageBitmap(bitmap)
        }
    }

    fun onCameraImageClick(view: View) {
        //intentオブジェクトを生成
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        //アクティビティを起動
        startActivityForResult(intent, 200)
    }

    //ここから顔認証記述　**MLkitを利用　URL:https://firebase.google.com/docs/ml-kit/android/detect-faces?hl=ja**

    // High-accuracy landmark detection and face classification
    val highAccuracyOpts = FirebaseVisionFaceDetectorOptions.Builder()
            .setPerformanceMode(FirebaseVisionFaceDetectorOptions.ACCURATE)
            .setLandmarkMode(FirebaseVisionFaceDetectorOptions.ALL_LANDMARKS)
            .setClassificationMode(FirebaseVisionFaceDetectorOptions.ALL_CLASSIFICATIONS)
            .build()

    // Real-time contour detection of multiple faces
    val realTimeOpts = FirebaseVisionFaceDetectorOptions.Builder()
            .setContourMode(FirebaseVisionFaceDetectorOptions.ALL_CONTOURS)
            .build()

//    private class YourImageAnalyzer : ImageAnalysis.Analyzer {
//        private fun degreesToFirebaseRotation(degrees: Int): Int = when (degrees) {
//            0 -> FirebaseVisionImageMetadata.ROTATION_0
//            90 -> FirebaseVisionImageMetadata.ROTATION_90
//            180 -> FirebaseVisionImageMetadata.ROTATION_180
//            270 -> FirebaseVisionImageMetadata.ROTATION_270
//            else -> throw Exception("Rotation must be 0, 90, 180, or 270.")
//        }
//        override fun analyze(imageProxy: ImageProxy?, degrees: Int) {
//            val mediaImage = imageProxy?.image
//            val imageRotation = degreesToFirebaseRotation(degrees)
//            if (mediaImage != null) {
//                val image = FirebaseVisionImage.fromMediaImage(mediaImage, imageRotation)
//                // Pass image to an ML Kit Vision API
//                // ...
//            }
//        }
//    }
    private val ORIENTATIONS = SparseIntArray()

    init {
        ORIENTATIONS.append(Surface.ROTATION_0, 90)
        ORIENTATIONS.append(Surface.ROTATION_90, 0)
        ORIENTATIONS.append(Surface.ROTATION_180, 270)
        ORIENTATIONS.append(Surface.ROTATION_270, 180)
    }
    /**
     * Get the angle by which an image must be rotated given the device's current
     * orientation.
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Throws(CameraAccessException::class)
    private fun getRotationCompensation(cameraId: String, activity: Activity, context: Context): Int {
        // Get the device's current rotation relative to its "native" orientation.
        // Then, from the ORIENTATIONS table, look up the angle the image must be
        // rotated to compensate for the device's rotation.
        val deviceRotation = activity.windowManager.defaultDisplay.rotation
        var rotationCompensation = ORIENTATIONS.get(deviceRotation)

        // On most devices, the sensor orientation is 90 degrees, but for some
        // devices it is 270 degrees. For devices with a sensor orientation of
        // 270, rotate the image an additional 180 ((270 + 270) % 360) degrees.
        val cameraManager = context.getSystemService(CAMERA_SERVICE) as CameraManager
        val sensorOrientation = cameraManager
                .getCameraCharacteristics(cameraId)
                .get(CameraCharacteristics.SENSOR_ORIENTATION)!!
        rotationCompensation = (rotationCompensation + sensorOrientation + 270) % 360

        // Return the corresponding FirebaseVisionImageMetadata rotation value.
        val result: Int
        when (rotationCompensation) {
            0 -> result = FirebaseVisionImageMetadata.ROTATION_0
            90 -> result = FirebaseVisionImageMetadata.ROTATION_90
            180 -> result = FirebaseVisionImageMetadata.ROTATION_180
            270 -> result = FirebaseVisionImageMetadata.ROTATION_270
            else -> {
                result = FirebaseVisionImageMetadata.ROTATION_0
                //Log.e(TAG, "Bad rotation value: $rotationCompensation")
            }
        }
        return result
    }
}

