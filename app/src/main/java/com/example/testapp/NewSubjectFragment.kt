package com.example.testapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.testapp.databinding.FragmentNewSubjectBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class NewSubjectFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentNewSubjectBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentNewSubjectBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnAddSubject.setOnClickListener{
            val etSubjectName=binding.subjectName.text.toString().trim()
            if(etSubjectName.isNotEmpty()){
                val result=Bundle().apply {
                    putString("subjectName",etSubjectName)
                }
                parentFragmentManager.setFragmentResult("requestKey",result)
                dismiss()
            }
            else{
                Toast.makeText(context,"Please Enter Subject Name",Toast.LENGTH_LONG).show()
            }
        }
    }
}