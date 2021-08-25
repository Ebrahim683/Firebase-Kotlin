package com.example.firebasekotlin

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.signInText

class LoginActivity : AppCompatActivity() {

    lateinit var email:String
    lateinit var password:String
    lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        mAuth = FirebaseAuth.getInstance()

        if (mAuth.currentUser != null){
            var intent: Intent = Intent(this, HomePage::class.java)
            startActivity(intent)
            finish()
        }

        signUpText.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }

        signInButton.setOnClickListener {
            signIn()
        }

    }

    private fun signIn() {
        email = emailLogin.text.toString().trim()
        password = passwordLogin.text.toString().trim()

        var animation = AnimationUtils.loadAnimation(this, R.anim.button_animation)
        signInButton.startAnimation(animation)

        var progressDialog: ProgressDialog = ProgressDialog(this)
            .apply {
                setTitle("Login In...")
                setCancelable(false)
            }
        progressDialog.show()

        if (email.isEmpty() and password.isEmpty()){
            Toast.makeText(this,"Fill Your Form First",Toast.LENGTH_SHORT).show()
            progressDialog.dismiss()
        }else{
            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this) { task ->
                if (task.isSuccessful){
                    progressDialog.dismiss()
                    var intent: Intent = Intent(this, HomePage::class.java)
                    startActivity(intent)
                    finish()
                }else{
                    Toast.makeText(this,"Error ${task.exception}", Toast.LENGTH_SHORT).show()
                    progressDialog.dismiss()
                }
            }
        }

    }
}