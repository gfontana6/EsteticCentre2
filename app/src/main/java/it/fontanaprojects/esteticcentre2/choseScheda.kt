package it.fontanaprojects.esteticcentre2

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.setPadding
import kotlinx.android.synthetic.main.chose_scheda_analisi_tecnica_ed_ricerca.*
import kotlinx.android.synthetic.main.homepage_user_layout.*

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

                /*
                Thread(Runnable {

                    //recupero dei trattamenti fatti

                }).start()

                 */
            }
            else if(choose == "mani_piedi"){
                elenco_trattamenti_text.text = "ELENCO TRATTAMENTI MANI/PIEDI"
                Thread(Runnable{
                    btn_add_analisi.setOnClickListener {
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

                Thread(Runnable {

                    //recupero dei trattamenti fatti
                   // showAllAnalisi(choose)

                }).start()
            }
    }

   /* fun showAllAnalisi(type: String){
        val namesClient = db.searchTrattament(false, null, id_client, type)

        if(namesClient != null){
            var i = 0
            while(i < namesClient.size){
                val textView = TextView(context)
                val namesClientSplitted = namesClient.get(i).split(";")
                textView.text = namesClientSplitted[1]
                textView.setPadding(3)
                textView.setTextSize(15F)
                textView.background = ResourcesCompat.getDrawable(context.resources, R.drawable.border, null)
                textView.setTypeface(null, Typeface.BOLD_ITALIC)
                textView.isClickable = true

                /*
                textView.setOnClickListener{

                    val nameClientResultSplitted = textView.text.toString().split(" - ")

                    val intent = Intent(this, ManageClient::class.java)
                    intent.putExtra("nameClientIntent", nameClientResultSplitted[1])
                    intent.putExtra("id_cliente", namesClientSplitted[0])
                    intent.putExtra("id_user", id_user)
                    intent.putExtra("name", admin)
                    startActivity(intent)

                }
                */

                textView.setTextColor(ContextCompat.getColor(context, R.color.blue_link))
                TableClients.addView(textView)
                i++
            }
        }
        else {
            TableClients.removeAllViews()
            val textView = TextView(context)
            textView.text = "Nessun trattamento trovato"
            textView.setPadding(3)
            textView.textSize = 15F
            textView.background = ResourcesCompat.getDrawable(context.resources, R.drawable.border, null)
            textView.setTypeface(null, Typeface.BOLD_ITALIC)
            textView.setTextColor(ContextCompat.getColor(context, R.color.black))
            TableClients.addView(textView)
        }
    }
*/
}