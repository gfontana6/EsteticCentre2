package it.fontanaprojects.esteticcentre2

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import kotlinx.android.synthetic.main.insert_new_client_layout.*
import java.util.*


class dialoginsertclient(context: Context, id_user: String) : Dialog(context) {

    private val myCalendar: Calendar = Calendar.getInstance()
    private val day :Int = myCalendar.get(Calendar.DAY_OF_MONTH)
    private val month:Int = myCalendar.get(Calendar.MONTH)
    private val year :Int = myCalendar.get(Calendar.YEAR)
    private val id_user = id_user


    init {
        setCancelable(true)
    }

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.insert_new_client_layout)

        this.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)

        val db = Db_query()

        data.setOnClickListener {

            TimerPicker()
            DateDialog("data")

        }
        il.setOnClickListener {

            DateDialog("il")

        }

        btn_insertNewClient.setOnClickListener{

            val cognome = cognome.text.toString()
            val nome = nome.text.toString()
            val via = via.text.toString()
            val cap = cap.text.toString()
            val citta = citta.text.toString()
            val provincia = provincia.text.toString()
            val tel = tel.text.toString()
            val e_mail = e_mail.text.toString()
            val nato_a = nato_a.text.toString()
            val eta = eta.text.toString()
            val professione = professione.text.toString()
            val motivo = motivo_comunicazione.text.toString()

            val resultInsert: Boolean  = db.inserNewClient(data.text.toString(), cognome, nome, via, cap, citta, provincia, tel, e_mail, nato_a, il.text.toString(), eta, professione, motivo, id_user)
            if(resultInsert){
                Toast.makeText(context, "Inserimento avvenuto con successo", Toast.LENGTH_SHORT).show()
            }
            else {
                Toast.makeText(context, "Errore durante l'inserimento", Toast.LENGTH_SHORT).show()
            }

        }

    }

    fun DateDialog(dove: String) {
        val listener =
            OnDateSetListener { view, year, monthOfYear, dayOfMonth -> if(dove=="data")data.setText("$dayOfMonth/${monthOfYear + 1}/$year") else if(dove == "il")il.setText("$dayOfMonth/${monthOfYear + 1}/$year") }
        val dpDialog = DatePickerDialog(context, listener, year, month, day)
        dpDialog.show()
    }
    fun TimerPicker() {
        val hour = myCalendar.get(Calendar.HOUR)
        val minute = myCalendar.get(Calendar.MINUTE)

        val tpd = TimePickerDialog(context,TimePickerDialog.OnTimeSetListener(function = { view, h, m ->

            data.setText(data.text.toString() + " : " + h.toString() + ":" + m.toString())

        }),hour,minute,false)

        tpd.show()
    }

}