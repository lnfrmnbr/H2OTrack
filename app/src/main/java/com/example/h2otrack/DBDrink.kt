package com.example.h2otrack

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBDrink(val context: Context, val factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, "h2otracker_db", factory, 1){
    override fun onCreate(db: SQLiteDatabase?) {
        val query = "CREATE TABLE drinks (id INT PRIMARY KEY, day INT, month INT, water INT, tea INT, coffee INT)"
        db!!.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS drinks")
        onCreate(db)
    }

    @SuppressLint("Range")
    fun changeWaterValue(id: Int, day: Int, month: Int, additionalMl: Int){
        val db = this.writableDatabase
        val query = "SELECT water FROM drinks WHERE id = '$id' AND day = '$day' AND month = '$month'"
        val cursor = db.rawQuery(query, null)
        var ml = 0
        if (cursor.moveToFirst()) {
            ml = cursor.getInt(3)
        }
        cursor.close()
        val contentValues = ContentValues()
        val updatedMl = ml + additionalMl
        contentValues.put("water", updatedMl)
        db.update("drinks", contentValues, "id = '$id' AND day = '$day' AND month = '$month'", null)
    }
}