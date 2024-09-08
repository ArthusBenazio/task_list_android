package com.arthusbenazio.apptaskslist

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.arthusbenazio.apptaskslist.database.TaskDAO
import com.arthusbenazio.apptaskslist.databinding.ActivityTaskAddBinding
import com.arthusbenazio.apptaskslist.model.Task

class TaskAddActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityTaskAddBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        var task: Task? = null
        val bundle = intent.extras

        if (bundle != null) {
            task = bundle.getSerializable("task") as Task
            binding.editTarefa.setText(task.description)
        }

        binding.btnSalvar.setOnClickListener {

            if (binding.editTarefa.text.isNotEmpty()) {

                if (task != null) {
                    edit(task)
                } else {
                    save()
                }

            } else {
                Toast.makeText(this, "Preencha uma tarefa", Toast.LENGTH_SHORT).show()
            }

        }
    }

    private fun edit(task: Task) {
        val description = binding.editTarefa.text.toString()
        val updateTask = Task(
            task.idTask, description, "default"
        )
        val taskDAO = TaskDAO(this)

        if (taskDAO.update(updateTask)) {
            Toast.makeText(
                this,
                "Tarefa atualizada com sucesso",
                Toast.LENGTH_SHORT
            ).show()
            finish()
        }
    }

    private fun save() {
        val description = binding.editTarefa.text.toString()
        val task = Task(
            -1,
            description,
            "default"
        )

        val taskDAO = TaskDAO(this)
        if (taskDAO.save(task)) {
            Toast.makeText(
                this,
                "Tarefa cadastrada com sucesso",
                Toast.LENGTH_SHORT
            ).show()
            finish()
        }
    }
}