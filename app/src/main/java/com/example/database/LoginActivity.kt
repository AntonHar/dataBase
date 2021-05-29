package com.example.database

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


class LoginActivity : AppCompatActivity() {
    private lateinit var edLogin: EditText
    private lateinit var edPassword: EditText
    private lateinit var mAuth: FirebaseAuth

    private lateinit var bStart: Button
    private lateinit var bSingUp: Button
    private lateinit var bSingIn: Button
    private lateinit var tvStart: TextView
    private lateinit var bSingOut: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_layout)
        init()
    }

    override fun onStart() {
        super.onStart()
        val cUser: FirebaseUser? = mAuth.currentUser
        if (cUser != null) {
            showSigned()
            val userName: String = "Вы вошли как: " + cUser.email
            tvStart.text = userName


            Toast.makeText(this, "User not null", Toast.LENGTH_SHORT).show()
        } else {
            notSigned()

            Toast.makeText(this, "User null", Toast.LENGTH_SHORT).show()
        }
    }

    private fun init() {
        edLogin = findViewById(R.id.edLogin)
        edPassword = findViewById(R.id.edPassword)
        mAuth = FirebaseAuth.getInstance()
        tvStart = findViewById(R.id.tvUserEmail)
        bStart = findViewById(R.id.bStart)
        bSingUp = findViewById(R.id.bSingUp)
        bSingIn = findViewById(R.id.bSingIn)
        bSingOut = findViewById(R.id.bSingOut)
    }

    fun onClickSignUp(view: View?) {
        if (!TextUtils.isEmpty(edLogin.text.toString()) && !TextUtils.isEmpty(edPassword!!.text.toString())) {
            mAuth.createUserWithEmailAndPassword(
                edLogin.text.toString(),
                edPassword.text.toString()
            ).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    showSigned()
                    sedEmailVer()
                    Toast.makeText(
                        applicationContext,
                        "User SignUp Successful",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    notSigned()
                    Toast.makeText(applicationContext, "User SignUp failed", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        } else {
            Toast.makeText(
                applicationContext,
                "Please entre Email and Password",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    fun onClickSignIn(view: View?) {
        if (!TextUtils.isEmpty(edLogin.text.toString()) && !TextUtils.isEmpty(edPassword.text.toString())) {
            mAuth.signInWithEmailAndPassword(
                edLogin.text.toString(),
                edPassword.text.toString()
            ).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    showSigned()
                    Toast.makeText(
                        applicationContext,
                        "User SignIn Successful",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    notSigned()
                    Toast.makeText(applicationContext, "User SignIn failed", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    fun onClickStart(view: View) {
        val i = Intent(this@LoginActivity, MainActivity::class.java)
        startActivity(i)
    }

    fun onClickSingOut(view: View) {
        FirebaseAuth.getInstance().signOut()
        notSigned()
    }

    private fun showSigned() {
        val user: FirebaseUser? = mAuth.currentUser
        if (user?.isEmailVerified == true) {
            val userName: String = "Вы вошли как: " + user.email
            tvStart.text = userName
            bStart.visibility = View.VISIBLE
            bSingOut.visibility = View.VISIBLE
            tvStart.visibility = View.VISIBLE
            edLogin.visibility = View.GONE
            edPassword.visibility = View.GONE
            bSingIn.visibility = View.GONE
            bSingUp.visibility = View.GONE
        } else{
            Toast.makeText(
                applicationContext,
                "Проверьте вашу почту для подтверждения вашего Эмайл адресса ",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun notSigned() {
        bStart.visibility = View.GONE
        bSingOut.visibility = View.GONE
        tvStart.visibility = View.GONE
        edLogin.visibility = View.VISIBLE
        edPassword.visibility = View.VISIBLE
        bSingIn.visibility = View.VISIBLE
        bSingUp.visibility = View.VISIBLE
    }

    private fun sedEmailVer() {
        mAuth.currentUser?.sendEmailVerification()
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    showSigned()
                    Toast.makeText(
                        applicationContext,
                        "Проверьте вашу почту для подтверждения вашего Эмайл адресса ",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    notSigned()
                    Toast.makeText(applicationContext, "Send Email Failed", Toast.LENGTH_SHORT)
                        .show()
                }
            }
    }
}