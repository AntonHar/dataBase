package com.example.database

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class MainActivity : AppCompatActivity() {
    private lateinit var edName: EditText
    private lateinit var edSecName: EditText
    private lateinit var edEmail: EditText
    private lateinit var mDataBase: DatabaseReference
    private val USER_KEY = "user"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    private fun init() {
        edName = findViewById(R.id.edName)
        edSecName = findViewById(R.id.edSecName)
        edEmail = findViewById(R.id.edEmail)
        mDataBase = FirebaseDatabase.getInstance().getReference(USER_KEY)
    }

    fun onClickSave(view: View?) {
        val id = mDataBase.key
        val name = edName.text.toString()
        val sec_name = edSecName.text.toString()
        val email = edEmail.text.toString()
        val newUser = User(id, name, sec_name, email)
        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(sec_name) && !TextUtils.isEmpty(email)) {
            mDataBase.push().setValue(newUser)
            Toast.makeText(this, "Сохранено", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Пустое поле", Toast.LENGTH_SHORT).show()
        }
    }

    fun onClickRead(view: View?) {
        val i = Intent(this@MainActivity, ReadActivity::class.java)
        startActivity(i)
    }
}