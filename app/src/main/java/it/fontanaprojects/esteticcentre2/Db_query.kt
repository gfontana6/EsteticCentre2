package it.fontanaprojects.esteticcentre2

import java.sql.Connection
import java.sql.ResultSet
import java.sql.Statement

class Db_query(connection: Connection?) {

    private var conn: Connection? = connection

    fun checkLogin(username: String, pass: String?): String? {

        val stmt: Statement = conn!!.createStatement()
        var ris: ResultSet = stmt.executeQuery("SELECT username FROM credenziali " + "WHERE username = '$username' AND password = '$pass' ;")
        if (!ris.next()) {
            ris = stmt.executeQuery("SELECT username FROM credenziali " + "WHERE username = '$username' ;")
            if (!ris.next()) {
                return "Username inesistente o errato"
            } else {
                val ris2: ResultSet = stmt.executeQuery(
                    "SELECT password FROM credenziali " + "WHERE username = '$username' AND password = '$pass' ;"
                )
                if (!(ris2.next())) {
                    return "password errata"
                }
            }
        } else {
            return "Authenticate"
        }
        return null
    }



}