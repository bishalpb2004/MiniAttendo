package com.example.testapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.testapp.databinding.FragmentEditBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class EditFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentEditBinding
    private val subjects= mutableListOf<Subject>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentEditBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val subjectName=arguments?.getString("subjectName")
        val attendClass=arguments?.getFloat("attendClass")
        val totalClass=arguments?.getFloat("totalClass")
        val position=arguments?.getInt("position")

        binding.etEditSubjectName.setText(subjectName)
        binding.etEditAtClass.setText(attendClass.toString())
        binding.etEditTotalClass.setText(totalClass.toString())

        binding.updateBtn.setOnClickListener {
            val editSubjectName=binding.etEditSubjectName.text.toString()
            val editAttendClass=binding.etEditAtClass.text.toString()
            val editTotalClass=binding.etEditTotalClass.text.toString()

            if(editSubjectName.isNotEmpty() and editAttendClass.isNotEmpty() and editTotalClass.isNotEmpty()){
                val result=Bundle().apply {
                    putString("editSubjectName",editSubjectName)
                    putString("editAttendClass",editAttendClass)
                    putString("editTotalClass",editTotalClass)
                    putInt("editPosition",position!!)
                }
                parentFragmentManager.setFragmentResult("rKey",result)
                dismiss()
            }
            else{
                Toast.makeText(context,"Enter all the details!",Toast.LENGTH_LONG).show()
            }

        }
    }
}