package com.websarva.wings.android.cameraml

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.*
import androidx.camera.core.CameraX.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetectorOptions
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


//顔認証画面

private class FaceAnalyzer(private var listener: (Int) -> Unit) : ImageAnalysis.Analyzer {
    private val detector = FaceDetection.getClient()

    @ExperimentalGetImage
    override fun analyze(imageProxy: ImageProxy) {

        val mediaImage = imageProxy.image ?: return
        val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)

        detector.process(image)
                .addOnSuccessListener { faces ->
                    listener(faces.size)
                }
                .addOnFailureListener { e ->
                    Log.e("FaceAnalyzer", "Face detection failure.", e)
                }
                .addOnCompleteListener {
                    imageProxy.close()
                }
    }
}

open class FaceDetectionActivity: AppCompatActivity(){
    private var imageCapture: ImageCapture? = null

    private lateinit var outputDirectory: File
    private lateinit var cameraExecutor: ExecutorService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Request camera permissions
        if (allPermissionsGranted()) {
            startCamera()
        } else {
            ActivityCompat.requestPermissions(
                    this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS)
        }

        // 写真を撮るボタンのリスナーを設定する
        eye_feature_value.setOnClickListener {
            //takePhoto()
        }

        outputDirectory = getOutputDirectory()

        cameraExecutor = Executors.newSingleThreadExecutor()


        //https://developers.google.com/ml-kit/vision/face-detection/android
        val highAccuracyOpts = FaceDetectorOptions.Builder()
            .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_ACCURATE)
            .setLandmarkMode(FaceDetectorOptions.LANDMARK_MODE_ALL)
            .setClassificationMode(FaceDetectorOptions.CLASSIFICATION_MODE_ALL)
            .build()

        // リアルタイムの輪郭検出
        val realTimeOpts = FaceDetectorOptions.Builder()
            .setContourMode(FaceDetectorOptions.CONTOUR_MODE_ALL)
            .build()
    }

    @SuppressLint("RestrictedApi", "SetTextI18n")
    //プレビューのユースケース
    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener(Runnable {
            // カメラのライフサイクルをライフサイクル所有者にバインドするために使用されます
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            // Preview
            val preview = Preview.Builder()
                    .build()
                    .also {
                        it.setSurfaceProvider(viewFinder.surfaceProvider)
                    }

            imageCapture = ImageCapture.Builder()
                    .build()

            //activity_main.xmlで設定したtextview（目の特徴値）に数値を設定
            val textView = findViewById<View>(R.id.eye_feature_value) as TextView
            //試験的に42を設定
            val value: Int = 42
            textView.setText(value.toString())

            val feature = ImageAnalysis.Builder()
                    .build()
                    .also {
                        it.setAnalyzer(cameraExecutor, FaceAnalyzer { faces ->
                            Log.d(TAG, "Face detected: $faces")
                            eye_feature_value.setEnabled(faces > 0)
                        })
                    }

            // デフォルトとしてフロントカメラを選択
            val cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA

            try {
                // 再バインドする前にユースケースのバインドを解除する
                cameraProvider.unbindAll()

                // ユースケースをカメラにバインドする
                cameraProvider.bindToLifecycle(
                        this, cameraSelector, preview, imageCapture, feature)

            } catch (exc: Exception) {
                Log.e(TAG, "Use case binding failed", exc)
            }

        }, ContextCompat.getMainExecutor(this))
    }

//    //ImageCaptureユースケース
//    private fun takePhoto() {
//        // 変更可能な画像キャプチャのユースケースの安定したリファレンスを取得する
//        val imageCapture = imageCapture ?: return
//
//        // 画像を保持するためのタイムスタンプ付き出力ファイルを作成します
//        val photoFile = File(
//                outputDirectory,
//                SimpleDateFormat(FILENAME_FORMAT, Locale.US
//                ).format(System.currentTimeMillis()) + ".jpg")
//
//        // ファイルとメタデータを含む出力オプションオブジェクトを作成します
//        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()
//
//        // Set up image capture listener, which is triggered after photo has
//        // been taken
//        imageCapture.takePicture(
//                outputOptions, ContextCompat.getMainExecutor(this), object : ImageCapture.OnImageSavedCallback {
//            override fun onError(exc: ImageCaptureException) {
//                Log.e(TAG, "Photo capture failed: ${exc.message}", exc)
//            }
//
//            override fun onImageSaved(output: ImageCapture.OutputFileResults) {
//                val savedUri = Uri.fromFile(photoFile)
//                val msg = "Photo capture succeeded: $savedUri"
//                Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
//                Log.d(TAG, msg)
//            }
//        })
//    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
                baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    private fun getOutputDirectory(): File {
        val mediaDir = externalMediaDirs.firstOrNull()?.let {
            File(it, resources.getString(R.string.app_name)).apply { mkdirs() } }
        return if (mediaDir != null && mediaDir.exists())
            mediaDir else filesDir
    }

    override fun onRequestPermissionsResult(
            requestCode: Int, permissions: Array<String>, grantResults:
            IntArray) {
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                startCamera()
            } else {
                Toast.makeText(this,
                        "Permissions not granted by the user.",
                        Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }

    companion object {
        private const val TAG = "CameraXBasic"
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
    }
}