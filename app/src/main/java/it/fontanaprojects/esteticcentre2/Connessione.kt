package it.fontanaprojects.esteticcentre2

import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

class Connessione() {
    private val host = "ec2-34-247-72-29.eu-west-1.compute.amazonaws.com"
    private val database = "dcegcrscdr84ae"
    private val port = 5432
    private val user = "bubqzmnmhdnqhr"
    private val pass = "90e7b061f55ecff165560d1fac71914f389628046e39a16265f9cb1f24280b65"
    private var url = "jdbc:postgresql://ec2-34-247-72-29.eu-west-1.compute.amazonaws.com:5432/dcegcrscdr84ae?sslmode=require"

    var conn: Connection? = null

    fun connetti(): Connection? {
            Class.forName("org.postgresql.Driver") /*Inizializzo il Driver giusto per PostgreSQL*/
            conn = DriverManager.getConnection(url, user, pass) /*Connessione al database*/
            println("Connesso al database")

        return conn
    }

    fun chiudiConn() {
        try {
            conn!!.close()
        } catch (e: SQLException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }
    }
}