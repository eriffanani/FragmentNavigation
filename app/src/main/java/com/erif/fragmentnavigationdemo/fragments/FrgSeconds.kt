package com.erif.fragmentnavigationdemo.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.erif.fragmentnavigationdemo.R

class FrgSeconds : Fragment() {

    private val viewModel: VMShared by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.frg_seconds, container, false)
        val btnBack: Button  = view.findViewById(R.id.frg_seconds_btnBack)
        btnBack.setOnClickListener {
            Navigation.findNavController(btnBack).popBackStack()
            viewModel.selectItem()
        }
        return view
    }

}