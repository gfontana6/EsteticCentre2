package it.fontanaprojects.esteticcentre2

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.text.method.PasswordTransformationMethod
import android.util.Base64
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import javax.crypto.Cipher
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec

class MainActivity : AppCompatActivity() {

    private val secretKey = "tK5UTui+DPh8lIlBxya5XVsmeDCoUl6vHhdIESMB6sQ="
    private val salt = "QWlGNHNhMTJTQWZ2bGhpV3U=" // base64 decode => AiF4sa12SAfvlhiWu
    private val iv = "bVQzNFNhRkQ1Njc4UUFaWA==" // base64 decode => mT34SaFD5678QAZX

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()

        val policy = ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        val connection = Connessione().connetti()
        val db = Db_query(connection)

        loginText.setTypeface(null, Typeface.BOLD_ITALIC)
        var checkBoxChecked = false
        checkBoxLogin.setOnClickListener{
            if(checkBoxChecked == false){
                password.transformationMethod = null
                checkBoxChecked = true
            }
            else{
                password.transformationMethod = PasswordTransformationMethod()
                checkBoxChecked = false
            }
        }
        btn_login.setOnClickListener{
            println(encrypt(password.text.toString()))
            val Credenziali = db.checkLogin(username.text.toString(), encrypt(password.text.toString().trim())?.trim())
            if(Credenziali == "Authenticate"){
                val intent = Intent(this, Homepage_User::class.java)
                intent.putExtra("name", username.text.toString())
                startActivity(intent)
            }
            else{
                Toast.makeText(this, Credenziali, Toast.LENGTH_LONG).show()
            }
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

    fun decrypt(strToDecrypt : String) : String? {
        try
        {

            val ivParameterSpec =  IvParameterSpec(Base64.decode(iv, Base64.DEFAULT))

            val factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")
            val spec =  PBEKeySpec(secretKey.toCharArray(), Base64.decode(salt, Base64.DEFAULT), 10000, 256)
            val tmp = factory.generateSecret(spec);
            val secretKey =  SecretKeySpec(tmp.encoded, "AES")

            val cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec);
            return  String(cipher.doFinal(Base64.decode(strToDecrypt, Base64.DEFAULT)))
        }
        catch (e : Exception) {
            println("Error while decrypting: $e");
        }
        return null
    }

}


