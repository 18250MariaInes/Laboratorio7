package com.example.maria.laboratorio7

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
//clase que se encarga de mostrar contacto y brinda la opcion de llamar o mandar mensaje
class MostrarContacto : AppCompatActivity() {
    lateinit var  txtcorreo: TextView
    lateinit var txtnumero: TextView
    lateinit var txtnombre: TextView
    lateinit var txtprioridad:TextView
    companion object {
        const val EXTRA_ID = "com.example.maria.laboratorio7.EXTRA_ID"
        const val EXTRA_NOMBRE = "com.example.maria.laboratorio7.EXTRA_NOMBRE"
        const val EXTRA_NUMERO = "com.example.maria.laboratorio7.EXTRA_NUMERO"
        const val EXTRA_PRIORITY = "com.example.maria.laboratorio7.EXTRA_PRIORITY"
        const val EXTRA_CORREO = "com.example.maria.laboratorio7.EXTRA_CORREO"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mostrar_contacto)
        txtnombre=findViewById(R.id.NombreC)
        txtnumero=findViewById(R.id.NumeroC)
        txtcorreo=findViewById(R.id.CorreoC)
        txtprioridad=findViewById(R.id.prioridadC)
        val numero=intent.getStringExtra(EXTRA_NUMERO)
        val correo=intent.getStringExtra(EXTRA_CORREO)

        txtnombre.setText(intent.getStringExtra(EXTRA_NOMBRE))
        txtnumero.setText(intent.getStringExtra(EXTRA_NUMERO))
        txtcorreo.setText(intent.getStringExtra(EXTRA_CORREO))
        txtprioridad.setText(intent.getStringExtra(EXTRA_PRIORITY))
//realizar llamada al seleccionar el numero de telefono
        txtnumero.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:"+numero)
            startActivity(intent)
        }
        //al hacer click en el correo se redirige a mandar un correo por medio de un broadcast receiver
        txtcorreo.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO)
            intent.type = "text/html"
            intent.data= Uri.parse("mailto:"+correo)
            intent.putExtra(Intent.EXTRA_EMAIL,correo)
            intent.putExtra(Intent.EXTRA_SUBJECT, "Prueba Android")
            intent.putExtra(Intent.EXTRA_TEXT, "Mi nombre es Maria Ines y mi numero es 30246448")

            startActivity(Intent.createChooser(intent, "Send Email"))
            intent.setAction("com.example.maria.laboratorio7.CUSTOM_INTENT");
            sendBroadcast(intent)

        }
    }
    fun regresarmenu (view: View){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
    /*fun editar (view: View){
        val numero=intent.getStringExtra(EXTRA_NUMERO)
        val correo=intent.getStringExtra(EXTRA_CORREO)
        val nombre=intent.getStringExtra(EXTRA_NOMBRE)
        val prioridad=intent.getStringExtra(EXTRA_PRIORITY)
        val id=intent.getStringExtra(EXTRA_ID)
        val intent=Intent(baseContext, AgregarEditarContactoActivity::class.java)
        intent.putExtra(AgregarEditarContactoActivity.EXTRA_NUMERO, numero)
        intent.putExtra(AgregarEditarContactoActivity.EXTRA_NOMBRE, nombre)
        intent.putExtra(AgregarEditarContactoActivity.EXTRA_CORREO, correo)
        intent.putExtra(AgregarEditarContactoActivity.EXTRA_PRIORITY, prioridad)
        intent.putExtra(AgregarEditarContactoActivity.EXTRA_ID, id)
        startActivityForResult(intent,-1)
    }*/
}
