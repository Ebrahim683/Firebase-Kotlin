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
    private lateinit var databaseReference: DatabaseReference
    lateinit var name: String
    lateinit var number: String
    lateinit var id: String
    lateinit var emailAddress: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        mAuth = FirebaseAuth.getInstance()
        firebaseDatabase = FirebaseDatabase.getInstance()

        emailTextID.text = mAuth.currentUser!!.email.toString()

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
            R.id.listIdMenu -> startActivity(Intent(this, ListActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }

    fun addData() {

        var animation = AnimationUtils.loadAnimation(this, R.anim.button_animation)
        saveButton.startAnimation(animation)

        id = mAuth.currentUser!!.uid
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
            val userModel = UserModel(id, name, number, emailAddress)
            databaseReference =
                firebaseDatabase.reference.child("User Data").child(id)
            databaseReference.apply {
                setValue(userModel)
                progressDialog.dismiss()
            }
            Snackbar.make(this, homePageID, "Data Added", Snackbar.LENGTH_SHORT).show()
            nameID.setText("")
            numberId.setText("")
        }
    }


}