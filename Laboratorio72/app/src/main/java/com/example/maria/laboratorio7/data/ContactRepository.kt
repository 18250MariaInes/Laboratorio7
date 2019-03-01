package com.example.maria.laboratorio7.data

import android.app.Application
import android.arch.lifecycle.LiveData
import android.os.AsyncTask

class ContactRepository(application: Application) {
    private var contactDao: ContactDao

    private var todosContactos: LiveData<List<Contact>>

    init {
        val database:ContactDatabase= ContactDatabase.getInstance(
            application.applicationContext
        )!!
        contactDao=database.contactDao()
        todosContactos=contactDao.getAllContacts()
    }

    fun insert (contact: Contact){
        val insertContactAsync=InsertContactAsyncTask(contactDao).execute(contact)
    }
    fun update (contact: Contact){
        val updateContactAsyncTask=UpdateContactAsyncTask(contactDao).execute(contact)
    }
    fun delete (contact: Contact){
        val deleteContactAsyncTask=DeleteContactAsyncTask(contactDao).execute(contact)
    }
    fun deleteAllContacts(){
        val deleteAllContactsAsyncTask=DeleteAllContactsAsyncTask(contactDao).execute()
    }
    fun getAllContacts():LiveData<List<Contact>>{
        return todosContactos
    }

    companion object {
         private class InsertContactAsyncTask(contactDao: ContactDao):AsyncTask<Contact, Unit, Unit>(){
             val contactDao=contactDao

             override fun doInBackground(vararg params: Contact?) {
                 contactDao.insert(params[0]!!)
             }
         }
        private class UpdateContactAsyncTask(contactDao: ContactDao):AsyncTask<Contact, Unit, Unit>(){
            val contactDao=contactDao

            override fun doInBackground(vararg params: Contact?) {
                contactDao.update(params[0]!!)
            }
        }
        private class DeleteContactAsyncTask(contactDao: ContactDao):AsyncTask<Contact, Unit, Unit>(){
            val contactDao=contactDao

            override fun doInBackground(vararg params: Contact?) {
                contactDao.delete(params[0]!!)
            }
        }
        private class DeleteAllContactsAsyncTask(contactDao: ContactDao):AsyncTask<Contact, Unit, Unit>(){
            val contactDao=contactDao

            override fun doInBackground(vararg params: Contact?) {
                contactDao.deleteAllContacts()
            }
        }

    }
}