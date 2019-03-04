package com.example.maria.laboratorio7.ContactAdapter

import android.os.Parcel
import android.os.Parcelable
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView

import android.widget.TextView
import com.example.maria.laboratorio7.R
import com.example.maria.laboratorio7.data.Contact
import kotlinx.android.synthetic.main.contact_item.view.*
import com.example.maria.laboratorio7.ContactAdapter.ContactAdapter.ItemLongClickListener


//adapter de contactos que se utiliza en el main para su visualizacion e interaccion
class ContactAdapter : ListAdapter<Contact, ContactAdapter.ContactHolder>(DIFF_CALLBACK), View.OnLongClickListener {
    companion object {
        private val DIFF_CALLBACK=object :DiffUtil.ItemCallback<Contact>(){
            override fun areItemsTheSame(p0: Contact, p1: Contact): Boolean {
                return p0.id==p1.id
            }

            override fun areContentsTheSame(p0: Contact, p1: Contact): Boolean {
                return p0.nombre==p1.nombre && p0.correo==p1.correo && p0.numero==p1.numero &&
                        p0.prioridad==p1.prioridad
            }
        }
    }

    private var listener:onItemClickListener?=null
    private var itemLongClickListener: ItemLongClickListener?=null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):ContactHolder{
        val itemView:View=LayoutInflater.from(parent.context).inflate(R.layout.contact_item, parent, false)
        return ContactHolder(itemView)
    }
    //metodos para mostrar en el main su nombre, numero y prioridad
    override fun onBindViewHolder(holder: ContactHolder, position: Int){
        val currentContact:Contact=getItem(position)
        holder.contactViewNombre.text=currentContact.nombre
        holder.contactViewPrioridad.text=currentContact.prioridad.toString()
        holder.contactViewNumero.text=currentContact.numero
    }
    fun getContactAt(position: Int):Contact{
        return getItem(position)
    }

    inner class ContactHolder(itemView: View): androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView){
        init {
            itemView.setOnClickListener{
                val position=adapterPosition
                if (position!= androidx.recyclerview.widget.RecyclerView.NO_POSITION) {
                    listener?.onItemClick(getItem(position))
                }
            }
        }
        var contactViewNombre:TextView=itemView.contact_nombre
        var contactViewPrioridad:TextView=itemView.contact_priority
        var contactViewNumero:TextView=itemView.contact_number
    }
//metodos para su seleccion en el recycleview
    interface onItemClickListener{
        fun onItemClick(contact: Contact)
    }
    interface ItemLongClickListener{
        fun onLongItemClick(view: View)
    }

    fun setItemLongClickListener(ic: ItemLongClickListener) {
        this.itemLongClickListener = ic
    }

    override fun onLongClick(v: View): Boolean {
        this.itemLongClickListener!!.onLongItemClick(v)
        return false
    }
    fun setOnItemClickListener(listener: onItemClickListener){
        this.listener=listener
    }
}