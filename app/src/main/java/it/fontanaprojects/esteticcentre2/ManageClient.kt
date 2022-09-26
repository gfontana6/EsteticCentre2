package it.fontanaprojects.esteticcentre2

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.manage_client_layout.*

class ManageClient : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.manage_client_layout)

        supportActionBar?.hide()

        val admin = intent.getStringExtra("name")
        val id_client = intent.getStringExtra("id_cliente")
        val id_user = intent.getStringExtra("id_user")
        val nameClient = intent.getStringExtra("nameClientIntent")

        val db = Db_query()

        nameClientManage.text = "Gestisci: $nameClient"

        addPerformance.setOnClickListener{
            val chooseTrattament = ChoosePerformance(this, id_client)
            chooseTrattament.show()

        }

        indietro.setTypeface(null, Typeface.BOLD_ITALIC)
        indietro.setOnClickListener {

            val intent = Intent(this, Homepage_User::class.java)
            intent.putExtra("name", admin)
            intent.putExtra("id_user", id_user)
            startActivity(intent)

        }

    }
}