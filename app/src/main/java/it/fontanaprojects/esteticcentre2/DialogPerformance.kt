package it.fontanaprojects.esteticcentre2

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Window
import android.widget.EditText
import android.widget.TableRow
import android.widget.TextView
import androidx.core.view.isNotEmpty
import kotlinx.android.synthetic.main.dialog_performance_layout.*
import kotlinx.android.synthetic.main.homepage_user_layout.*

class DialogPerformance(private val choose: String, context: Context, id_client: String?) : Dialog(context){

    private val id_client = id_client

    init {
        setCancelable(true)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_performance_layout)

        println(id_client)

        if(TableQuestions.isNotEmpty()){
            TableQuestions.removeAllViews()
        }

        if(choose == "viso_corpo"){
            val executeTrattament = TextView(context)
            executeTrattament.text = "Trattamento eseguito"
            val editTextTrattamento = EditText(context)
            editTextTrattamento.hint = "Trattamento"
            var row = TableRow(context)
            row.addView(executeTrattament)
            row.addView(editTextTrattamento)
            TableQuestions.addView(row)

            val mediatore = TextView(context)
            mediatore.text = "Mediatore"
            val editTextMediatore = EditText(context)
            editTextMediatore.hint = "Mediatore"
            row = TableRow(context)
            row.addView(mediatore)
            row.addView(editTextMediatore)
            TableQuestions.addView(row)

            val base = TextView(context)
            base.text = "Base"
            val editTextBase = EditText(context)
            editTextBase.hint = "Base"
            row = TableRow(context)
            row.addView(base)
            row.addView(editTextBase)
            TableQuestions.addView(row)

            val gel = TextView(context)
            gel.text = "Gel"
            val editTextgel = EditText(context)
            editTextgel.hint = "Gel"
            row = TableRow(context)
            row.addView(gel)
            row.addView(editTextgel)
            TableQuestions.addView(row)

            val colore_mani = TextView(context)
            colore_mani.text = "Colore Mani"
            val editTextcolore_mani = EditText(context)
            editTextcolore_mani.hint = "Colore Mani"
            row = TableRow(context)
            row.addView(colore_mani)
            row.addView(editTextcolore_mani)
            TableQuestions.addView(row)

            val sigillante = TextView(context)
            sigillante.text = "Sigillante"
            val editTextsigillante = EditText(context)
            editTextsigillante.hint = "Sigillante"
            row = TableRow(context)
            row.addView(sigillante)
            row.addView(editTextsigillante)
            TableQuestions.addView(row)

            val tips = TextView(context)
            tips.text = "Tips"
            val editTexttips = EditText(context)
            editTexttips.hint = "Tips"
            row = TableRow(context)
            row.addView(tips)
            row.addView(editTexttips)
            TableQuestions.addView(row)

        }
        else if(choose == "mani_piedi"){
            val Domanda1 = TextView(context)
            Domanda1.text = "Domanda1"
            val Domanda1edit = EditText(context)
            Domanda1edit.hint = "Domanda1"
            var row = TableRow(context)
            row.addView(Domanda1)
            row.addView(Domanda1edit)
            TableQuestions.addView(row)

            val Domanda2 = TextView(context)
            Domanda2.text = "Domanda2"
            val Domanda2edit = EditText(context)
            Domanda2edit.hint = "Domanda2"
            row = TableRow(context)
            row.addView(Domanda2)
            row.addView(Domanda2edit)
            TableQuestions.addView(row)

            val Domanda3 = TextView(context)
            Domanda3.text = "Domanda3"
            val Domanda3edit = EditText(context)
            Domanda3edit.hint = "Domanda3"
            row = TableRow(context)
            row.addView(Domanda3)
            row.addView(Domanda3edit)
            TableQuestions.addView(row)

            val Domanda4 = TextView(context)
            Domanda4.text = "Domanda4"
            val Domanda4edit = EditText(context)
            Domanda4edit.hint = "Domanda4"
            row = TableRow(context)
            row.addView(Domanda4)
            row.addView(Domanda4edit)
            TableQuestions.addView(row)

            val Domanda5 = TextView(context)
            Domanda5.text = "Domanda5"
            val Domanda5edit = EditText(context)
            Domanda5edit.hint = "Domanda5"
            row = TableRow(context)
            row.addView(Domanda5)
            row.addView(Domanda5edit)
            TableQuestions.addView(row)

            val Domanda6 = TextView(context)
            Domanda6.text = "Domanda6"
            val Domanda6edit = EditText(context)
            Domanda6edit.hint = "Domanda6"
            row = TableRow(context)
            row.addView(Domanda6)
            row.addView(Domanda6edit)
            TableQuestions.addView(row)

            val Domanda7 = TextView(context)
            Domanda7.text = "Domanda7"
            val Domanda7edit = EditText(context)
            Domanda7edit.hint = "Domanda7"
            row = TableRow(context)
            row.addView(Domanda7)
            row.addView(Domanda7edit)
            TableQuestions.addView(row)
        }
    }
}