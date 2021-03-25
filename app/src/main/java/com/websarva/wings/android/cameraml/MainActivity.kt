package com.websarva.wings.android.cameraml

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

//MainActivityはホーム画面にします
//スタートボタンを押したらFaceDetectionActivityが呼び出される
//ゆくゆくはユーザ登録ボタンやユーザ選択欄も設ける
//まずxmlでメイン画面作成


class MainActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mainmenu)
        val StartButton = findViewById<Button>(R.id.measure_start_button)
        //ボタンが押されたら
        StartButton.setOnClickListener {
            val intent = Intent(application, FaceDetectionActivity::class.java)
            startActivity(intent)
        }
    }
}