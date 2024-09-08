package com.arthusbenazio.apptaskslist.model

import java.io.Serializable

data class Task (
    val idTask: Int,
    val description: String,
    val dateRegister: String
        ) : Serializable