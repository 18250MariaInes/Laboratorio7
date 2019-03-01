package com.example.maria.laboratorio7

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.ItemTouchHelper
import android.view.Menu
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.maria.laboratorio7.ContactAdapter.ContactAdapter
import com.example.maria.laboratorio7.data.Contact
import com.example.maria.laboratorio7.viewmodels.ContactViewModel
import kotlinx.android.synthetic.main.activity_main.*
//errores en recycle view
class MainActivity : AppCompatActivity() {

    companion object {
        const val AGREGAR_CONTACTO_REQUEST=1
        const val EDITAR_CONTACTO_REQUEST=2
    }
    private lateinit var contactViewModel: ContactViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonAddNote.setOnClickListener{
            startActivityForResult(
                Intent(this, AgregarEditarContactoActivity::class.java), AGREGAR_CONTACTO_REQUEST
            )
        }
val recycler_view: androidx.recyclerview.widget.RecyclerView =findViewById(R.id.recycler_view)
        recycler_view.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        recycler_view.setHasFixedSize(true)

        var adapter=ContactAdapter()

        recycler_view.adapter=adapter

        contactViewModel = ViewModelProviders.of(this).get(ContactViewModel::class.java)
        contactViewModel.getAllContacts().observe(this, Observer<List<Contact>>{
            adapter.submitList(it)
        })

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT.or(ItemTouchHelper.RIGHT)) {
            override fun onMove(
                recyclerView: androidx.recyclerview.widget.RecyclerView,
                viewHolder: androidx.recyclerview.widget.RecyclerView.ViewHolder,
                target: androidx.recyclerview.widget.RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: androidx.recyclerview.widget.RecyclerView.ViewHolder, direction: Int) {
                contactViewModel.delete(adapter.getContactAt(viewHolder.adapterPosition))
                Toast.makeText(baseContext, "Contacto Eliminado!", Toast.LENGTH_SHORT).show()
            }
        }
        ).attachToRecyclerView(recycler_view)

        adapter.setOnItemClickListener(object :ContactAdapter.onItemClickListener{
            override fun onItemClick(contact: Contact){
                var intent=Intent(baseContext, AgregarEditarContactoActivity::class.java)
                intent.putExtra(AgregarEditarContactoActivity.EXTRA_ID, contact.id)
                intent.putExtra(AgregarEditarContactoActivity.EXTRA_NOMBRE, contact.nombre)
                intent.putExtra(AgregarEditarContactoActivity.EXTRA_NUMERO, contact.numero)
                intent.putExtra(AgregarEditarContactoActivity.EXTRA_CORREO, contact.correo)
                intent.putExtra(AgregarEditarContactoActivity.EXTRA_PRIORITY, contact.prioridad)
                startActivityForResult(intent, EDITAR_CONTACTO_REQUEST)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.mainmenu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId){
            R.id.delete_all_notes->{
                contactViewModel.deleteAllContacts()
                Toast.makeText(this, "Todos los contactos han sido borrados", Toast.LENGTH_SHORT).show()
                true
            }
            else ->{
                super.onOptionsItemSelected(item)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode== AGREGAR_CONTACTO_REQUEST && resultCode== Activity.RESULT_OK){
            val newContact=Contact(
                data!!.getStringExtra(AgregarEditarContactoActivity.EXTRA_NOMBRE),
                data.getStringExtra(AgregarEditarContactoActivity.EXTRA_CORREO),
                data.getStringExtra(AgregarEditarContactoActivity.EXTRA_NUMERO),
                data.getIntExtra(AgregarEditarContactoActivity.EXTRA_PRIORITY, 1)
            )
            contactViewModel.insert(newContact)
            Toast.makeText(this, "Contacto guardado", Toast.LENGTH_SHORT).show()
        }else if (requestCode == EDITAR_CONTACTO_REQUEST && resultCode == Activity.RESULT_OK){
            val id=data?.getIntExtra(AgregarEditarContactoActivity.EXTRA_ID, -1)

            if (id==-1){
                Toast.makeText(this, "No se logro editar", Toast.LENGTH_SHORT).show()
            }

            val actContact=Contact(
                data!!.getStringExtra(AgregarEditarContactoActivity.EXTRA_NOMBRE),
                data.getStringExtra(AgregarEditarContactoActivity.EXTRA_CORREO),
                data.getStringExtra(AgregarEditarContactoActivity.EXTRA_NUMERO),
                data.getIntExtra(AgregarEditarContactoActivity.EXTRA_PRIORITY, 1)
            )
            actContact.id=data.getIntExtra(AgregarEditarContactoActivity.EXTRA_ID, -1)
            contactViewModel.update(actContact)
        }else{
            Toast.makeText(this, "ERROR NO SE PUDO GUARDAR", Toast.LENGTH_SHORT).show()
        }
    }
}
