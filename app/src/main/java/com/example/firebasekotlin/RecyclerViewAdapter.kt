package com.example.firebasekotlin

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class RecyclerViewAdapter(var context: Context, var arrayList: ArrayList<UserModel>) :
    RecyclerView.Adapter<RecyclerViewAdapter.RcViewHolder>() {


    class RcViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var sampleProfileImage: CircleImageView = itemView.findViewById(R.id.sampleProfileImage)
        var sampleName: TextView = itemView.findViewById(R.id.sampleName)
        var sampleNumber: TextView = itemView.findViewById(R.id.sampleNumber)
        var sampleEmail: TextView = itemView.findViewById(R.id.sampleEmail)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RcViewHolder {
        var view = LayoutInflater.from(context).inflate(R.layout.rec_single_row, parent, false)
        return RcViewHolder(view)
    }

    override fun onBindViewHolder(holder: RcViewHolder, position: Int) {
        var userModel = arrayList[position]
        holder.sampleName.text = userModel.name
        holder.sampleNumber.text = userModel.number
        holder.sampleEmail.text = userModel.email
//        Picasso.get().load(userModel.image!!).into(holder.sampleProfileImage)
//        holder.sampleProfileImage.setImageResource(userModel.image!!)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

}