package it.fontanaprojects.esteticcentre2

import java.sql.ResultSet
import java.sql.Statement

class Db_query {


    fun checkLogin(username: String, pass: String?): String? {

        val conn = Connessione().connetti()
        val stmt: Statement = conn!!.createStatement()
        var ris: ResultSet = stmt.executeQuery("SELECT username FROM credenziali " + "WHERE username = '$username' AND password = '$pass' ;")
        if (!ris.next()) {
            ris = stmt.executeQuery("SELECT username FROM credenziali " + "WHERE username = '$username' ;")
            if (!ris.next()) {
                Connessione().chiudiConn(conn)
                return "Username inesistente o errato"
            } else {
                val ris2: ResultSet = stmt.executeQuery("SELECT password FROM credenziali " + "WHERE username = '$username' AND password = '$pass' ;")
                if (!(ris2.next())) {
                    Connessione().chiudiConn(conn)
                    return "password errata"
                }
            }
        } else {
            Connessione().chiudiConn(conn)
            return "Authenticate"
        }
        Connessione().chiudiConn(conn)
        return null
    }

    fun searchClient(singol: Boolean, nameSearch: String?): MutableList<String>? {

        val conn = Connessione().connetti()
        val stmt: Statement = conn!!.createStatement()
        val informationClients : MutableList<String> = mutableListOf()
        var ris: ResultSet?

        if(singol == true){
            ris = stmt.executeQuery("SELECT id_cliente, data, nome, cognome FROM clienti WHERE nome LIKE '%$nameSearch%' OR cognome LIKE '%$nameSearch%'")
        }
        else{
            ris = stmt.executeQuery("SELECT id_cliente, data, nome, cognome FROM clienti ORDER BY data DESC")
        }
        if(ris.next()){
            var singolclient = ris.getString(1) + ";" + ris.getString(2) + " - " + ris.getString(3) + " " + ris.getString(4)
            informationClients.add(singolclient)

            while(ris.next()){
                singolclient = ris.getString(1) + ";" + ris.getString(2) + " - " + ris.getString(3) + " " + ris.getString(4)
                informationClients.add(singolclient)
            }

            Connessione().chiudiConn(conn)
            return informationClients

        }
        else {
            Connessione().chiudiConn(conn)
            return null
        }
    }

    fun inserNewClient(data: String, cognome: String, nome: String, via: String, cap: String, citta: String, provincia: String, tel: String,
                       eMail: String, natoA: String, il: String, eta: String, professione: String, motivo: String) : Boolean {

        val conn = Connessione().connetti()
        val stmt: Statement = conn!!.createStatement()

        val ris = stmt.execute("INSERT INTO public.clienti(nome, cognome, data, via, comune, provincia, tel, e_mail, nato_a, il, eta, professione, motivo, cap) VALUES ('$nome', '$cognome', '$data', '$via', '$citta', '$provincia', ${tel.trim()}, '$eMail', '$natoA', '$il', ${eta.trim()}, '$professione', '$motivo', ${cap.trim()});")

        return if(ris) {
            Connessione().chiudiConn(conn)
            false
        } else {
            Connessione().chiudiConn(conn)
            true
        }
    }

    fun AutoLogin(update:Boolean, auto: String?, username: String, pass: String?): Boolean {

        val conn = Connessione().connetti()
        val stmt: Statement = conn!!.createStatement()

        if(update == true){
            val ris = stmt.execute("UPDATE TABLE credenziali SET auto = '$auto' WHERE username = '$username' AND password = '$pass'")

            return if(ris) {
                Connessione().chiudiConn(conn)
                false
            } else {
                Connessione().chiudiConn(conn)
                true
            }
        }
        else{
            val ris = stmt.executeQuery("SELECT auto FROM credenziali WHERE username = '$username'")
            if(ris.next()){
                return if(ris.getString(1) == "true") {
                    Connessione().chiudiConn(conn)
                    true
                } else {
                    Connessione().chiudiConn(conn)
                    false
                }
            }
        }
        return false
    }

}