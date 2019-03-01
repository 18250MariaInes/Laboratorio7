package com.example.maria.laboratorio7

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.widget.LinearLayout
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.maria.laboratorio7.ContactAdapter.ContactAdapter
import com.example.maria.laboratorio7.data.Contact
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        const val AGREGAR_CONTACTO_REQUEST=1
        const val EDITAR_CONTACTO_REQUEST=2
    }
    private lateinit var contactViewModel: ContactViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recycler_view.layoutManager=LinearLayout(this)
        recycler_view.setHasFixedsize(true)

        var adapter=ContactAdapter()

        recycler_view.adapter=adapter

        contactViewModel= ViewModelProviders.of(this).get(ContactViewModel::class.java)
        contactViewModel.getAllContacts().observe(this, Observer<List<Contact>>{
            adapter.submitList(it)
        })

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT.or(ItemTouchHelper.RIGHT)) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                contactViewModel.delete(adapter.getContactAt(viewHolder.adapterPosition))
                Toast.makeText(baseContext, "Contacto Eliminado!", Toast.LENGTH_SHORT).show()
            }
        }
        ).attachToRecyclerView(recycler_view)

    }
}
