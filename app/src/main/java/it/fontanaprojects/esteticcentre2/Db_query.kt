package it.fontanaprojects.esteticcentre2

import java.sql.ResultSet
import java.sql.Statement

class Db_query {


    fun checkLogin(username: String, pass: String?): String {
            val conn = Connessione().connetti()
            val stmt: Statement = conn!!.createStatement()
            var ris: ResultSet = stmt.executeQuery("SELECT id_user, username FROM credenziali " + "WHERE username = '$username' AND password = '$pass' ;")
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
                val id_user = ris.getString(1)
                Connessione().chiudiConn(conn)
                return "Authenticate;$id_user"
            }
            Connessione().chiudiConn(conn)
            return ""
    }

    fun searchClient(singol: Boolean, nameSearch: String?, id_user: String): MutableList<String>? {

        val conn = Connessione().connetti()
        val stmt: Statement = conn!!.createStatement()
        val informationClients : MutableList<String> = mutableListOf()
        var ris: ResultSet?

        if(singol == true){
            ris = stmt.executeQuery("SELECT id_cliente, data, nome, cognome FROM clienti WHERE nome LIKE '%$nameSearch%' OR cognome LIKE '%$nameSearch%' AND id_user = $id_user ORDER BY data DESC")
        }
        else{
            ris = stmt.executeQuery("SELECT id_cliente, data, nome, cognome FROM clienti WHERE id_user = $id_user ORDER BY data DESC")
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

    fun inserNewAdmin(username: String, pass: String) : Boolean {

        val conn = Connessione().connetti()
        val stmt: Statement = conn!!.createStatement()

        val ris = stmt.execute("INSERT INTO public.credenziali(username, password) VALUES ('$username', '$pass');")

        return if(ris) {
            Connessione().chiudiConn(conn)
            false
        } else {
            Connessione().chiudiConn(conn)
            true
        }
    }

    fun inserNewClient(data: String, cognome: String, nome: String, via: String, cap: String, citta: String, provincia: String, tel: String, eMail: String,
                       natoA: String, il: String, eta: String, professione: String, motivo: String, id_user: String) : Boolean {

        val conn = Connessione().connetti()
        val stmt: Statement = conn!!.createStatement()
        var tel1 = ""
        var eta1 = ""
        var cap1 = ""

        if(tel == "") {
            tel1 = "0"
        }
        else{
            tel1 = tel
        }
        if(eta == ""){
            eta1 = "0"
        }
        else{
            eta1 = eta
        }
        if(cap == ""){
            cap1 = "0"
        }
        else{
            cap1 = cap
        }

        val ris = stmt.execute("INSERT INTO public.clienti(id_user, nome, cognome, data, via, comune, provincia, tel, e_mail, nato_a, il, eta, professione, motivo, cap) VALUES ($id_user, '$nome', '$cognome', '$data', '$via', '$citta', '$provincia', ${tel1.trim()}, '$eMail', '$natoA', '$il', ${eta1.trim()}, '$professione', '$motivo', ${cap1.trim()});")

        return if(ris) {
            Connessione().chiudiConn(conn)
            false
        } else {
            Connessione().chiudiConn(conn)
            true
        }
    }

    fun AutoLogin(update:Boolean, auto: String?, id_user: String): Boolean {

        var result = ""
        val conn = Connessione().connetti()
        val stmt: Statement = conn!!.createStatement()

        var id_user = id_user
        if(id_user == ""){
            id_user = 0.toString()
        }

        if(update == true){
            val ris = stmt.execute("UPDATE credenziali SET auto = '$auto' WHERE id_user = ${id_user.trim()}")

            return if(ris) {
                Connessione().chiudiConn(conn)
                false
            } else {
                Connessione().chiudiConn(conn)
                true
            }
        }
        else{
            val ris = stmt.executeQuery("SELECT auto FROM credenziali WHERE id_user = ${id_user.trim()}")
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

    fun readPersonalData(idCliente: String): MutableList<String>? {

        val conn = Connessione().connetti()
        val stmt: Statement = conn!!.createStatement()
        val informationClients : MutableList<String> = mutableListOf()
        var ris: ResultSet?

        ris = stmt.executeQuery("SELECT nome, cognome, il, via, comune, provincia, tel, e_mail, professione FROM clienti WHERE id_cliente = $idCliente")
        if(ris.next()){
            val nome_cognome = ris.getString(1) + " " + ris.getString(2)
            val data_nascita = ris.getString(3)
            val indirizzo = ris.getString(4) + ", " + ris.getString(5) + ", " + ris.getString(6)
            var tel = ris.getString(7)
            val e_mail = ris.getString(8)
            val professione = ris.getString(9)

            informationClients.add(nome_cognome)
            informationClients.add(data_nascita)
            informationClients.add(indirizzo)
            if(tel == null){
                tel = "0"
            }
            informationClients.add(tel)
            informationClients.add(e_mail)
            informationClients.add(professione)

            return informationClients
        }
        else{
            return null
        }
    }

    fun inserNewAnalisi(
        id_ultimo_trattamento: Int, id_cliente: Int, professione: String, farmaci: String, patologie: String,
        allergie: String, colore: String, hobby_sport: String, guanti_domanda: Boolean,
        iporidrosi_domanda: Boolean, micosi_unghiale_domanda: Boolean,
        onicofobia_domanda: Boolean, strappare_cuticole_domanda: Boolean,
        ormonali_domanda: Boolean, consenso: Boolean, firma_digitale: String,
        analisi_letto: String, analisi_cuticole: String, superficie: String,
        architettura: String, foto1: String, foto2: String, foto3: String,
        foto4: String
    ) : Boolean {


        var checkInsertAnalisi = false

        var conn = Connessione().connetti()
        var stmt: Statement = conn!!.createStatement()
        var ris = stmt.execute("UPDATE public.clienti SET professione = '$professione' WHERE id_cliente = $id_cliente")

        if(ris) {
            Connessione().chiudiConn(conn)
            checkInsertAnalisi = false
        } else {
            Connessione().chiudiConn(conn)
            checkInsertAnalisi = true
        }

        conn = Connessione().connetti()
        stmt = conn!!.createStatement()

        ris = stmt.execute("INSERT INTO public.analisi_informativa_mani_piedi(id_trattamento, id_cliente, farmaci, patologie," +
                " allergie, colore, hobby_sport, guanti_domanda," +
                " iporidrosi_domanda, micosi_ungueale_domanda, onicofobia_domanda, strappare_cuticole_domanda," +
                " ormonali_domanda, consenso, firma_digitale) " +
                "VALUES (${id_ultimo_trattamento+1}, $id_cliente, '$farmaci', '$patologie', '$allergie', '$colore', '$hobby_sport'," +
                " $guanti_domanda, $iporidrosi_domanda, $micosi_unghiale_domanda, $onicofobia_domanda," +
                " $strappare_cuticole_domanda, $ormonali_domanda, $consenso, '$firma_digitale');")

        if(ris) {
            Connessione().chiudiConn(conn)
            checkInsertAnalisi = false
        } else {
            Connessione().chiudiConn(conn)
            checkInsertAnalisi = true
        }

        conn = Connessione().connetti()
        stmt = conn!!.createStatement()
        ris = stmt.execute("INSERT INTO public.analisi_visiva_mani_piedi(id_trattamento, id_cliente, analisi_letto," +
                " analisi_cuticole, superficie, architettura, foto1," +
                " foto2, foto3, foto4) " +
                "VALUES (${id_ultimo_trattamento+1}, $id_cliente, '$analisi_letto', '$analisi_cuticole', '$superficie', '$architettura'," +
                " '$foto1', '$foto2', '$foto3', '$foto4');")

        if(ris) {
            Connessione().chiudiConn(conn)
            checkInsertAnalisi = false
        } else {
            Connessione().chiudiConn(conn)
            checkInsertAnalisi = true
        }

        return checkInsertAnalisi
    }
/*
    fun leggoimmagineprova(): String? {

        val conn = Connessione().connetti()
        val stmt: Statement = conn!!.createStatement()
        val ris: ResultSet?

        ris = stmt.executeQuery("SELECT foto1 FROM public.analisi_visiva_mani_piedi WHERE id_cliente = 2")

        if(ris.next()){
            val image = ris.getString(1)
            Connessione().chiudiConn(conn)
            return image

        }
        else {
            Connessione().chiudiConn(conn)
            return null
        }

    }

 */

    fun takeLastIdTrattment(id_cliente: Int): Int? {

        val conn = Connessione().connetti()
        val stmt: Statement = conn!!.createStatement()
        val ris: ResultSet?

        ris = stmt.executeQuery("SELECT MAX(id_trattamento) FROM public.analisi_informativa_mani_piedi WHERE id_cliente = $id_cliente")

        if(ris.next()){
            val id = ris.getInt(1)
            Connessione().chiudiConn(conn)
            return id

        }
        else {
            Connessione().chiudiConn(conn)
            return null
        }

    }

    fun insertNewTecnica(
        id_ultimo_trattamento: Int, id_cliente: Int, data: String, trattamento: String, mediatore: String,
        base: String, gel: String, top: String, colore: String,
        deco: String, altro: String
    ) : Boolean {

        var checkInsertAnalisi = false

        val conn = Connessione().connetti()
        val stmt: Statement = conn!!.createStatement()

        val ris = stmt.execute("INSERT INTO public.scheda_tecnica_mani_piedi(id_trattamento, id_cliente, data, trattamento," +
                " base, mediatore, gel, top," +
                " colore, deco, altro) " +
                "VALUES (${id_ultimo_trattamento+1}, $id_cliente, '$data', '$trattamento', '$base', '$mediatore', '$gel'," +
                " '$top', '$colore', '$deco', '$altro');")

        if(ris) {
            Connessione().chiudiConn(conn)
            checkInsertAnalisi = false
        } else {
            Connessione().chiudiConn(conn)
            checkInsertAnalisi = true
        }

        return checkInsertAnalisi
    }

}