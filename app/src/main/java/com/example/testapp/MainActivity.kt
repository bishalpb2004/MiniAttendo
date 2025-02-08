package com.example.testapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.testapp.databinding.ActivityMainBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: SubjectAdapter
    private var subjects= mutableListOf<Subject>()
    private lateinit var floatingButton:FloatingActionButton

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        recyclerView=findViewById(R.id.recyclerView)
        floatingButton=findViewById(R.id.fab)

        adapter=SubjectAdapter(subjects)

        recyclerView.adapter=adapter
        recyclerView.layoutManager=LinearLayoutManager(this)

        supportFragmentManager.setFragmentResultListener("requestKey",this){_,bundle->
            val newSubjectName=bundle.getString("subjectName")
            val newSubject=Subject(subjectName = newSubjectName.toString())
            subjects.add(newSubject)
            adapter.notifyDataSetChanged()
        }

        supportFragmentManager.setFragmentResultListener("rKey",this){_,bundle->
            val editSubjectName=bundle.getString("editSubjectName")
            val editAttendClass=bundle.getString("editAttendClass")
            val editTotalClass=bundle.getString("editTotalClass")
            val position=bundle.getInt("editPosition")

            subjects[position].subjectName= editSubjectName.toString()
            subjects[position].attendClass=editAttendClass.toString().toFloat()
            subjects[position].totalClass=editTotalClass.toString().toFloat()

            adapter.notifyDataSetChanged()
        }

        val itemTouchHelper=ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position=viewHolder.adapterPosition
                val removedSubject=subjects[position]
                subjects.removeAt(position)
                this@MainActivity.adapter.notifyDataSetChanged()
                Snackbar.make(findViewById(R.id.recyclerView),"Deleted Successfully",Snackbar.LENGTH_LONG)
                    .setAction("Undo"){
                        subjects.add(position,removedSubject)
                        this@MainActivity.adapter.notifyDataSetChanged()
                    }.show()
            }

        })
        itemTouchHelper.attachToRecyclerView(recyclerView)

        floatingButton.setOnClickListener {
            NewSubjectFragment().show(supportFragmentManager,"TAG")
        }
    }
}