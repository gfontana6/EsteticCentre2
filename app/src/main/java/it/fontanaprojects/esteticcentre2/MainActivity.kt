package it.fontanaprojects.esteticcentre2

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()

        val mRootRef: DatabaseReference = FirebaseDatabase.getInstance().reference
        val db = Db_query()

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
            val Credenziali = db.CheckLogin(this, username.text.toString(), encode(password.text.toString()))
            if(Credenziali == "Authenticate"){
                val intent = Intent(this, Homepage_User::class.java)
                intent.putExtra("name", username.text.toString())
                startActivity(intent)
            }

        }
    }

    override fun onBackPressed() {
        Toast.makeText(this, "Non puoi tornare indietro dalla schermata di Login per motivi di sicurezza", Toast.LENGTH_SHORT).show()
    }


    fun encode(s: String): String {
        // create a string to add in the initial
        // binary code for extra security
        val ini = "11111111"
        var cu = 0

        // create an array
        val arr = IntArray(11111111)

        // iterate through the string
        for (i in 0 until s.length) {
            // put the ascii value of
            // each character in the array
            arr[i] = s[i].code
            cu++
        }
        var res = ""

        // create another array
        val bin = IntArray(111)
        var idx = 0

        // run a loop of the size of string
        for (i1 in 0 until cu) {

            // get the ascii value at position
            // i1 from the first array
            var temp = arr[i1]

            // run the second nested loop of same size
            // and set 0 value in the second array
            for (j in 0 until cu) bin[j] = 0
            idx = 0

            // run a while for temp > 0
            while (temp > 0) {
                // store the temp module
                // of 2 in the 2nd array
                bin[idx++] = temp % 2
                temp = temp / 2
            }
            var dig = ""
            var temps: String

            // run a loop of size 7
            for (j in 0..6) {

                // convert the integer to string
                temps = Integer.toString(bin[j])

                // add the string using
                // concatenation function
                dig = dig + temps
            }
            var revs = ""

            // reverse the string
            for (j in dig.length - 1 downTo 0) {
                val ca = dig[j]
                revs = revs + ca.toString()
            }
            res = res + revs
        }
        // add the extra string to the binary code
        res = ini + res

        // return the encrypted code
        return res
    }

}


