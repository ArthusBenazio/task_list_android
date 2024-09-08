package com.arthusbenazio.apptaskslist.adapter

import android.content.DialogInterface.OnClickListener
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arthusbenazio.apptaskslist.databinding.ItemTaskBinding
import com.arthusbenazio.apptaskslist.model.Task

class TaskAdapter(
    val onClickDelete: (Int) -> Unit,
    val onClickEdit: (Task) -> Unit
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    private var taskList: List<Task> = emptyList()

    fun listAdd(list: List<Task>) {
        this.taskList = list
        notifyDataSetChanged()
    }

    inner class TaskViewHolder(itemBinding: ItemTaskBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        private val binding: ItemTaskBinding

        init {
            binding = itemBinding
        }

        fun bind(task: Task) {
            binding.textDescricao.text = task.description
            binding.textData.text = task.dateRegister

            binding.btnExcluir.setOnClickListener{
                onClickDelete(task.idTask)
            }

            binding.btnEditar.setOnClickListener {
                onClickEdit(task)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val itemTarefaBinding = ItemTaskBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return TaskViewHolder(itemTarefaBinding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = taskList[position]
        holder.bind(task)
    }

    override fun getItemCount(): Int {
        return taskList.size
    }

}