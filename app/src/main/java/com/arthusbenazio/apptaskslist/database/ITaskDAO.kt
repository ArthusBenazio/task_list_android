package com.arthusbenazio.apptaskslist.database

import com.arthusbenazio.apptaskslist.model.Task

interface ITaskDAO {

    fun save(task: Task): Boolean
    fun update(task: Task): Boolean
    fun delete(idTask: Int): Boolean
    fun list(): List<Task>
}