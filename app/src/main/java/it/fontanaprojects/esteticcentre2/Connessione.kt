package it.fontanaprojects.esteticcentre2

import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

class Connessione() {
    private val host = "ec2-34-249-236-155.eu-west-1.compute.amazonaws.com"
    private val database = "d5ledenupga0hs"
    private val port = "5432"
    private val user = "ylijlhmxcjjzzp"
    private val pass = "ebcdd6f1312459645a3736315d09f9793fb5cb62e880f439db45ec92cc5ae1d5"
    private var url = "jdbc:postgresql://$host:$port/$database?sslmode=require"

    fun connetti(): Connection? {
            Class.forName("org.postgresql.Driver") /*Inizializzo il Driver giusto per PostgreSQL*/
            var conn: Connection? = DriverManager.getConnection(url, user, pass) /*Connessione al database*/
            println("Connesso al database")

        return conn
    }

    fun chiudiConn(conn: Connection) {
        try {
            conn.close()
        } catch (e: SQLException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }
    }
}