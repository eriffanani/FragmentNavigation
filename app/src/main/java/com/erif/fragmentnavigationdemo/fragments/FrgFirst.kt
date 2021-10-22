package com.erif.fragmentnavigationdemo.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation
import com.erif.fragmentnavigationdemo.R

class FrgFirst : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.frg_first, container, false)
        val btnOpen: Button = view.findViewById(R.id.frg_first_btnOpen)
        btnOpen.setOnClickListener {
            Navigation.findNavController(btnOpen).navigate(R.id.nav_action_frg1_to_frg2)
        }
        return view
    }

}