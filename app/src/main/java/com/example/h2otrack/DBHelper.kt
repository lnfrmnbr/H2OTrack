package com.example.h2otrack

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DBHelper(val context: Context, val factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, "h2otracker_db", factory, 1){

    override fun onCreate(db: SQLiteDatabase?) {
        val query = "CREATE TABLE users (id INT PRIMARY KEY, name TEXT, sex TEXT, weight INT, activity INT, pass TEXT)"
        db!!.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS users")
        onCreate(db)
    }

    fun generateUniquePrimaryKey(): Int {
        val db = this.readableDatabase
        var key: Int
        do {
            key = (1..1000).random()
        } while (db.rawQuery("SELECT * FROM users WHERE id = '$key'", null).moveToFirst())

        return key
    }

    fun addUser(user: User){                     //vika v123
        val values = ContentValues()
        values.put("id", generateUniquePrimaryKey())
        values.put("name", user.name)
        values.put("sex", user.sex)
        values.put("weight", user.weight)
        values.put("activity", user.activity)
        values.put("pass", user.pass)

        val db = this.writableDatabase
        db.insert("users", null, values)
        db.close()
    }

    fun checkUser(name: String, pass: String): Boolean{
        val db = this.readableDatabase
        val result = db.rawQuery("SELECT * FROM users WHERE name = '$name' AND pass = '$pass'", null)
        return result.moveToFirst()
    }

    fun getId(name: String, pass: String): Int{
        val db = this.readableDatabase
        var userid = 0
        val result = db.rawQuery("SELECT id FROM users WHERE name = '$name' AND pass = '$pass'", null)
        if (result.moveToFirst()){
            userid = result.getInt(0)
        }
        result.close()
        Log.e("DEBUG", "in getId = '$userid'")
        return userid
    }

    fun displayAllData() {
        val db = this.readableDatabase
        val query = "SELECT * FROM users"

        val cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(0)
                val name = cursor.getString(1)
                val pass = cursor.getString(5)
                Log.e("DEBUG","ID: $id, Name: $name, pass: $pass")

            } while (cursor.moveToNext())
        }

        cursor.close()
    }

}