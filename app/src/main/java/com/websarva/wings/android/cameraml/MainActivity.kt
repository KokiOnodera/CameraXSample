package com.websarva.wings.android.cameraml

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


//MainActivityはホーム画面にします
//ユーザを押したらFaceDetectionActivityが呼び出される
//ユーザ登録ボタンやユーザ選択欄も設けらせている
//DBからユーザを取得し、リストに追加
//

class MainActivity : AppCompatActivity() {
    /**
     * 選択されたユーザの主キーIDを表すフィールド。
     */
    private var _userId = -1
    /**
     * 選択されたユーザ名を表すフィールド。
     */
    private var _userName = ""
    /**
     * データベースヘルパーオブジェクト。
     */
    private val _helper = DBActivity(this@MainActivity)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mainmenu)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        //DBActivity()
        UsersList()

        val CreateUserButton = findViewById<Button>(R.id.create_user_button)
        //新規作成ボタンが押されたら
        CreateUserButton.setOnClickListener {

            val intent = Intent(application, CreateUserActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onDestroy() {
        //ヘルパーオブジェクトの開放。
        _helper.close()
        super.onDestroy()
    }

    //ユーザリストがタップされた時の処理が記述されたメンバクラス
    private inner class ListItemClickListener:OnItemClickListener{
        override fun onItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long){
            //現時点ではadminを押すと顔認証の画面に遷移（4/1木）
            val user = parent.getItemAtPosition(position) as String
            if (user==="admin") {
                val intent = Intent(application, FaceDetectionActivity::class.java)
                startActivity(intent)
            }
        }
    }

    fun UsersList(){
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
        listView.adapter = adapter
        listView.onItemClickListener = ListItemClickListener()
    }

    companion object {
       const val TAG = "アクセス："
    }
}