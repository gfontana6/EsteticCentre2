package it.fontanaprojects.esteticcentre2

import android.content.Context
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
import java.io.*
import javax.crypto.Cipher
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec

class MainActivity : AppCompatActivity() {

    private val secretKey = "tK5UTui+DPh8lIlBxya5XVsmeDCoUl6vHhdIESMB6sQ="
    private val salt = "QWlGNHNhMTJTQWZ2bGhpV3U=" // base64 decode => AiF4sa12SAfvlhiWu
    private val iv = "bVQzNFNhRkQ1Njc4UUFaWA==" // base64 decode => mT34SaFD5678QAZX
    private var autoLoginCheckText = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()

        val policy = ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        val db = Db_query()

        loginText.setTypeface(null, Typeface.BOLD_ITALIC)

        var readed = ""
        val letDirectory = File(this.filesDir, "textUsername")
        if(!(letDirectory.exists())){
            letDirectory.createNewFile()
        }
        letDirectory.mkdirs()
        val file = File(letDirectory, "username.txt")
        if(!(letDirectory.exists())){
            file.createNewFile()
        }
        FileInputStream(file).bufferedReader().use { readed = it.readText() }
        println(readed)

        if(readed != "" && readed == null){
            val oldAutologin = db.AutoLogin(false, null, readed.trim(), null)
            if(oldAutologin == true){
                val intent = Intent(this, Homepage_User::class.java)
                intent.putExtra("name", readed.trim())
                startActivity(intent)
            }
        }

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

        autoLoginCheck.setOnClickListener{
            autoLoginCheckText = !autoLoginCheckText
        }

        btn_login.setOnClickListener{
            println(encrypt(password.text.toString()))
            val Credenziali = db.checkLogin(username.text.toString(), encrypt(password.text.toString().trim())?.trim())
            if(Credenziali == "Authenticate"){
                val letDirectory = File(this.filesDir, "textUsername")
                letDirectory.createNewFile()
                letDirectory.mkdirs()
                val file = File(letDirectory, "username.txt")
                file.createNewFile()
                file.delete()

                try {
                    val letDirectory = File(this.filesDir, "textUsername")
                    letDirectory.createNewFile()
                    letDirectory.mkdirs()
                    val file = File(letDirectory, "username.txt")
                    file.createNewFile()
                    file.appendText(username.text.toString())
                }
                catch (e: Exception) {
                    e.printStackTrace()
                }
                    if(autoLoginCheckText==true){
                        val auto = db.AutoLogin(true, "true", username.text.toString(), encrypt(password.text.toString().trim())!!.trim())
                        if(auto == false){
                            println("l'update non è andato a buon fine")
                        }
                    }
                    else{
                        val auto = db.AutoLogin(true, "false", username.text.toString(), encrypt(password.text.toString().trim())!!.trim())
                        if(auto == false){
                            println("l'update non è andato a buon fine")
                        }
                    }

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


