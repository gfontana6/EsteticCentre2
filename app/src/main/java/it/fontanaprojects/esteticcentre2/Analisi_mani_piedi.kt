package it.fontanaprojects.esteticcentre2

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toBitmap
import kotlinx.android.synthetic.main.mani_piedi_analisi_layout.*
import kotlinx.coroutines.CoroutineScope
import java.io.ByteArrayOutputStream
import kotlin.coroutines.CoroutineContext


class Analisi_mani_piedi : AppCompatActivity() {

    private var camerachosed: ImageView? = null
    private val cameraRequest = 1888
    private val PICK_IMAGE = 100
    private val contextAnalisi_mani_piedi = this

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.mani_piedi_analisi_layout)

        this.window!!.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )

        val id_cliente: String? = intent.getStringExtra("id_client")
        val id_ultimo_trattamento: Int = intent.getIntExtra("id_ultimo_trattamento", 1)

        supportActionBar?.hide()

        val db = Db_query()

        val dataPersonal = db.readPersonalData(id_cliente!!)
        name_cognometext.text = dataPersonal!![0]
        data_nascita.text = dataPersonal[1]
        address.text = dataPersonal[2]
        tel.text = dataPersonal[3]
        mail.text = dataPersonal[4]
        professione.setText(dataPersonal[5])

        var SiGuantiactivated = false
        var Siiporidrosiactivated = false
        var SiMicosiUnghialeactivated = false
        var SiOncicofobiaactivated = false
        var SiStrappareCuticoleactivated = false
        var SiProblemiOrmonaliactivated = false
        var ConsensoInformativoActivated = false

        professione.isEnabled = false
        farmaci.isEnabled = false
        patologie.isEnabled = false
        allergie.isEnabled = false
        colore_preferito.isEnabled = false
        hobby_sport.isEnabled = false

        val thread1=Thread(Runnable {

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
            NoOnicofobia.setOnClickListener {
                SiOnicofobia.isChecked = false
                NoOnicofobia.isChecked = true
                SiOncicofobiaactivated = false
            }

            SiStrappareCuticole.setOnClickListener {
                NoStrappareCuticole.isChecked = false
                SiStrappareCuticole.isChecked = true
                SiStrappareCuticoleactivated = true
            }
            NoStrappareCuticole.setOnClickListener {
                SiStrappareCuticole.isChecked = false
                NoStrappareCuticole.isChecked = true
                SiStrappareCuticoleactivated = false
            }

            SiProblemiOrmonali.setOnClickListener {
                NoProblemiOrmonali.isChecked = false
                SiProblemiOrmonali.isChecked = true
                SiProblemiOrmonaliactivated = true
            }
            NoProblemiOrmonali.setOnClickListener {
                SiProblemiOrmonali.isChecked = false
                NoProblemiOrmonali.isChecked = true
                SiProblemiOrmonaliactivated = false
            }

            consenso_informativo_mani_piedi_analisi.setOnClickListener {
                ConsensoInformativoActivated = if (!ConsensoInformativoActivated) {
                    true
                } else {
                    true
                }
            }
            Thread.sleep(2000)
        })
        thread1.isDaemon = true
        thread1.start()

        val thread2 = Thread(
            Runnable {
                var AltroAnalisiLettoChecked = false
                AltroAnalisiLettoText.isEnabled = false

                AltroAnalisiLettoBox.setOnClickListener {

                    if (AltroAnalisiLettoChecked == false) {

                        AltroAnalisiLettoChecked = true
                        AltroAnalisiLettoText.isEnabled = true

                    } else {

                        AltroAnalisiLettoChecked = false
                        AltroAnalisiLettoText.isEnabled = false

                    }
                }

                var AltroAnalisiCuticoleChecked = false
                AltroAnalisiCuticoleText.isEnabled = false

                AltroAnalisiCuticoleBox.setOnClickListener {

                    if (AltroAnalisiCuticoleChecked == false) {

                        AltroAnalisiCuticoleChecked = true
                        AltroAnalisiCuticoleText.isEnabled = true

                    } else {

                        AltroAnalisiCuticoleChecked = false
                        AltroAnalisiCuticoleText.isEnabled = false

                    }
                }

                var AltroSuperficieChecked = false
                AltroSuperficeText.isEnabled = false

                AltroSuperficeBox.setOnClickListener {

                    if (AltroSuperficieChecked == false) {

                        AltroSuperficieChecked = true
                        AltroSuperficeText.isEnabled = true

                    } else {

                        AltroSuperficieChecked = false
                        AltroSuperficeText.isEnabled = false

                    }
                }

                var AltroArchitetturaChecked = false
                AltroArchitetturaText.isEnabled = false

                AltroArchitetturaBox.setOnClickListener {

                    if (AltroArchitetturaChecked == false) {

                        AltroArchitetturaChecked = true
                        AltroArchitetturaText.isEnabled = true

                    } else {

                        AltroArchitetturaChecked = false
                        AltroArchitetturaText.isEnabled = false

                    }
                }

                var professionChecked = false
                professione.isEnabled = false

                checkProfessione.setOnClickListener {

                    if (professionChecked == false) {

                        professionChecked = true
                        professione.isEnabled = true

                    } else {

                        professionChecked = false
                        professione.isEnabled = false

                    }
                }

                var farmaciChecked = false
                farmaci.isEnabled = false

                checkFarmaci.setOnClickListener {

                    if (farmaciChecked == false) {

                        farmaciChecked = true
                        farmaci.isEnabled = true

                    } else {

                        farmaciChecked = false
                        farmaci.isEnabled = false

                    }
                }

                var patologieChecked = false
                patologie.isEnabled = false

                checkPatologie.setOnClickListener {

                    if (patologieChecked == false) {

                        patologieChecked = true
                        patologie.isEnabled = true

                    } else {

                        patologieChecked = false
                        patologie.isEnabled = false

                    }
                }

                var allergieChecked = false
                allergie.isEnabled = false

                checkAllergie.setOnClickListener {

                    if (allergieChecked == false) {

                        allergieChecked = true
                        allergie.isEnabled = true

                    } else {

                        allergieChecked = false
                        allergie.isEnabled = false

                    }
                }

                var coloreChecked = false
                colore_preferito.isEnabled = false

                checkColore.setOnClickListener {

                    if (coloreChecked == false) {

                        coloreChecked = true
                        colore_preferito.isEnabled = true

                    } else {

                        coloreChecked = false
                        colore_preferito.isEnabled = false

                    }
                }

                var hobby_sportChecked = false
                hobby_sport.isEnabled = false

                checkHobby_Sport.setOnClickListener {

                    if (hobby_sportChecked == false) {

                        hobby_sportChecked = true
                        hobby_sport.isEnabled = true

                    } else {

                        hobby_sportChecked = false
                        hobby_sport.isEnabled = false

                    }
                }
                Thread.sleep(3000)
            })
            thread2.isDaemon = true
            thread2.start()

            val thread3 = Thread(
                Runnable {
                    val mutableListFoto: MutableList<ImageView> = mutableListOf()
                    mutableListFoto.add(0, Foto1Analisi)
                    mutableListFoto.add(1, Foto2Analisi)
                    mutableListFoto.add(2, Foto3Analisi)
                    mutableListFoto.add(3, Foto4Analisi)

                    val mutableListButtonCameraFoto: MutableList<ImageButton> = mutableListOf()
                    mutableListButtonCameraFoto.add(0, btn_Image1Analisi)
                    mutableListButtonCameraFoto.add(1, btn_Image2Analisi)
                    mutableListButtonCameraFoto.add(2, btn_Image3Analisi)
                    mutableListButtonCameraFoto.add(3, btn_Image4Analisi)

                    val mutableListButtonStorageFoto: MutableList<ImageButton> = mutableListOf()
                    mutableListButtonStorageFoto.add(0, btn_Storage1Analisi)
                    mutableListButtonStorageFoto.add(1, btn_Storage2Analisi)
                    mutableListButtonStorageFoto.add(2, btn_Storage3Analisi)
                    mutableListButtonStorageFoto.add(3, btn_Storage4Analisi)


                    for (i in 0..3) {

                        mutableListButtonCameraFoto[i].setOnClickListener {
                            camerachosed = mutableListFoto[i]
                            try {
                                val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                                startActivityForResult(cameraIntent, cameraRequest)
                            } catch (e: SecurityException) {
                                Toast.makeText(
                                    contextAnalisi_mani_piedi,
                                    "Permesso negato, andare nelle informazioni dell'app, su impostazioni, per garantire l'accesso alla fotocamera",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                        mutableListButtonStorageFoto[i].setOnClickListener {
                            camerachosed = mutableListFoto[i]
                            val gallery =
                                Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
                            startActivityForResult(gallery, PICK_IMAGE)

                        }
                    }

                    Thread.sleep(4000)

                }
            )
        thread3.isDaemon = true
        thread3.start()

        addDigitalSignatureManiPiedi.setOnClickListener {

            val DigitalSignturePad = DigitalSignature(this, this, null,  id_cliente)
            DigitalSignturePad.show()
        }



        val threadSaveAll = Thread(Runnable{
            Btn_SaveSchedaAnalisi.setOnClickListener{
                var professioneValue = ""
                if(professione.isEnabled) {
                    professioneValue = professione.text.toString()
                }
                var farmaciValue = ""
                if(farmaci.isEnabled) {
                    farmaciValue = farmaci.text.toString()
                }
                var patologieValue = ""
                if(patologie.isEnabled) {
                    patologieValue = patologie.text.toString()
                }

                var allergineValue = ""
                if(allergie.isEnabled) {
                    allergineValue = allergie.text.toString()
                }

                var coloreValue = ""
                if(colore_preferito.isEnabled) {
                    coloreValue = colore_preferito.text.toString()
                }

                var hobby_sportValue = ""
                if(hobby_sport.isEnabled) {
                    hobby_sportValue = hobby_sport.text.toString()
                }

                var imageSignatureString = ""
                val stream = ByteArrayOutputStream()

                if(imageSignature.drawable != null){
                    val bitmap: Bitmap = imageSignature.drawable.toBitmap()
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
                    imageSignatureString = Base64.encodeToString(stream.toByteArray(), Base64.DEFAULT) //for save in DB PostGress
                }

                var analisiLetto = ""
                if(FungoAnalisiLetto.isChecked){
                        analisiLetto += FungoAnalisiLetto.text.toString()+";"
                }
                if(PseudomaAnalisiLetto.isChecked){
                        analisiLetto += PseudomaAnalisiLetto.text.toString()+";"
                }
                if(ArrossatoAnalisiLetto.isChecked){
                    analisiLetto += ArrossatoAnalisiLetto.text.toString()+";"
                }
                if(AltroAnalisiLettoText.isEnabled){
                    analisiLetto += AltroAnalisiLettoText.text.toString()+";"
                }

                var analisiCuticole = ""
                if(SensibiliAnalisiCuticole.isChecked){
                    analisiCuticole += SensibiliAnalisiCuticole.text.toString()+";"
                }
                if(SpesseAnalisiCuticole.isChecked){
                    analisiCuticole += SpesseAnalisiCuticole.text.toString()+";"
                }
                if(LesionataAnalisiCuticole.isChecked){
                    analisiCuticole += LesionataAnalisiCuticole.text.toString()+";"
                }
                if(MangiataAnalisiCuticole.isChecked){
                    analisiCuticole += MangiataAnalisiCuticole.text.toString()+";"
                }
                if(AltroAnalisiCuticoleText.isEnabled){
                    analisiCuticole += AltroAnalisiCuticoleText.text.toString()+";"
                }

                var analisiSuperficie = ""
                if(OnicolisiSuperfice.isChecked){
                    analisiSuperficie += OnicolisiSuperfice.text.toString()+";"
                }
                if(SfaldataSuperfice.isChecked){
                    analisiSuperficie += SfaldataSuperfice.text.toString()+";"
                }
                if(RigataSuperfice.isChecked){
                    analisiSuperficie += RigataSuperfice.text.toString()+";"
                }
                if(AvvallamentiSuperfice.isChecked){
                    analisiSuperficie += AvvallamentiSuperfice.text.toString()+";"
                }
                if(MaccheBiancheSuperfice.isChecked){
                    analisiSuperficie += MaccheBiancheSuperfice.text.toString()+";"
                }
                if(GrassaLucidaSuperfice.isChecked){
                    analisiSuperficie += GrassaLucidaSuperfice.text.toString()+";"
                }
                if(SeccaSuperfice.isChecked){
                    analisiSuperficie += SeccaSuperfice.text.toString()+";"
                }
                if(AltroSuperficeText.isEnabled){
                    analisiSuperficie += AltroSuperficeText.text.toString()+";"
                }

                var analisiArchitettura = ""
                if(LateraliAssentiArchitettura.isChecked){
                    analisiArchitettura += LateraliAssentiArchitettura.text.toString()+";"
                }
                if(AVentaglioArchitettura.isChecked){
                    analisiArchitettura += AVentaglioArchitettura.text.toString()+";"
                }
                if(OnicogrifoticaArchitettura.isChecked){
                    analisiArchitettura += OnicogrifoticaArchitettura.text.toString()+";"
                }
                if(CucchiaioArchitettura.isChecked){
                    analisiArchitettura += CucchiaioArchitettura.text.toString()+";"
                }
                if(StandardArchitettura.isChecked){
                    analisiArchitettura += StandardArchitettura.text.toString()+";"
                }
                if(OnicofagicaSuperfice.isChecked){
                    analisiArchitettura += OnicofagicaSuperfice.text.toString()+";"
                }

                if(AltroArchitetturaText.isEnabled){
                    analisiArchitettura += AltroArchitetturaText.text.toString()+";"
                }

                var imageFoto1String = ""
                val stream2 = ByteArrayOutputStream()
                try {
                    if (Foto1Analisi.drawable != null) {
                        val bitmap = Foto1Analisi.drawable.toBitmap()
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream2)
                        imageFoto1String = Base64.encodeToString(stream2.toByteArray(), Base64.DEFAULT) //for save in DB PostGress
                    }
                }
                catch (e: NullPointerException){
                    imageFoto1String = ""
                }

                var imageFoto2String = ""
                val stream3 = ByteArrayOutputStream()

                try{
                if(Foto2Analisi.drawable != null) {
                    val bitmap = Foto2Analisi.drawable.toBitmap()
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream3)
                    imageFoto2String = Base64.encodeToString(stream3.toByteArray(), Base64.DEFAULT)
                }
                }
                catch (e: NullPointerException){
                    imageFoto2String = ""
                }

                var imageFoto3String = ""
                val stream4 = ByteArrayOutputStream()
                try{
                if(Foto3Analisi.drawable != null) {
                    val bitmap = Foto3Analisi.drawable.toBitmap()
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream4)
                    imageFoto3String = Base64.encodeToString(stream4.toByteArray(), Base64.DEFAULT)
                }
                }
                catch (e: NullPointerException){
                    imageFoto3String = ""
                }

                var imageFoto4String = ""
                val stream5 = ByteArrayOutputStream()
                try {
                    if (Foto4Analisi.drawable != null) {
                        val bitmap = Foto4Analisi.drawable.toBitmap()
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream5)
                        imageFoto4String = Base64.encodeToString(stream5.toByteArray(), Base64.DEFAULT)
                    }
                }
                catch (e: NullPointerException){
                    imageFoto4String = ""
                }

                 /*   println(professioneValue)
                    println(farmaciValue)
                    println(patologieValue)
                    println(allergineValue)
                    println(coloreValue)
                    println(hobby_sportValue)
                    println(SiGuantiactivated)
                    println(Siiporidrosiactivated)
                    println(SiMicosiUnghialeactivated)
                    println(SiOncicofobiaactivated)
                    println(SiStrappareCuticoleactivated)
                    println(SiProblemiOrmonaliactivated)
                    println(ConsensoInformativoActivated)
                    println(imageSignatureString)
                    println(analisiLetto)
                    println(analisiCuticole)
                    println(analisiSuperficie)
                    println(analisiArchitettura)
                    println(imageFoto1String)
                    println(imageFoto2String)
                    println(imageFoto3String)
                    println(imageFoto4String)*/

                if(ConsensoInformativoActivated == true){
                    val check_insert = db.inserNewAnalisi(id_ultimo_trattamento, id_cliente.toInt(), professioneValue, farmaciValue, patologieValue,
                    allergineValue, coloreValue, hobby_sportValue, SiGuantiactivated, Siiporidrosiactivated, SiMicosiUnghialeactivated,
                    SiOncicofobiaactivated, SiStrappareCuticoleactivated, SiProblemiOrmonaliactivated, ConsensoInformativoActivated,
                    imageSignatureString, analisiLetto, analisiCuticole, analisiSuperficie, analisiArchitettura, imageFoto1String,
                    imageFoto2String, imageFoto3String, imageFoto4String)

                    if(check_insert){
                        Toast.makeText(this, "inserimento avvenuto con successo", Toast.LENGTH_SHORT).show()
                    }
                    else{
                        Toast.makeText(this, "Errore durante l'inserimento", Toast.LENGTH_SHORT).show()
                    }
                }
                else{
                    val builder = AlertDialog.Builder(this)
                    builder.setTitle("Consenso trattamento dati personali assente")
                    builder.setMessage("Per poter memorizzare i dati, bisogna acconsentire al trattamento dei dati personali")
                    builder.show()
                }
            }
        })
        threadSaveAll.start()
/*
        val immagineprova: String? = db.leggoimmagineprova()

        if(immagineprova == null){
            Toast.makeText(this, "errore recupero immagine", Toast.LENGTH_LONG).show()
        }
        else {
            val imageBytes = Base64.decode(immagineprova, 0)
            val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
            Foto1Analisi.setImageBitmap(decodedImage)
        }
*/
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