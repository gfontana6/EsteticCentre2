package it.fontanaprojects.esteticcentre2

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.homepage_user_layout.*

class Homepage_User: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.homepage_user_layout)

        supportActionBar?.hide()

        val admin = intent.getStringExtra("name")
        nameAdmin.text = "Salve " + admin





        logoutText.setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java))
        }

    }


    override fun onBackPressed() {
        startActivity(Intent(this, MainActivity::class.java))
    }

}