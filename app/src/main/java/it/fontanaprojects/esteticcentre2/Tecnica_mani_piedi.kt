package it.fontanaprojects.esteticcentre2

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.ViewGroup
import android.view.Window
import java.util.*

class Tecnica_mani_piedi(context: Context, id_client: String) : Dialog(context) {

    private val id_client = id_client


    init {
        setCancelable(true)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.mani_piedi_tecnica)

        this.window!!.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )

        val db = Db_query()

    }
}