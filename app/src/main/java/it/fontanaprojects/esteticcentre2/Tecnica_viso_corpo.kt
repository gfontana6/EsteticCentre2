package it.fontanaprojects.esteticcentre2

import android.os.Bundle
import android.view.ViewGroup
import android.view.Window
import androidx.appcompat.app.AppCompatActivity

class Tecnica_viso_corpo  : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.viso_corpo_tecnica)

        val id_cliente : String? = intent.getStringExtra("id_client")
        val id_ultimo_trattamento: Int = intent.getIntExtra("id_ultimo_trattamento", 1)

        this.window!!.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )

        val db = Db_query()

        supportActionBar?.hide()

    }
}