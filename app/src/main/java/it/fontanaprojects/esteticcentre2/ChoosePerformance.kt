package it.fontanaprojects.esteticcentre2

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Window
import kotlinx.android.synthetic.main.choose_performance.*

class ChoosePerformance(context: Context, id_client: String?) : Dialog(context) {

    private val id_client = id_client

    init {
        setCancelable(true)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.choose_performance)

        var choose = ""

        viso_corpo.setOnClickListener {
            choose = "viso_corpo"
            val dialog : Dialog = DialogPerformance(choose, context, id_client)
            dialog.show()
        }

        mani_piedi.setOnClickListener {
            choose = "mani_piedi"
            val dialog : Dialog = DialogPerformance(choose, context, id_client)
            dialog.show()
        }
    }
}