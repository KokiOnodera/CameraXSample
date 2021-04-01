package com.websarva.wings.android.cameraml

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import androidx.core.content.ContextCompat.startActivity

class FaceRecognitionTutorial:AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.regist_tutorial)
        val StartButton = findViewById<Button>(R.id.start_button)
        //開始ボタンが押されたら
        StartButton.setOnClickListener {
            //顔認証の画面に遷移
            val intent = Intent(application, FaceDetectionActivity::class.java)
            startActivity(intent)
        }
    }
}