package com.example.firebasekotlin

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_list.*

class ListActivity : AppCompatActivity() {

    lateinit var arrayList: ArrayList<UserModel>
    lateinit var recyclerViewAdapter: RecyclerViewAdapter
    lateinit var firebaseDatabase: FirebaseDatabase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        supportActionBar?.hide()


        arrayList = ArrayList()
        firebaseDatabase = FirebaseDatabase.getInstance()

        recyclerViewAdapter = RecyclerViewAdapter(this, data())
        recId.apply {
            layoutManager = LinearLayoutManager(this@ListActivity)
            setHasFixedSize(true)
            adapter = recyclerViewAdapter
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
//                            userModel?.id = snapshot.key!!
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