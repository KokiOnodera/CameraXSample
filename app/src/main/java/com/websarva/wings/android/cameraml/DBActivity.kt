package com.websarva.wings.android.cameraml

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.widget.*
import java.io.ByteArrayOutputStream
import android.app.Application

/**
 * DB処理
 */

class DBActivity(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){
    //クラス内のpirvate定数を宣言するためにcompanion objectブロックとする。
    companion object {
        /**
         * データベースファイル名の定数フィールド。
         */
        private const val DATABASE_NAME = "userdata.db"
        /**
         * バージョン情報の定数フィールド。
         */
        private const val DATABASE_VERSION = 1
    }

    //データベースが存在しない時に一回だけ実行される
    override fun onCreate(db: SQLiteDatabase) {
        //テーブル作成用SQL文字列の作成。
        val sb = StringBuilder()
        //値が重複するためPRIMARY KEYは一旦使わない
        sb.append("CREATE TABLE userdata (")
        sb.append("_id INTEGER ,")
        sb.append("name TEXT")
        //sb.append("face_data TEXT,")
        //sb.append("acceleration_data TEXT,")
        //sb.append("angle_data TEXT,")
        sb.append(");")
        val sql = sb.toString()

        //SQLの実行
        db.execSQL(sql)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

    }
}
//    private val dbName: String = "DB"
//    private val tableName: String = "UserTable"
//    private val dbVersion: Int = 2
//    private var arrayListId: ArrayList<String> = arrayListOf()
//    private var arrayListName: ArrayList<String> = arrayListOf()
//    private var arrayListType: ArrayList<Int> = arrayListOf()
//    private var arrayListBitmap: ArrayList<Bitmap> = arrayListOf()
//
//
//
//    private class SampleDBHelper(context: Context, databaseName: String,
//                                 factory: SQLiteDatabase.CursorFactory?, version: Int) :
//            SQLiteOpenHelper(context, databaseName, factory, version) {
//
//        override fun onCreate(database: SQLiteDatabase?) {
//            database?.execSQL("create table if not exists SampleTable (id text , name text)")
//        }
//
//        override fun onUpgrade(database: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
//            if (oldVersion < newVersion) {
//                database?.execSQL("alter table SampleTable add column deleteFlag integer default 0")
//            }
//        }
//    }
//
//    fun insertData(id: String, name: String) {
//        try {
//            val dbHelper = SampleDBHelper(applicationContext, dbName, null, dbVersion);
//            val database = dbHelper.writableDatabase
//
//            val values = ContentValues()
//            values.put("id", id)
//            values.put("name", name)
////            values.put("type", type)
////            val byteArrayOutputStream = ByteArrayOutputStream();
////            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
////            val bytes = byteArrayOutputStream.toByteArray()
////            values.put("image", bytes)
//
//            database.insertOrThrow(tableName, null, values)
//        } catch (exception: Exception) {
//            Log.e("insertData", exception.toString())
//        }
//    }
//
//    private fun updateData(whereId: String, newName: String, newType: Int, newBitmap: Bitmap) {
//        try {
//            val dbHelper = SampleDBHelper(applicationContext, dbName, null, dbVersion);
//            val database = dbHelper.writableDatabase
//
//            val values = ContentValues()
//            values.put("name", newName)
//            values.put("type", newType)
//            val byteArrayOutputStream = ByteArrayOutputStream();
//            newBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
//            val bytes = byteArrayOutputStream.toByteArray()
//            values.put("image", bytes)
//
//            val whereClauses = "id = ?"
//            val whereArgs = arrayOf(whereId)
//            database.update(tableName, values, whereClauses, whereArgs)
//        } catch (exception: Exception) {
//            Log.e("updateData", exception.toString())
//        }
//    }

//     fun deleteData(whereId: String) {
//        try {
//            val dbHelper = SampleDBHelper(applicationContext, dbName, null, dbVersion);
//            val database = dbHelper.writableDatabase
//
//            val whereClauses = "id = ?"
//            val whereArgs = arrayOf(whereId)
//            database.delete(tableName, whereClauses, whereArgs)
//        } catch (exception: Exception) {
//            Log.e("deleteData", exception.toString())
//        }
//    }

//    @SuppressLint("Recycle")
//  fun selectData() {
//       try {
//            arrayListId.clear();arrayListName.clear();arrayListType.clear();arrayListBitmap.clear()
//
//            val dbHelper = SampleDBHelper(applicationContext, dbName, null, dbVersion)
//            val database = dbHelper.readableDatabase
//
//            val sql = "select name from $tableName where id='1'"
//            val cursor = database.rawQuery(sql, null)
//            if (cursor.count > 0) {
//                cursor.moveToFirst()
//                while (!cursor.isAfterLast) {
//                    arrayListId.add(cursor.getString(0))
//                    arrayListName.add(cursor.getString(1))
//                    arrayListType.add(cursor.getInt(2))
//                    val blob: ByteArray = cursor.getBlob(3)
//                    val bitmap = BitmapFactory.decodeByteArray(blob, 0, blob.size)
//                    arrayListBitmap.add(bitmap)
//                    cursor.moveToNext()
//                }
            //}
//        } catch (exception: Exception) {
//            Log.e("selectData", exception.toString());
//        }
//    }
//}
