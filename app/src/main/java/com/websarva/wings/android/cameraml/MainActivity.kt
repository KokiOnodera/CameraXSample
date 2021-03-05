package com.websarva.wings.android.cameraml

import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.ImageView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

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
}

