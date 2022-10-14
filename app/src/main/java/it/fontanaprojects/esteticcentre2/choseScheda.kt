package it.fontanaprojects.esteticcentre2

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.setPadding
import kotlinx.android.synthetic.main.chose_scheda_analisi_tecnica_ed_ricerca.*

class choseScheda(private val choose: String, context: Context, id_client: String?) : Dialog(context){

    private val id_client = id_client
    private val db: Db_query = Db_query()

    init {
        setCancelable(true)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.chose_scheda_analisi_tecnica_ed_ricerca)

        this.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)

            var id_ultimo_trattamento: Int? = db.takeLastIdTrattment(id_client!!.toInt())

            if(id_ultimo_trattamento == null){
                Toast.makeText(context, "Non ci sono trattamenti per questo cliente", Toast.LENGTH_SHORT).show()
                id_ultimo_trattamento  = 1
            }

            if(choose == "viso_corpo"){
                elenco_trattamenti_text.text = "ELENCO TRATTAMENTI VISO/CORPO"
                Thread(Runnable{
                    btn_add_analisi.setOnClickListener {
                        val intent = Intent(context, Analisi_viso_corpo::class.java)
                        intent.putExtra("id_client", id_client)
                        intent.putExtra("id_ultimo_trattamento", id_ultimo_trattamento)
                        context.startActivity(intent)
                    }
                    btn_add_tecnica.setOnClickListener {
                        val intent = Intent(context, Tecnica_viso_corpo::class.java)
                        intent.putExtra("id_client", id_client)
                        intent.putExtra("id_ultimo_trattamento", id_ultimo_trattamento)
                        context.startActivity(intent)
                    }
                }).start()

                //recupero dei trattamenti fatti

            }
            else if(choose == "mani_piedi"){
                elenco_trattamenti_text.text = "ELENCO TRATTAMENTI MANI/PIEDI"
                Thread(Runnable{
                    btn_add_analisi.setOnClickListener {
                        btn_add_tecnica.isEnabled = true
                        val intent = Intent(context, Analisi_mani_piedi::class.java)
                        intent.putExtra("id_client", id_client)
                        intent.putExtra("id_ultimo_trattamento", id_ultimo_trattamento)
                        context.startActivity(intent)
                    }
                    btn_add_tecnica.setOnClickListener {
                        val intent = Intent(context, Tecnica_mani_piedi::class.java)
                        intent.putExtra("id_client", id_client)
                        intent.putExtra("id_ultimo_trattamento", id_ultimo_trattamento)
                        context.startActivity(intent)
                    }
                }).start()


                showAllAnalisi(choose)

                Toast.makeText(context, "Per poter aggiungere le schede tecniche associate, bisogna prima " +
                        "compilare la scheda analisi informativa e visiva", Toast.LENGTH_LONG).show()

            }
    }

    fun showAllAnalisi(type: String){
        val namesClient = db.searchTrattament(null, true, id_client, type)

        if(namesClient != null){
            var i = 0
            while(i < namesClient.size){
                val textView = TextView(context)
                val TrattamentsSplitted = namesClient.get(i).split(";")
                textView.text = TrattamentsSplitted[1] + ":\n" + TrattamentsSplitted[2]
                textView.textAlignment = View.TEXT_ALIGNMENT_CENTER
                textView.gravity = View.TEXT_ALIGNMENT_CENTER
                textView.setPadding(3)
                textView.setTextSize(15F)
                textView.background = ResourcesCompat.getDrawable(context.resources, R.drawable.border, null)
                textView.setTypeface(null, Typeface.BOLD_ITALIC)
                textView.isClickable = true

                textView.setOnClickListener{

                    val showTrattaments = ShowTrattaments(TrattamentsSplitted[0], context)
                    showTrattaments.show()

                }

                textView.setTextColor(ContextCompat.getColor(context, R.color.blue_link))
                TableTrattament.addView(textView)
                i++
            }
        }
        else {
            TableTrattament.removeAllViews()
            val textView = TextView(context)
            textView.text = "Nessun trattamento trovato"
            textView.textAlignment = View.TEXT_ALIGNMENT_CENTER
            textView.gravity = View.TEXT_ALIGNMENT_CENTER
            textView.setPadding(3)
            textView.textSize = 15F
            textView.background = ResourcesCompat.getDrawable(context.resources, R.drawable.border, null)
            textView.setTypeface(null, Typeface.BOLD_ITALIC)
            textView.setTextColor(ContextCompat.getColor(context, R.color.black))
            TableTrattament.addView(textView)
        }
    }

}