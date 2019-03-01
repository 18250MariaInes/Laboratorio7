package com.example.maria.laboratorio7

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity//*************************
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_agregar_editar_contacto.*

class AgregarEditarContactoActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_ID = "com.example.maria.laboratorio7.EXTRA_ID"
        const val EXTRA_NOMBRE = "com.example.maria.laboratorio7.EXTRA_NOMBRE"
        const val EXTRA_NUMERO = "com.example.maria.laboratorio7.EXTRA_NUMERO"
        const val EXTRA_PRIORITY = "com.example.maria.laboratorio7.EXTRA_PRIORITY"
        const val EXTRA_CORREO = "com.example.maria.laboratorio7.EXTRA_CORREO"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_editar_contacto)

        number_picker_priority.minValue = 1
        number_picker_priority.maxValue = 10

        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close)

        if (intent.hasExtra(EXTRA_ID)){
            title="Editar contacto"
            edit_text_nombre.setText(intent.getStringExtra(EXTRA_NOMBRE))
            edit_text_numero.setText(intent.getStringExtra((EXTRA_NUMERO)))
            edit_text_correo.setText(intent.getStringExtra(EXTRA_CORREO))
            number_picker_priority.value = intent.getIntExtra(EXTRA_PRIORITY, 1)
        }else{
            title="Agregar contacto"
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.agregar_contacto_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId){
            R.id.save_note->{
                saveContact()
                true
            }
            else ->super.onOptionsItemSelected(item)
        }
    }
    private fun saveContact(){
        if (edit_text_nombre.text.toString().trim().isBlank()|| edit_text_correo.text.toString().trim().isBlank()||edit_text_numero.text.toString().trim().isBlank()){
            Toast.makeText(this, "No puede crear un contacto vacio", Toast.LENGTH_SHORT).show()
            return
        }
        val data= Intent().apply{
            putExtra(EXTRA_NOMBRE, edit_text_nombre.text.toString())
            putExtra(EXTRA_CORREO, edit_text_correo.text.toString())
            putExtra(EXTRA_NUMERO, edit_text_numero.text.toString())
            putExtra(EXTRA_PRIORITY, number_picker_priority.value)
            if (intent.getIntExtra(EXTRA_ID, -1)!=-1){
                putExtra(EXTRA_ID, intent.getIntExtra(EXTRA_ID, -1))
            }
        }
        setResult(Activity.RESULT_OK, data)
        finish()
    }

}
