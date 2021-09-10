package com.router.certificationplatform

import android.app.Application
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference

class GlobalApplication : Application() {


    companion object{
        lateinit var user : FirebaseUser
    }

}