package com.abi.excelreader

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.widget.Toolbar

class HomeActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val toolbar = findViewById<Toolbar>(R.id.toolbar_home)
        setUpToolBar(toolbar)

        var buttonRead = findViewById<Button>(R.id.read_data)
        var buttonWrite = findViewById<Button>(R.id.write_data)

        buttonRead.setOnClickListener(this)
        buttonWrite.setOnClickListener(this)
    }

    private fun setUpToolBar(toolbar: Toolbar?) {
        setSupportActionBar(toolbar)
        toolbar?.title = "Home"
    }

    override fun onClick(view: View?) {
        if (view != null) {
            when(view.id){
                R.id.read_data -> { startActivity(Intent(this, MainActivity::class.java))}
                R.id.write_data -> {Toast.makeText(this, "Not available Now!!", Toast.LENGTH_SHORT).show()}
            }
        }

    }
}
