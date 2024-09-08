package com.arthusbenazio.apptaskslist

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.arthusbenazio.apptaskslist.adapter.TaskAdapter
import com.arthusbenazio.apptaskslist.database.TaskDAO
import com.arthusbenazio.apptaskslist.databinding.ActivityMainBinding
import com.arthusbenazio.apptaskslist.databinding.ActivityTaskAddBinding
import com.arthusbenazio.apptaskslist.model.Task

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private var taskList = emptyList<Task>()
    private var taskAdapter: TaskAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setTitle(R.string.app_name)

        binding.fabAdd.setOnClickListener {

            val intent = Intent(this, TaskAddActivity::class.java)

            startActivity(intent)
        }

        taskAdapter = TaskAdapter(
            { id ->
                confirmDelete(id)
            },
            { task ->
                edit(task)
            }
        )

        binding.rvTasks.adapter = taskAdapter

        binding.rvTasks.layoutManager = LinearLayoutManager(this)

    }

    private fun edit(task: Task) {

        val intent = Intent(this, TaskAddActivity::class.java)

        intent.putExtra("task", task)

        startActivity(intent)

    }

    private fun confirmDelete(id: Int) {


        val alertBuilder = AlertDialog.Builder(this)

        alertBuilder.setTitle("Confirmar exclusão")
        alertBuilder.setMessage("Deseja realmente excluir a tarefa?")
        alertBuilder.setPositiveButton("Sim") { _, _ ->

            val taskDAO = TaskDAO(this)
            if (taskDAO.delete(id)) {
                updateTaskList()
                Toast.makeText(
                    this,
                    "Sucesso ao remover tarefa",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(
                    this,
                    "Erro ao remover tarefa",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        alertBuilder.setNegativeButton("Não") { _, _ -> }
        alertBuilder.create().show()
    }

    private fun updateTaskList() {
        val taskDAO = TaskDAO(this)
        taskList = taskDAO.list()
        taskAdapter?.listAdd(taskList)
    }

    override fun onStart() {
        super.onStart()

        updateTaskList()

    }
}