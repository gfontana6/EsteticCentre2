package it.fontanaprojects.esteticcentre2

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.marginTop
import kotlinx.android.synthetic.main.homepage_user_layout.*
import kotlinx.android.synthetic.main.mani_piedi_tecnica.*
import kotlinx.android.synthetic.main.mani_piedi_tecnica.view.*
import java.util.*

class Tecnica_mani_piedi : AppCompatActivity() {

    private val myCalendar: Calendar = Calendar.getInstance()
    private val day :Int = myCalendar.get(Calendar.DAY_OF_MONTH)
    private val month:Int = myCalendar.get(Calendar.MONTH)
    private val year :Int = myCalendar.get(Calendar.YEAR)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.mani_piedi_tecnica)

        var editText = EditText(this)

        val id_cliente : String? = intent.getStringExtra("id_client")
        val id_ultimo_trattamento: Int = intent.getIntExtra("id_ultimo_trattamento", 1)

        this.window!!.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )

        val db = Db_query()


        Thread(Runnable {
            dataTecnica.setOnClickListener {
                TimerPicker(dataTecnica)
                DateDialog(dataTecnica)
            }
        }).start()


        var i = 1

        Thread(Runnable {

            addSchedaTecnica.setOnClickListener{

                var linearLayoutHorizzontal = LinearLayout(this)
                linearLayoutHorizzontal.orientation = LinearLayout.HORIZONTAL
                linearLayoutHorizzontal.minimumWidth = ConstraintLayout.LayoutParams.MATCH_PARENT
                var textView = TextView(this)
                textView.text = "Data"
                textView.textSize = 25F
                linearLayoutHorizzontal.addView(textView)
                (textView.layoutParams as LinearLayout.LayoutParams).weight = 0.2F
                editText = EditText(this)
                editText.hint = "Name"
                editText.id = ("1$i").toInt()
                editText.isFocusable = false
                linearLayoutHorizzontal.addView(editText)
                (editText.layoutParams as LinearLayout.LayoutParams).weight = 1F
                linearSchedaTecnica.addView(linearLayoutHorizzontal)

                val idForListener = editText.id
                val listener = findViewById<EditText>(idForListener)
                listener.setOnClickListener{

                    TimerPicker(listener)
                    DateDialog(listener)

                }

                for(y in 2..9){

                    linearLayoutHorizzontal = LinearLayout(this)
                    linearLayoutHorizzontal.orientation = LinearLayout.HORIZONTAL
                    linearLayoutHorizzontal.minimumWidth = ConstraintLayout.LayoutParams.MATCH_PARENT
                    textView = TextView(this)

                    if(y == 2){
                        textView.text = "Trattamento"
                    }
                    else if(y == 3){
                        textView.text = "Mediatore"
                    }
                    else if(y == 4){
                        textView.text = "Base"
                    }
                    else if(y == 5){
                        textView.text = "Gel"
                    }
                    else if(y == 6){
                        textView.text = "Top"
                    }
                    else if(y == 7){
                        textView.text = "Colore"
                    }
                    else if(y == 8){
                        textView.text = "Deco"
                    }
                    else if(y == 9){
                        textView.text = "Altro"
                    }

                    textView.textSize = 25F
                    linearLayoutHorizzontal.addView(textView)
                    (textView.layoutParams as LinearLayout.LayoutParams).weight = 0.2F
                    editText = EditText(this)
                    editText.hint = "Name"
                    editText.id = ("$y$i").toInt()
                    linearLayoutHorizzontal.addView(editText)
                    (editText.layoutParams as LinearLayout.LayoutParams).weight = 1F
                    linearSchedaTecnica.addView(linearLayoutHorizzontal)
                }

                val view = View(this)
                view.minimumWidth = ConstraintLayout.LayoutParams.MATCH_PARENT
                view.minimumHeight = 5
                view.setBackgroundColor(ContextCompat.getColor(this, R.color.black))
                linearSchedaTecnica.addView(view)

                i++
                println(i)
            }
        }).start()


        Thread(Runnable{
            saveSchedeTecniche.setOnClickListener {

                var risultatoFinale = false

                var dataTecnica = dataTecnica.text.toString()
                var trattamentoTecnica = trattamentoTecnico.text.toString()
                var mediatoreTecnica = mediatoreTecnico.text.toString()
                var baseTecnica = baseTecnico.text.toString()
                var gelTecnica = gelTecnico.text.toString()
                var topTecnica = topTecnico.text.toString()
                var coloreTecnica = coloreTecnico.text.toString()
                var decoTecnica = decoTecnico.text.toString()
                var altroTecnica = altroTecnico.text.toString()

                var result = db.insertNewTecnica(id_ultimo_trattamento, id_cliente!!.toInt(), dataTecnica,
                    trattamentoTecnica, mediatoreTecnica, baseTecnica, gelTecnica, topTecnica, coloreTecnica,
                    decoTecnica, altroTecnica)

                risultatoFinale = result
                if(risultatoFinale == false){
                    Toast.makeText(this, "Errore nell'inserimento", Toast.LENGTH_SHORT).show()
                }
                else{
                    for(c in 1 until i){

                        var listener = findViewById<EditText>("1$c".toInt())
                        dataTecnica = listener.text.toString()

                        for(z in 2..9){

                            listener = findViewById<EditText>("$z$c".toInt())
                            if(z == 2){
                                trattamentoTecnica = listener.text.toString()
                            }
                            else if(z == 3){
                                mediatoreTecnica = listener.text.toString()
                            }
                            else if(z == 4){
                                baseTecnica = listener.text.toString()
                            }
                            else if(z == 5){
                                gelTecnica = listener.text.toString()
                            }
                            else if (z == 6){
                                topTecnica = listener.text.toString()
                            }
                            else if(z == 7){
                                coloreTecnica = listener.text.toString()
                            }
                            else if(z == 8){
                                decoTecnica = listener.text.toString()
                            }
                            else if(z == 9){
                                altroTecnica = listener.text.toString()
                            }
                        }

                        //salvataggio
                        result = db.insertNewTecnica(id_ultimo_trattamento, id_cliente.toInt(), dataTecnica,
                            trattamentoTecnica, mediatoreTecnica, baseTecnica, gelTecnica, topTecnica, coloreTecnica,
                            decoTecnica, altroTecnica)

                        risultatoFinale = result
                        if(risultatoFinale == false){
                            Toast.makeText(this, "Errore nell'inserimento", Toast.LENGTH_SHORT).show()
                            break
                        }
                        else{
                            if(c == i-1){
                                Toast.makeText(this, "Tutte le schede tecniche sono state inserite", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
            }
        }).start()
    }

    fun DateDialog(listener: EditText) {
        val listener =
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                listener.setText("$dayOfMonth/${monthOfYear + 1}/$year")
            }
        val dpDialog = DatePickerDialog(this, listener, year, month, day)
        dpDialog.show()
    }
    fun TimerPicker(listener: EditText) {
        val hour = myCalendar.get(Calendar.HOUR)
        val minute = myCalendar.get(Calendar.MINUTE)

        val tpd = TimePickerDialog(this,
            TimePickerDialog.OnTimeSetListener(function = { view, h, m ->

                listener.setText(listener.text.toString() + " : " + h.toString() + ":" + m.toString())

        }),hour,minute,false)

        tpd.show()
    }

}