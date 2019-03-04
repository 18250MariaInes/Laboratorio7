package com.example.maria.laboratorio7.data

import androidx.room.Entity
import androidx.room.PrimaryKey
//objeto contact el cual es el que se guarda y maneja en la base de datos
@Entity (tableName = "contact_table")
class Contact(
    var nombre:String,
    var correo:String,
    var numero:String,
    var prioridad:Int
) {
    @PrimaryKey (autoGenerate = true)
    var id:Int=0//su id se genera automaticamente
}