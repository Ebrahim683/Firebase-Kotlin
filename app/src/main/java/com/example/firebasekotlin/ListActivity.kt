package com.example.firebasekotlin

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_list.*

class ListActivity : AppCompatActivity() {

    lateinit var arrayList: ArrayList<UserModel>
    lateinit var recyclerViewAdapter: RecyclerViewAdapter
    lateinit var firebaseDatabase: FirebaseDatabase
    lateinit var imageUri:Uri
    lateinit var userModel: UserModel
    lateinit var storageReference: StorageReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        supportActionBar?.hide()


        arrayList = ArrayList()
        firebaseDatabase = FirebaseDatabase.getInstance()
        storageReference = FirebaseStorage.getInstance().getReference("images/")

        recyclerViewAdapter = RecyclerViewAdapter(this, data())
        recId.apply {
            layoutManager = LinearLayoutManager(this@ListActivity)
            setHasFixedSize(true)
            adapter = recyclerViewAdapter
        }
        userModel = UserModel()
        textID.text = userModel.name.toString()

        profileImage.setOnClickListener {
            selectImage()
        }

        saveImage.setOnClickListener {
            uploadImage()
        }

    }

    private fun uploadImage() {
        val progressDialog: ProgressDialog = ProgressDialog(this)
            .apply {
                setTitle("Uploading...")
                setCancelable(false)
            }
        progressDialog.show()

        val uploadStorageReference:StorageReference = storageReference.child("images/")
        storageReference.putFile(imageUri).addOnSuccessListener {
            userModel.image?.let { it1 -> profileImage.setImageResource(it1) }
            Toast.makeText(this,"Uploaded",Toast.LENGTH_SHORT).show()
            if (progressDialog.isShowing){
                progressDialog.dismiss()
            }
        }.addOnFailureListener {
            Toast.makeText(this,it.message.toString(),Toast.LENGTH_SHORT).show()
            if (progressDialog.isShowing){
                progressDialog.dismiss()
            }
        }
    }

    fun selectImage(){
        var intent = Intent().apply {
            action = Intent.ACTION_GET_CONTENT
            type = "image/*"
        }

        startActivityForResult(intent,100)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 100 && resultCode == RESULT_OK){
            imageUri = data?.data!!
            profileImage.setImageURI(imageUri)
        }
    }


    fun data(): ArrayList<UserModel> {
        arrayList = ArrayList()

        firebaseDatabase.reference.child("User Data")
            .addValueEventListener(object : ValueEventListener {
                @SuppressLint("NotifyDataSetChanged")
                override fun onDataChange(snapshot: DataSnapshot) {
                    arrayList.clear()
                    if (snapshot.exists()) {
                        for (dataSnapshot in snapshot.children) {
                            val userModel = dataSnapshot.getValue(UserModel::class.java)
                            arrayList.add(userModel!!)
                        }
                    }
                    recyclerViewAdapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(
                        this@ListActivity,
                        "Error : ${error.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
        return arrayList
    }

}