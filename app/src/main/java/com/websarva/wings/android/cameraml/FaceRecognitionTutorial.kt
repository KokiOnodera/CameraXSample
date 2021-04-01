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
        //新規作成ボタンが押されたら
        StartButton.setOnClickListener {
            val intent = Intent(application, FaceDetectionActivity::class.java)
            startActivity(intent)
        }
    }
}