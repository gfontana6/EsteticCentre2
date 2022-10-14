package it.fontanaprojects.esteticcentre2

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.mani_piedi_analisi_layout.*

class Analisi_viso_corpo : AppCompatActivity() {

    private var camerachosed: ImageView? = null
    private val cameraRequest = 1888
    private val PICK_IMAGE = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.viso_corpo_analisi_layout)

        val id_cliente : String? = intent.getStringExtra("id_client")
        val id_ultimo_trattamento: Int = intent.getIntExtra("id_ultimo_trattamento", 1)

        this.window!!.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )

        val db = Db_query()

        supportActionBar?.hide()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        try{
            if (requestCode == cameraRequest) {
                val photo: Bitmap = data?.extras?.get("data") as Bitmap
                camerachosed?.setImageBitmap(photo)
            }
            else if(requestCode == PICK_IMAGE){
                val uri = data?.data
                camerachosed?.setImageURI(uri)
            }
        }
        catch (i: RuntimeException){
            println("dovuto all'errore di prima")
        }
    }

    fun setImageSignature(image: Bitmap){
        imageSignature.setImageBitmap(image)
    }
}