package com.example.firebasekotlin

import android.media.Image
import kotlin.properties.Delegates

open class UserModel(var id:String? = null, val name: String? = null, val number: String? = null) {

    var email:String? = ""
    var image:Int? = null

    constructor(id: String, name: String, number: String, email: String) : this(
        id,
        name,
        number
    ) {
        this.email = email
    }

    constructor(id: String, name: String, number: String, email: String, image: Int) : this(
        id,
        name,
        number
    ) {
        this.image = image
        this.email = email
    }

}