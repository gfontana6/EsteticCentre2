package it.fontanaprojects.esteticcentre2

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.ViewGroup
import android.view.Window
import kotlinx.android.synthetic.main.chose_scheda_analisi_tecnica_ed_ricerca.*

class choseScheda(private val choose: String, context: Context, id_client: String?) : Dialog(context){

    private val id_client = id_client

    init {
        setCancelable(true)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.chose_scheda_analisi_tecnica_ed_ricerca)

        this.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)


        if(choose == "viso_corpo"){
            elenco_trattamenti_text.text = "ELENCO TRATTAMENTI VISO/CORPO"
            btn_add_analisi.setOnClickListener {
                val Analisi_viso_corpo = Analisi_viso_corpo(context, id_client!!)
                Analisi_viso_corpo.show()

            }
            btn_add_tecnica.setOnClickListener {
                val Tecnica_viso_corpo = Tecnica_viso_corpo(context, id_client!!)
                Tecnica_viso_corpo.show()

            }
        }
        else if(choose == "mani_piedi"){
            elenco_trattamenti_text.text = "ELENCO TRATTAMENTI MANI/PIEDI"
            btn_add_analisi.setOnClickListener {
                val Analisi_mani_piedi= Analisi_mani_piedi(context, id_client!!)
                Analisi_mani_piedi.show()

            }
            btn_add_tecnica.setOnClickListener {
                val Tecnica_mani_piedi = Tecnica_mani_piedi(context, id_client!!)
                Tecnica_mani_piedi.show()

            }
        }
    }
}