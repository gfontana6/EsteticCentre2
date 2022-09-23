package it.fontanaprojects.esteticcentre2

import android.app.Dialog
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.setPadding
import kotlinx.android.synthetic.main.homepage_user_layout.*

class Homepage_User: AppCompatActivity() {

    private val db = Db_query()
    private var admin: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.homepage_user_layout)

        supportActionBar?.hide()

        admin = intent.getStringExtra("name")
        nameAdmin.text = "Salve " + admin

        TableClients.background = ResourcesCompat.getDrawable(resources, R.drawable.border, null)

        showAllClients()

        btn_search_client.setOnClickListener{
            if(nameSearchclient.text.toString() == "" || nameSearchclient.text.toString() == null){
                TableClients.removeAllViews()
                showAllClients()
            }
            else {
                val research = db.searchClient(true, nameSearchclient.text.toString())
                if (research == null) {
                    Toast.makeText(this, "Cliente desiderato non trovato", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    val textView = TextView(this)
                    TableClients.removeAllViews()
                    val namesClientSplitted = research.get(0).split(";")
                    textView.text = namesClientSplitted[1]
                    textView.setPadding(3)
                    textView.setTextSize(15F)
                    textView.background =
                        ResourcesCompat.getDrawable(resources, R.drawable.border, null)
                    textView.setTypeface(null, Typeface.BOLD_ITALIC)
                    textView.isClickable = true

                    textView.setOnClickListener {

                        val TextTextViewSplitted = research.get(0).split(" - ")
                        val nameFromTextView = TextTextViewSplitted[1]

                        val intent = Intent(this, ManageClient::class.java)
                        intent.putExtra("nameClientIntent", nameFromTextView)
                        intent.putExtra("id_cliente", namesClientSplitted[0])
                        intent.putExtra("name", admin)
                        startActivity(intent)

                    }

                    textView.setTextColor(ContextCompat.getColor(this, R.color.blue_link))
                    TableClients.addView(textView)
                }
            }
        }

        logoutText.setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java))
        }

        addClient.setOnClickListener{
            val dialoginsert : Dialog = dialoginsertclient(this)
            dialoginsert.show()
        }

    }

    override fun onBackPressed() {
        startActivity(Intent(this, MainActivity::class.java))
    }

    fun showAllClients(){
        val namesClient = db.searchClient(false, null)
        if(namesClient != null){
            var i = 0
            while(i < namesClient.size){
                val textView = TextView(this)
                val namesClientSplitted = namesClient.get(i).split(";")
                textView.text = namesClientSplitted[1]
                textView.setPadding(3)
                textView.setTextSize(15F)
                textView.background = ResourcesCompat.getDrawable(resources, R.drawable.border, null)
                textView.setTypeface(null, Typeface.BOLD_ITALIC)
                textView.isClickable = true

                textView.setOnClickListener{

                    val nameClientResultSplitted = textView.text.toString().split(" - ")

                    val intent = Intent(this, ManageClient::class.java)
                    intent.putExtra("nameClientIntent", nameClientResultSplitted[1])
                    intent.putExtra("id_cliente", namesClientSplitted[0])
                    intent.putExtra("name", admin)
                    startActivity(intent)

                }

                textView.setTextColor(ContextCompat.getColor(this, R.color.blue_link))
                TableClients.addView(textView)
                i++
            }
        }
        else {
            TableClients.removeAllViews()
            val textView = TextView(this)
            textView.text = "Nessun cliente trovato"
            textView.setPadding(3)
            textView.setTextSize(15F)
            textView.background = ResourcesCompat.getDrawable(resources, R.drawable.border, null)
            textView.setTypeface(null, Typeface.BOLD_ITALIC)
            textView.setTextColor(ContextCompat.getColor(this, R.color.black))
            TableClients.addView(textView)

        }
    }

}
