package com.arthusbenazio.apptaskslist.database

import android.content.ContentValues
import android.content.Context
import android.util.Log
import com.arthusbenazio.apptaskslist.model.Task

class TaskDAO(context: Context) : ITaskDAO {

    private val writing = DatabaseHelper(context).writableDatabase
    private val read = DatabaseHelper(context).readableDatabase

    override fun save(task: Task): Boolean {

        val content = ContentValues()
        content.put("${DatabaseHelper.COLUMN_DESCRIPTION}", task.description)

        try {
            writing.insert(
                DatabaseHelper.NAME_TABLE_TASKS,
                null,
                content
            )
            Log.i("info_db", "Sucesso ao salvar tarefa")
        } catch (e: Exception) {
            e.printStackTrace()
            Log.i("info_db", "Erro ao salvar tarefa")
            return false
        }
        return true
    }

    override fun update(task: Task): Boolean {


        val args = arrayOf(task.idTask.toString())
        val content = ContentValues()
        content.put("${DatabaseHelper.COLUMN_DESCRIPTION}", task.description)

        try {
            read.update(
                DatabaseHelper.NAME_TABLE_TASKS,
                content,
                "${DatabaseHelper.COLUMN_ID_TASKS} = ?",
                args
            )
            Log.i("info_db", "Sucesso ao atualizar tarefa")
        } catch (e: Exception) {
            e.printStackTrace()
            Log.i("info_db", "Erro ao atualizar tarefa")
            return false
        }

        return true
    }

    override fun delete(idTask: Int): Boolean {

        val args = arrayOf(idTask.toString())
        try {
            read.delete(
                DatabaseHelper.NAME_TABLE_TASKS,
                "${DatabaseHelper.COLUMN_ID_TASKS} = ?",
                args
            )
            Log.i("info_db", "Sucesso ao remover tarefa")
        } catch (e: Exception) {
            e.printStackTrace()
            Log.i("info_db", "Erro ao remover tarefa")
            return false
        }

        return true
    }

    override fun list(): List<Task> {

        val taskList = mutableListOf<Task>()

//        val sql = "SELECT ${DatabaseHelper.COLUMN_ID_TASKS}," +
//                "${DatabaseHelper.COLUMN_DESCRIPTION}," +
//                "strftime('%d/%m/%Y %H:%M', ${DatabaseHelper.COLUMN_DATE_REGISTER}) date_register"
//                " FROM ${DatabaseHelper.NAME_TABLE_TASKS}"

        val sql = "SELECT ${DatabaseHelper.COLUMN_ID_TASKS}," +
                "${DatabaseHelper.COLUMN_DESCRIPTION}," +
                "coalesce(strftime('%d/%m/%Y %H:%M', ${DatabaseHelper.COLUMN_DATE_REGISTER}), '') as date_register" +
                " FROM ${DatabaseHelper.NAME_TABLE_TASKS}"


        val cursor = read.rawQuery(sql, null)

        val indexId = cursor.getColumnIndex(DatabaseHelper.COLUMN_ID_TASKS)
        val indexDescription = cursor.getColumnIndex(DatabaseHelper.COLUMN_DESCRIPTION)
        val indexDate = cursor.getColumnIndex(DatabaseHelper.COLUMN_DATE_REGISTER)

        while (cursor.moveToNext()) {

            val idTask = cursor.getInt(indexId)
            val description = cursor.getString(indexDescription)
            val dateRegister = cursor.getString(indexDate)

            taskList.add(
                Task(idTask, description, dateRegister)
            )
        }

        return taskList
    }
}