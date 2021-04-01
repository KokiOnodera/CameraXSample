package com.websarva.wings.android.cameraml

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity


//MainActivityはホーム画面にします
//スタートボタンを押したらFaceDetectionActivityが呼び出される
//ゆくゆくはユーザ登録ボタンやユーザ選択欄も設ける
//まずxmlでメイン画面作成


class MainActivity : AppCompatActivity(){
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
        //listView.setOnItemClickListener(ListItemClickListener())

        //新規作成ボタンが押されたら
        CreateUserButton.setOnClickListener {
            //val intent = Intent(application, FaceDetectionActivity::class.java)
            val intent = Intent(application, CreateUserActivity::class.java)
            startActivity(intent)
        }
        class ListItemClickListener : OnItemClickListener {
            override fun onItemClick(parent: AdapterView<*>, view: View, position: Int, l: Long) {
                val prefName = parent.getItemAtPosition(position) as String

                val intent = Intent(application, CreateUserActivity::class.java)
            }
        }
    }

    private fun ListItemClickListener() {
       fun onItemClick(parent: AdapterView<*>, view: View, position: Int, l: Long) {
            val prefName = parent.getItemAtPosition(position) as String

            val intent = Intent(application, CreateUserActivity::class.java)
        }
    }


}