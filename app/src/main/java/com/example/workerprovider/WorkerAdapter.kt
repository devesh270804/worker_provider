package com.example.workerprovider

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class WorkerAdapter(private val workers : List<OccupationWorkerProfile>)
    : RecyclerView.Adapter<WorkerAdapter.WorkerViewHolder>(){

    class WorkerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.workerName2)
        val phoneTextView: TextView = itemView.findViewById(R.id.workerPhoneNumber2)
        val wageTextView:TextView = itemView.findViewById(R.id.workerWage2)
        val experienceTextView:TextView = itemView.findViewById(R.id.workerExperience2)
        val addressTextView:TextView  = itemView.findViewById(R.id.workerAddress2)
        val distanceTextView:TextView = itemView.findViewById(R.id.workerLocationDistance)
    }

    override fun getItemCount(): Int {
        return workers.size
    }

    override fun onBindViewHolder(holder:WorkerViewHolder,position:Int){
        val worker = workers[position]

        val n1 = "Name : ${worker.name}"
        val n2 = "Number : ${worker.phoneNumber}"
        val n3 = "Wages : Rs  ${worker.dailyWages} "
        val n4 = "Experience : ${worker.experience} years"
        val n5 = "Address :  ${worker.address}"
        val n7 = worker.distance /1000
        val n8 = String.format("%.2f",n7)
        val n6 = "Distance : $n8 km "

        holder.nameTextView.text = n1
        holder.phoneTextView.text = n2
        holder.wageTextView.text = n3
        holder.experienceTextView.text = n4
        holder.addressTextView.text = n5
        holder.distanceTextView.text = n6
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.worker_occupation_card , parent,false)
        return WorkerViewHolder(view)
    }


}