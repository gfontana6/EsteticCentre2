package it.fontanaprojects.esteticcentre2

import android.content.Intent
import android.os.Bundle
import android.os.StrictMode
import android.text.method.PasswordTransformationMethod
import android.util.Base64
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.sign_up_layout.*
import kotlinx.android.synthetic.main.sign_up_layout.password
import kotlinx.android.synthetic.main.sign_up_layout.username
import javax.crypto.Cipher
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec

class SignUp : AppCompatActivity() {

    private val secretKey = "tK5UTui+DPh8lIlBxya5XVsmeDCoUl6vHhdIESMB6sQ="
    private val salt = "QWlGNHNhMTJTQWZ2bGhpV3U=" // base64 decode => AiF4sa12SAfvlhiWu
    private val iv = "bVQzNFNhRkQ1Njc4UUFaWA==" // base64 decode => mT34SaFD5678QAZX

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sign_up_layout)

        supportActionBar?.hide()

        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        val db = Db_query()

        btn_SignUp.setOnClickListener{
            val checkSignIn = db.inserNewAdmin(username.text.toString(), encrypt(password.text.toString().trim())!!.trim())

            if(checkSignIn){
                Toast.makeText(this, "Inserimento avvenuto con successo", Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(this, "Errore durante l'inserimento", Toast.LENGTH_SHORT).show()
            }
        }
        var checkBoxChecked = false
        checkBoxSignUp.setOnClickListener{
            if(checkBoxChecked == false){
                password.transformationMethod = null
                checkBoxChecked = true
            }
            else{
                password.transformationMethod = PasswordTransformationMethod()
                checkBoxChecked = false
            }
        }

        toLogin.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

    }

    override fun onBackPressed() {
        Toast.makeText(this, "Non puoi tornare indietro dalla schermata di Login per motivi di sicurezza", Toast.LENGTH_SHORT).show()
    }

    fun encrypt(strToEncrypt: String) :  String?
    {
        try
        {
            val ivParameterSpec = IvParameterSpec(Base64.decode(iv, Base64.DEFAULT))

            val factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")
            val spec =  PBEKeySpec(secretKey.toCharArray(), Base64.decode(salt, Base64.DEFAULT), 10000, 256)
            val tmp = factory.generateSecret(spec)
            val secretKey =  SecretKeySpec(tmp.encoded, "AES")

            val cipher = Cipher.getInstance("AES/CBC/PKCS7Padding")
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec)
            return Base64.encodeToString(cipher.doFinal(strToEncrypt.toByteArray(Charsets.UTF_8)), Base64.DEFAULT)
        }
        catch (e: Exception)
        {
            println("Error while encrypting: $e")
        }
        return null
    }

}
