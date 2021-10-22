package com.erif.fragmentnavigationdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.erif.fragmentnavigationdemo.fragments.VMShared

class MainActivity : AppCompatActivity() {

    private val viewModel: VMShared by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.selectedItem.observe(this, { clicked ->
            if (clicked) {
                Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show()
            }
        })

    }
}