package it.fontanaprojects.esteticcentre2

import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.show_trattaments_layout.*
import org.w3c.dom.Text
import java.util.*

class ShowTrattaments(private val id_trattamento: String, context: Context): Dialog(context) {


    private val myCalendar: Calendar = Calendar.getInstance()
    private val day :Int = myCalendar.get(Calendar.DAY_OF_MONTH)
    private val month:Int = myCalendar.get(Calendar.MONTH)
    private val year :Int = myCalendar.get(Calendar.YEAR)

    init {
        setCancelable(true)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.show_trattaments_layout)


        val db = Db_query()
        var editText = EditText(context)
        var textView = TextView(context)

        val showTrattamentValues = db.searchTrattament(id_trattamento, false, null, null)
        var SingolTrattment : List<String>? = null
        var linearLayoutHorizzontal: LinearLayout = LinearLayout(context)

            for(iteration in 0 .. showTrattamentValues!!.size) {

                SingolTrattment = showTrattamentValues[iteration].split(";")

                Thread(Runnable {
                    linearLayoutHorizzontal = LinearLayout(context)
                    linearLayoutHorizzontal.orientation = LinearLayout.HORIZONTAL
                    linearLayoutHorizzontal.minimumWidth = ConstraintLayout.LayoutParams.MATCH_PARENT
                    textView = TextView(context)
                    textView.text = "Data"
                    textView.textSize = 25F
                    linearLayoutHorizzontal.addView(textView)
                    (textView.layoutParams as LinearLayout.LayoutParams).weight = 0.2F

                }).start()

                val thisThread = Thread(Runnable {
                    editText = EditText(context)
                    editText.setText(SingolTrattment!![0])
                    editText.id = ("1$iteration").toInt()
                    editText.isFocusable = false
                    linearLayoutHorizzontal.addView(editText)
                    (editText.layoutParams as LinearLayout.LayoutParams).weight = 1F
                    linearShow.addView(linearLayoutHorizzontal)

                    val idForListener = editText.id
                    val listener = findViewById<EditText>(idForListener)
                    listener.setOnClickListener {

                        TimerPicker(listener)
                        DateDialog(listener)

                    }
                })
                thisThread.interrupt()
                thisThread.start()

                if(thisThread.isInterrupted) {
                    for (y in 2..9) {

                        linearLayoutHorizzontal = LinearLayout(context)
                        linearLayoutHorizzontal.orientation = LinearLayout.HORIZONTAL
                        linearLayoutHorizzontal.minimumWidth =
                            ConstraintLayout.LayoutParams.MATCH_PARENT
                        textView = TextView(context)

                        if (y == 2) {
                            textView.text = "Trattamento"
                        } else if (y == 3) {
                            textView.text = "Mediatore"
                        } else if (y == 4) {
                            textView.text = "Base"
                        } else if (y == 5) {
                            textView.text = "Gel"
                        } else if (y == 6) {
                            textView.text = "Top"
                        } else if (y == 7) {
                            textView.text = "Colore"
                        } else if (y == 8) {
                            textView.text = "Deco"
                        } else if (y == 9) {
                            textView.text = "Altro"
                        }

                        textView.textSize = 25F
                        linearLayoutHorizzontal.addView(textView)
                        (textView.layoutParams as LinearLayout.LayoutParams).weight = 0.2F
                        editText = EditText(context)
                        editText.setText(SingolTrattment!![y - 1])
                        editText.id = ("$y$iteration").toInt()
                        linearLayoutHorizzontal.addView(editText)
                        (editText.layoutParams as LinearLayout.LayoutParams).weight = 1F
                        linearShow.addView(linearLayoutHorizzontal)
                    }
                }

                val view = View(context)
                view.minimumWidth = ConstraintLayout.LayoutParams.MATCH_PARENT
                view.minimumHeight = 5
                view.setBackgroundColor(ContextCompat.getColor(context, R.color.black))
                linearShow.addView(view)

            }
    }

    fun DateDialog(listener: EditText) {
        val listener =
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                listener.setText("$dayOfMonth/${monthOfYear + 1}/$year")
            }
        val dpDialog = DatePickerDialog(context, listener, year, month, day)
        dpDialog.show()
    }
    fun TimerPicker(listener: EditText) {
        val hour = myCalendar.get(Calendar.HOUR)
        val minute = myCalendar.get(Calendar.MINUTE)

        val tpd = TimePickerDialog(context,
            TimePickerDialog.OnTimeSetListener(function = { view, h, m ->

                listener.setText(listener.text.toString() + " : " + h.toString() + ":" + m.toString())

            }),hour,minute,false)

        tpd.show()
    }

}