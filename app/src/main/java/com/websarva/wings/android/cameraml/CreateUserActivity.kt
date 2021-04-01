package com.websarva.wings.android.cameraml

import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

//ユーザ登録画面
//MainActivityから呼び出される

class CreateUserActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_regist)
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        //ボタンが押されたら
        val CreateUserButton = findViewById<Button>(R.id.camera_capture_button)
        CreateUserButton.setOnClickListener {
            //val intent = Intent(application, FaceDetectionActivity::class.java)
            val intent = Intent(application, FaceRecognitionTutorial::class.java)
            startActivity(intent)
        }
    }
}