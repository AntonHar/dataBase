package com.example.database

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class ShowActivity : AppCompatActivity() {
    private var tvName: TextView? = null
    private var tvSecName: TextView? = null
    private var tvEmail: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.show_layout)
        init()
        intentMain
    }

    private fun init() {
        tvName = findViewById(R.id.tvName)
        tvSecName = findViewById(R.id.tvSecName)
        tvEmail = findViewById(R.id.tvEmail)
    }

    private val intentMain: Unit
        private get() {
            val i = intent
            if (i != null) {
                tvName!!.text = i.getStringExtra(Constant.USER_NAME)
                tvSecName!!.text = i.getStringExtra(Constant.USER_SEC_NAME)
                tvEmail!!.text = i.getStringExtra(Constant.USER_EMAIL)
            }
        }
}