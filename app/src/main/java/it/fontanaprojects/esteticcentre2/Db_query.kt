package it.fontanaprojects.esteticcentre2

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.google.firebase.database.*

class Db_query {

    private val mRootRef: DatabaseReference = FirebaseDatabase.getInstance().reference

    fun CheckLogin(context: Context, username: String, pass: String): String?{

        var check: String? = null
        val mConditionRef = mRootRef.child("credenziali")
        mConditionRef.orderByChild("username").equalTo(username)
            .addListenerForSingleValueEvent(
                object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        if (dataSnapshot.exists()) {
                            mConditionRef.orderByChild("password").equalTo(pass)
                                .addListenerForSingleValueEvent(
                                    object : ValueEventListener {
                                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                                            if (dataSnapshot.exists()) {
                                                val passwordcheck: String = dataSnapshot.getValue(String.class)
                                                if(decode(dataSnapshot.getValue(String.class)) == pass)
                                                check = "Authenticate"

                                            } else {
                                                Toast.makeText(context, "Password errata", Toast.LENGTH_SHORT).show()
                                            }
                                        }

                                        override fun onCancelled(databaseError: DatabaseError) {}
                                    }
                                )
                        } else {
                            Toast.makeText(context, "Username errato o inesistente", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onCancelled(databaseError: DatabaseError) {}
                }
            )
        return check
    }

    fun decode(s: String): String {
        val invalid = "Invalid Code"

        // create the same initial
        // string as in encode class
        val ini = "11111111"
        var flag = true

        // run a loop of size 8
        for (i in 0..7) {
            // check if the initial value is same
            if (ini[i] != s[i]) {
                flag = false
                break
            }
        }
        var `val` = ""

        // reverse the encrypted code
        for (i in 8 until s.length) {
            val ch = s[i]
            `val` = `val` + ch.toString()
        }

        // create a 2 dimensional array
        val arr = Array(11101) {
            IntArray(
                8
            )
        }
        var ind1 = -1
        var ind2 = 0

        // run a loop of size of the encrypted code
        for (i in 0 until `val`.length) {

            // check if the position of the
            // string if divisible by 7
            if (i % 7 == 0) {
                // start the value in other
                // column of the 2D array
                ind1++
                ind2 = 0
                val ch = `val`[i]
                arr[ind1][ind2] = ch - '0'
                ind2++
            } else {
                // otherwise store the value
                // in the same column
                val ch = `val`[i]
                arr[ind1][ind2] = ch - '0'
                ind2++
            }
        }
        // create an array
        val num = IntArray(11111)
        var nind = 0
        var tem = 0
        var cu = 0

        // run a loop of size of the column
        for (i in 0..ind1) {
            cu = 0
            tem = 0
            // convert binary to decimal and add them
            // from each column and store in the array
            for (j in 6 downTo 0) {
                val tem1 = Math.pow(2.0, cu.toDouble()).toInt()
                tem += arr[i][j] * tem1
                cu++
            }
            num[nind++] = tem
        }
        var ret = ""
        var ch: Char
        // convert the decimal ascii number to its
        // char value and add them to form a decrypted
        // string using conception function
        for (i in 0 until nind) {
            ch = num[i].toChar()
            ret = ret + ch.toString()
        }
        Log.e("dec", "text 11 - $ret")

        // check if the encrypted code was
        // generated for this algorithm
        return if (`val`.length % 7 == 0 && flag == true) {
            // return the decrypted code
            ret
        } else {
            // otherwise return an invalid message
            invalid
        }
    }

}