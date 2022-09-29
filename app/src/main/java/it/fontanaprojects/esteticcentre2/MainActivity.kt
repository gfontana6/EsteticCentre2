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
        val file = File(this.filesDir, "textUsername")
        file.createNewFile()
        file.parentFile?.mkdirs()
        readed = FileInputStream(file).bufferedReader().use { it.readText() }

        var id_user = ""
        var readedText = ""
        if(readed.contains(";")){
            val readedsplit = readed.split(";")
            id_user = readedsplit[1]
            readedText = readedsplit[0]
        }

        val oldAutologin = db.AutoLogin(false, null, id_user)
        println(oldAutologin)
        if(oldAutologin == true) {
            val intent = Intent(this, Homepage_User::class.java)
            intent.putExtra("name", readedText.trim())
            intent.putExtra("id_user", id_user.trim())
            startActivity(intent)
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

        toSignUp.setOnClickListener {
            startActivity(Intent(this, SignUp::class.java))
        }
        btn_login.setOnClickListener{
            println(encrypt(password.text.toString()))
            val Credenziali = db.checkLogin(username.text.toString(), encrypt(password.text.toString().trim())?.trim())
            if(Credenziali.split(";")[0] == "Authenticate"){

                val file = File(this.filesDir, "textUsername")
                file.createNewFile()
                file.parentFile?.mkdirs()
                file.delete()

                try {
                    val file = File(this.filesDir, "textUsername")
                    file.createNewFile()
                    file.parentFile?.mkdirs()
                    file.createNewFile()
                    file.appendText(username.text.toString() + ";" + Credenziali.split(";")[1])
                }
                catch (e: Exception) {
                    e.printStackTrace()
                }
                    if(autoLoginCheckText==true){
                        val auto = db.AutoLogin(true, "true", id_user)
                        if(auto == false){
                            println("l'update non è andato a buon fine")
                        }
                    }
                    else{
                        val auto = db.AutoLogin(true, "false", id_user)
                        if(auto == false){
                            println("l'update non è andato a buon fine")
                        }
                    }

                val intent = Intent(this, Homepage_User::class.java)
                intent.putExtra("name", username.text.toString())
                intent.putExtra("id_user", Credenziali.split(";")[1])
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


