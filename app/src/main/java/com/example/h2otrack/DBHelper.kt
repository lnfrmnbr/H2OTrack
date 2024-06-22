package com.example.h2otrack

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

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

    fun addUser(user: User){                     //vika v123
        val values = ContentValues()
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
}