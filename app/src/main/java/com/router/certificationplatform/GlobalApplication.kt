package com.router.certificationplatform

import android.app.Application
import com.google.firebase.auth.FirebaseUser

class GlobalApplication : Application() {

    companion object{
        lateinit var user : FirebaseUser
    }

}