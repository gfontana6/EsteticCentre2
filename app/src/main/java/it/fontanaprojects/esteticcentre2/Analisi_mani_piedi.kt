package it.fontanaprojects.esteticcentre2

import android.app.Dialog
import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.view.ViewGroup
import android.view.Window
import kotlinx.android.synthetic.main.mani_piedi_analisi_layout.*

class Analisi_mani_piedi(context: Context, id_cliente: String) : Dialog(context) {

    private val id_cliente = id_cliente


    init {
        setCancelable(true)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.mani_piedi_analisi_layout)

        this.window!!.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )

        val db = Db_query()

        val dataPersonal = db.readPersonalData(id_cliente)
        name_cognometext.text = dataPersonal!![0]
        data_nascita.text = dataPersonal[1]
        address.text = dataPersonal[2]
        tel.text = dataPersonal[3]
        mail.text = dataPersonal[4]
        professione.text = dataPersonal[5]

        var SiGuantiactivated = false
        var Siiporidrosiactivated = false
        var SiMicosiUnghialeactivated = false
        var SiOncicofobiaactivated = false
        var SiStrappareCuticoleactivated = false
        var SiProblemiOrmonaliactivated = false
        var ConsensoInformativoActivated = false

        val thread = Runnable {
            SiGuanti.setOnClickListener {
                NoGuanti.isChecked = false
                SiGuanti.isChecked = true
                SiGuantiactivated = true
            }
            NoGuanti.setOnClickListener {
                SiGuanti.isChecked = false
                NoGuanti.isChecked = true
                SiGuantiactivated = false
            }

            SiIporidrosi.setOnClickListener {
                NoIporidrosi.isChecked = false
                SiIporidrosi.isChecked = true
                Siiporidrosiactivated = true
            }
            NoIporidrosi.setOnClickListener {
                SiIporidrosi.isChecked = false
                NoIporidrosi.isChecked = true
                Siiporidrosiactivated = false
            }

            SiMicosiUngueale.setOnClickListener {
                NoMicosiUngueale.isChecked = false
                SiMicosiUngueale.isChecked = true
                SiMicosiUnghialeactivated = true
            }
            NoMicosiUngueale.setOnClickListener {
                SiMicosiUngueale.isChecked = false
                NoMicosiUngueale.isChecked = true
                SiMicosiUnghialeactivated = false
            }

            SiOnicofobia.setOnClickListener {
                NoOnicofobia.isChecked = false
                SiOnicofobia.isChecked = true
                SiOncicofobiaactivated = true
            }
            NoOnicofobia.setOnClickListener{
                SiOnicofobia.isChecked = false
                NoOnicofobia.isChecked = true
                SiOncicofobiaactivated = false
            }

            SiStrappareCuticole.setOnClickListener {
                NoStrappareCuticole.isChecked = false
                SiStrappareCuticole.isChecked = true
                SiStrappareCuticoleactivated = true
            }
            NoStrappareCuticole.setOnClickListener{
                SiStrappareCuticole.isChecked = false
                NoStrappareCuticole.isChecked = true
                SiStrappareCuticoleactivated = false
            }

            SiProblemiOrmonali.setOnClickListener {
                NoProblemiOrmonali.isChecked = false
                SiProblemiOrmonali.isChecked = true
                SiProblemiOrmonaliactivated = true
            }
            NoProblemiOrmonali.setOnClickListener{
                SiProblemiOrmonali.isChecked = false
                NoProblemiOrmonali.isChecked = true
                SiProblemiOrmonaliactivated = false
            }
            consenso_informativo_mani_piedi_analisi.setOnClickListener{
                ConsensoInformativoActivated = if(!ConsensoInformativoActivated){
                    true
                } else{
                    true
                }
            }
            var AltroAnalisiLettoChecked = false
            AltroAnalisiLettoText.isEnabled = false

            AltroAnalisiLettoBox.setOnClickListener {

                if(AltroAnalisiLettoChecked == false){

                    AltroAnalisiLettoChecked = true
                    AltroAnalisiLettoText.isEnabled = true

                }
                else{

                    AltroAnalisiLettoChecked = false
                    AltroAnalisiLettoText.isEnabled = false

                }
            }

            var AltroAnalisiLettoChecked = false
            AltroAnalisiLettoText.isEnabled = false

            AltroAnalisiLettoBox.setOnClickListener {

                if(AltroAnalisiLettoChecked == false){

                    AltroAnalisiLettoChecked = true
                    AltroAnalisiLettoText.isEnabled = true

                }
                else{

                    AltroAnalisiLettoChecked = false
                    AltroAnalisiLettoText.isEnabled = false

                }
            }


        }
        thread.run()

        addDigitalSignatureManiPiedi.setOnClickListener {

            val DigitalSignturePad = DigitalSignature(context, this, null,  id_cliente)
            DigitalSignturePad.show()
        }

    }






    fun setImageSignature(image: Bitmap){
        imageSignature.setImageBitmap(image)
    }
}