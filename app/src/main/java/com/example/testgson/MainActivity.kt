package com.example.testgson

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)




        recyclerView_main.layoutManager = LinearLayoutManager(this) as RecyclerView.LayoutManager?
        recyclerView_main.adapter = MainAdapter()
    }
}
