package com.example.maria.laboratorio7.data

import androidx.lifecycle.LiveData
import androidx.room.*
//Data access object
//Maneja la informacion de los contactos por metodos CRUD
@Dao
interface ContactDao {
    @Insert
    fun insert(contact: Contact)

    @Update
    fun update(contact: Contact)

    @Delete
    fun delete(contact: Contact)

    @Query ("DELETE FROM contact_table")
    fun deleteAllContacts()

    @Query ("SELECT * FROM contact_table ORDER BY prioridad DESC")
    fun getAllContacts():LiveData<List<Contact>>
}