package com.example.h2otrack

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DBHelper(val context: Context, val factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, "h2otracker_db", factory, 2){

    override fun onCreate(db: SQLiteDatabase?) {
        val query1 = "CREATE TABLE drinks (id INT PRIMARY KEY, day INT, month INT, water INT, tea INT, coffee INT)"
        val query = "CREATE TABLE users (id INT PRIMARY KEY, name TEXT, sex TEXT, weight INT, activity INT, pass TEXT)"
        db!!.execSQL(query)
        db.execSQL(query1)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS users")
        db.execSQL("DROP TABLE IF EXISTS drinks")
        onCreate(db)
    }

    private fun generateUniquePrimaryKey(): Int {
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
        val cursor = db.rawQuery("SELECT * FROM users WHERE name = '$name' AND pass = '$pass'", null)
        val result = cursor.moveToFirst()
        cursor.close()
        return result
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
        val query = "SELECT * FROM drinks"

        val cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(0)
                val day = cursor.getString(1)
                val month = cursor.getInt(2)
                val w = cursor.getString(3)
                Log.e("DEBUG","ID: $id, day: $day, month: $month  , w: $w")

            } while (cursor.moveToNext())
        }

        cursor.close()
    }

    fun addDataForDay(id: Int, day: Int, month: Int){                     //vika v123
        val values = ContentValues()
        values.put("id", id)
        values.put("day", day)
        values.put("month", month)
        values.put("water", 0)
        values.put("tea", 0)
        values.put("coffee", 0)

        val db = this.writableDatabase
        db.insert("drinks", null, values)
        db.close()
    }

    fun checkDataForDay(id: Int, day: Int, month: Int): Boolean{
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM drinks WHERE id = '$id' AND day = '$day' AND month = '$month'", null)
        val result = cursor.moveToFirst()
        cursor.close()
        return result
    }

    fun getCurrentMlOfDay(id: Int, day: Int, month: Int): Int{
        val db = this.readableDatabase
        val query = "SELECT water FROM drinks WHERE id = '$id' AND day = '$day' AND month = '$month'"
        val cursor = db.rawQuery(query, null)
        var ml = 0
        if (cursor.moveToFirst()) {
            ml = cursor.getInt(0)
        }
        cursor.close()
        return ml
    }


    fun changeWaterValue(id: Int, day: Int, month: Int, additionalMl: Int){
        val db = this.writableDatabase
        val currentMl = getCurrentMlOfDay(id, day, month)
        val contentValues = ContentValues()
        val updatedMl = currentMl + additionalMl
        contentValues.put("water", updatedMl)
        db.update("drinks", contentValues, "id = '$id' AND day = '$day' AND month = '$month'", null)
    }

}