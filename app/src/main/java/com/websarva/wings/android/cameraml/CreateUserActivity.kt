package com.websarva.wings.android.cameraml

import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

//ユーザ登録画面
//MainActivityから呼び出される

class CreateUserActivity : AppCompatActivity(){
    /**
     * 選択されたカクテルの主キーIDを表すフィールド。
     */
    private var _userId = -1
    /**
     * 選択されたカクテル名を表すフィールド。
     */
    private var _userName = ""
    /**
     * データベースヘルパーオブジェクト。
     */
    private val _helper = DBActivity(this@CreateUserActivity)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_regist)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        val CreateUserButton = findViewById<Button>(R.id.camera_capture_button)


        //[OK]ボタンが押されたら
        CreateUserButton.setOnClickListener {
            //DBActivity().insertData("1","Takuya")

            //名前入力欄の名前を取得。
            val etName = findViewById<EditText>(R.id.editTextPersonName)
            //入力された名前を取得。
            val name = etName.text.toString()

            //データベースヘルパーオブジェクトからデータベース接続オブジェクトを取得。
            val db = _helper.writableDatabase

            //まず、リストで選択されたカクテルのメモデータを削除。その後インサートを行う。
            //削除用SQL文字列を用意。
            //val sqlDelete = "DELETE FROM userdata WHERE _id = ?"
            //SQL文字列を元にプリペアドステートメントを取得。
            //var stmt = db.compileStatement(sqlDelete)
            //変数のバイド。
            //stmt.bindLong(1, _userId.toLong())
            //削除SQLの実行。
            //stmt.executeUpdateDelete()

            //インサート用SQL文字列の用意。
            val sqlInsert = "INSERT INTO userdata (_id, name) VALUES (?, ?)"
            //SQL文字列を元にプリペアドステートメントを取得。
            val stmt = db.compileStatement(sqlInsert)
            //変数のバイド。
            stmt.bindLong(1, _userId.toLong())
            stmt.bindString(2, name)
            //インサートSQLの実行。
            stmt.executeInsert()

            //感想欄の入力値を消去。
            //etName.setText("")
            //カクテル名を表示するTextViewを取得。
            //val tvCocktailName = findViewById<TextView>(R.id.user_list)
            //カクテル名を「未選択」に変更。
            //tvCocktailName.text = getString(R.string.tv_name)
            //保存ボタンを取得。
            //val btnSave = findViewById<Button>(R.id.btnSave)
            //保存ボタンをタップできないように変更。
            //btnSave.isEnabled = false
            val intent = Intent(application, FaceRecognitionTutorial::class.java)
            startActivity(intent)
        }
    }
    override fun onDestroy() {
        //ヘルパーオブジェクトの開放。
        _helper.close()
        super.onDestroy()
    }
}