package com.example.maria.laboratorio7.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "contact_table")
class Contact(
    var nombre:String,
    var correo:String,
    var numero:String,
    var prioridad:Int
) {
    @PrimaryKey (autoGenerate = true)
    var id:Int=0
}