package com.example.firebasekotlin

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

lateinit var mAuth: FirebaseAuth
lateinit var email: String
lateinit var password: String

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        mAuth = FirebaseAuth.getInstance()

        signUpButton.setOnClickListener {
            signUp()
        }

        signInText.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

    }

    private fun signUp() {
        var animation = AnimationUtils.loadAnimation(this, R.anim.button_animation)
        signUpButton.startAnimation(animation)
        email = emailID.text.toString().trim()
        password = passwordID.text.toString().trim()

        var progressDialog: ProgressDialog = ProgressDialog(this)
            .apply {
                setTitle("Creating Account...")
                setCancelable(false)
            }
        progressDialog.show()

        if (email.isEmpty() and password.isEmpty()){
            Toast.makeText(this,"Fill Your Form First",Toast.LENGTH_SHORT).show()
            progressDialog.dismiss()
        }else{
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    progressDialog.dismiss()
                    var intent: Intent = Intent(this, HomePage::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "Error ${task.exception}", Toast.LENGTH_SHORT).show()
                    progressDialog.dismiss()
                }
            }
        }
    }
}