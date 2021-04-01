package com.websarva.wings.android.cameraml

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import android.view.ViewParent
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import java.text.FieldPosition


//MainActivityはホーム画面にします
//ユーザを押したらFaceDetectionActivityが呼び出される
//ユーザ登録ボタンやユーザ選択欄も設けらせている


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mainmenu)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        val CreateUserButton = findViewById<Button>(R.id.create_user_button)

        // user_listに表示するリスト項目をArrayListで準備する
        //今後、データベースサーバから登録されているユーザ名を持ってくる
        val data = ArrayList<Any>()
        data.add("admin")
        data.add("")
        data.add("")
        data.add("")
        data.add("")
        data.add("")
        data.add("")
        data.add("")
        data.add("")
        // リスト項目とListViewを対応付けるArrayAdapterを用意する
        val adapter: ArrayAdapter<*> = ArrayAdapter(this, android.R.layout.simple_list_item_1, data)
        // ListViewにArrayAdapterを設定する
        val listView: ListView = findViewById<View>(R.id.user_list) as ListView
        listView.setAdapter(adapter)
        //val userlist = findViewById<ListView>(R.id.user_list)
        listView.onItemClickListener = ListItemClickListener()

        //新規作成ボタンが押されたら
        CreateUserButton.setOnClickListener {
            val intent = Intent(application, CreateUserActivity::class.java)
            startActivity(intent)
        }
    }

    //ユーザリストがタップされた時の処理が記述されたメンバクラス
    private inner class ListItemClickListener:OnItemClickListener{
        override fun onItemClick(parent: AdapterView<*>,view: View,position: Int,id:Long){
            //現時点ではadminを押すと顔認証の画面に遷移（4/1木）
            val user = parent.getItemAtPosition(position) as String
            if (user==="admin") {
                val intent = Intent(application, FaceDetectionActivity::class.java)
                startActivity(intent)
            }
        }
    }
}