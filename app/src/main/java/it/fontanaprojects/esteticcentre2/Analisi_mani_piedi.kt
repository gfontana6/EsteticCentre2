package it.fontanaprojects.esteticcentre2

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.ViewGroup
import android.view.Window
import kotlinx.android.synthetic.main.mani_piedi_analisi_layout.*

class Analisi_mani_piedi(context: Context, id_cliente: String) : Dialog(context) {

    private val id_cliente = id_cliente


    init {
        setCancelable(true)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.mani_piedi_analisi_layout)

        this.window!!.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )

        val db = Db_query()

        val dataPersonal = db.readPersonalData(id_cliente)
        name_cognometext.text = dataPersonal!![0]
        data_nascita.text = dataPersonal[1]
        address.text = dataPersonal[2]
        tel.text = dataPersonal[3]
        mail.text = dataPersonal[4]
        professione.text = dataPersonal[5]


    }
}