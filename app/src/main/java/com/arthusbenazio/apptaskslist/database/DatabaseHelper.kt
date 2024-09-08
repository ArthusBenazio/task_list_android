package com.arthusbenazio.apptaskslist.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DatabaseHelper(context: Context): SQLiteOpenHelper(
    context, NAME_DB, null, VERSION
) {

    companion object {
        const val NAME_DB = "TaskList.db"
        const val VERSION = 1
        const val NAME_TABLE_TASKS = "tasks"
        const val COLUMN_ID_TASKS = "id_task"
        const val COLUMN_DESCRIPTION = "description"
        const val COLUMN_DATE_REGISTER = "date_register"

    }

    override fun onCreate(db: SQLiteDatabase?) {
        val sql = "CREATE TABLE IF NOT EXISTS $NAME_TABLE_TASKS(" +
                "$COLUMN_ID_TASKS INTEGER not NULL PRIMARY KEY AUTOINCREMENT," +
                "$COLUMN_DESCRIPTION VARCHAR(70)," +
                "$COLUMN_DATE_REGISTER DATETIME NOT NULL DEFAULT CURENT_TIMESTAMP" +
                ");"

        try {
            db?.execSQL(sql)
            Log.i("info_db","Sucesso ao criar tabela")
        } catch (e: Exception) {
            e.printStackTrace()
            Log.i("info_db","Erro ao criar tabela")
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }
}