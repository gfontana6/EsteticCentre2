package it.fontanaprojects.esteticcentre2

import android.app.Dialog
import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.view.ViewGroup
import android.view.Window
import kotlinx.android.synthetic.main.digital_signature_layout.*

class DigitalSignature(context: Context, precedentDialogManiPiedi: Analisi_mani_piedi?, precedentDialogVisoCorpo: Analisi_viso_corpo?, id_client: String) : Dialog(context) {

    private val id_client = id_client
    private val precedentDialogManiPiedi = precedentDialogManiPiedi
    private val precedentDialogVisoCorpo = precedentDialogVisoCorpo


    init {
        setCancelable(true)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.digital_signature_layout)

        this.window!!.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )

        AddSignature.setOnClickListener {
            val bitmap: Bitmap = Signature_pad.signatureBitmap
            if(precedentDialogVisoCorpo == null) {
                precedentDialogManiPiedi!!.setImageSignature(bitmap)
                Signature_pad.clear()
                this.dismiss()
            }
            else{
                //precedentDialogVisoCorpo!!.setImageSignature(bitmap)
                Signature_pad.clear()
                this.dismiss()
            }
        }
    }
}