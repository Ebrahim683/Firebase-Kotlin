package com.example.firebasekotlin


import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_home_page.*

class HomePage : AppCompatActivity() {

    lateinit var mAuth: FirebaseAuth
    lateinit var firebaseDatabase: FirebaseDatabase
    lateinit var databaseReference: DatabaseReference
    lateinit var name: String
    lateinit var number: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        mAuth = FirebaseAuth.getInstance()
        firebaseDatabase = FirebaseDatabase.getInstance()

        saveButton.setOnClickListener {
            addData()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        var menuInflater: MenuInflater = menuInflater
        var menuItem = menuInflater.inflate(R.menu.menu_homepage, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.logOutMenuId -> {
                mAuth.signOut()
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun addData() {

        var animation = AnimationUtils.loadAnimation(this, R.anim.button_animation)
        saveButton.startAnimation(animation)

        name = nameID.text.toString()
        number = numberId.text.toString()

        var progressDialog: ProgressDialog = ProgressDialog(this)
            .apply {
                setTitle("Creating Account...")
                setCancelable(false)
            }
        progressDialog.show()

        if (name.isEmpty() and number.isEmpty()) {
            Toast.makeText(this, "Fill Your Form First", Toast.LENGTH_SHORT).show()
            progressDialog.dismiss()
        } else {

            val userModel = UserModel(name, number)
            databaseReference =
                firebaseDatabase.getReference("User Data").child(mAuth.currentUser!!.uid)
            databaseReference.apply {
                setValue(userModel)
                progressDialog.dismiss()
            }
            Snackbar.make(this, homePageID, "Data Added", Snackbar.LENGTH_SHORT).show()
            nameID.text = null
            numberId.text = null

        }
    }


}