package com.example.testapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text

class SubjectAdapter(private val subjects:MutableList<Subject>):RecyclerView.Adapter<SubjectAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val tvSubjectName=itemView.findViewById<TextView>(R.id.tvSubjectName)
        val tvAttendClass=itemView.findViewById<TextView>(R.id.tvAtClasses)
        val tvTotalClass=itemView.findViewById<TextView>(R.id.tvTotalClass)
        val tvPercentage=itemView.findViewById<TextView>(R.id.tvPercentage)
        val btnYes=itemView.findViewById<Button>(R.id.btnYes)
        val btnNo=itemView.findViewById<Button>(R.id.btnNo)
        val btnEdit=itemView.findViewById<Button>(R.id.btnEdit)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubjectAdapter.ViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.each_item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: SubjectAdapter.ViewHolder, position: Int) {
        val subject=subjects[position]
        holder.tvSubjectName.text=subject.subjectName
        updatePercentage(subject.attendClass,subject.totalClass,holder.tvAttendClass,holder.tvTotalClass,holder.tvPercentage)
        holder.btnYes.setOnClickListener {
            subject.attendClass++
            subject.totalClass++
            updatePercentage(subject.attendClass,subject.totalClass,holder.tvAttendClass,holder.tvTotalClass,holder.tvPercentage)
        }
        holder.btnNo.setOnClickListener {
            subject.totalClass++
            updatePercentage(subject.attendClass,subject.totalClass,holder.tvAttendClass,holder.tvTotalClass,holder.tvPercentage)
        }
        holder.btnEdit.setOnClickListener {
            val subjectName=subject.subjectName
            val attendClasses=subject.attendClass
            val totalClasses=subject.totalClass

            val result= Bundle().apply {
                putString("subjectName",subjectName)
                putFloat("attendClass",attendClasses)
                putFloat("totalClass",totalClasses)
                putInt("position",position)
            }
            val editFragment = EditFragment().apply {
                arguments = result
            }
            editFragment.show((holder.itemView.context as AppCompatActivity).supportFragmentManager,"EditFragment")
        }
    }

    private fun updatePercentage(attendClasses:Float,totalClasses:Float,tvAttendClass: TextView?, tvTotalClass: TextView?, tvPercentage: TextView?) {
        val percentage=if(totalClasses>0){
            (attendClasses * 1f)/totalClasses * 100
        }else{
            0f
        }
        tvAttendClass?.text="$attendClasses"
        tvTotalClass?.text="$totalClasses"
        tvPercentage?.text="$percentage"
    }

    override fun getItemCount(): Int {
        return subjects.size
    }

}