package com.example.maria.laboratorio7.ContactAdapter

import android.os.Parcel
import android.os.Parcelable
import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView

import android.widget.TextView
import com.example.maria.laboratorio7.R
import com.example.maria.laboratorio7.data.Contact
import kotlinx.android.synthetic.main.contact_item.view.*

class ContactAdapter : ListAdapter<Contact, ContactAdapter.ContactHolder>(DIFF_CALLBACK) {
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


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):ContactHolder{
        val itemView:View=LayoutInflater.from(parent.context).inflate(R.layout.contact_item, parent, false)
        return ContactHolder(itemView)
    }

    override fun onBindViewHolder(holder: ContactHolder, position: Int){
        val currentContact:Contact=getItem(position)

        holder.contactViewNombre.text=currentContact.nombre
        holder.contactViewPrioridad.text=currentContact.prioridad.toString()
        holder.contactViewNumero.text=currentContact.numero
    }
    fun getContactAt(position: Int):Contact{
        return getItem(position)
    }

    inner class ContactHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        init {
            itemView.setOnClickListener{
                val position=adapterPosition
                if (position!=RecyclerView.NO_POSITION) {
                    listener?.onItemClick(getItem(position))
                }
            }
        }
        var contactViewNombre:TextView=itemView.contact_nombre
        var contactViewPrioridad:TextView=itemView.contact_priority
        var contactViewNumero:TextView=itemView.contact_number
    }

    interface onItemClickListener{
        fun onItemClick(contact: Contact)
    }
    fun setOnItemClickListener(listener: onItemClickListener){
        this.listener=listener
    }
}