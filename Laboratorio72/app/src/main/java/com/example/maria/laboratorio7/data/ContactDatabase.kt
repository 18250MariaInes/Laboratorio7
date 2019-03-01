package com.example.maria.laboratorio7.data

import android.content.Context
import android.os.AsyncTask
import androidx.room.Database
import androidx.room.Room
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

@Database (entities = [Contact::class], version = 1)
abstract class ContactDatabase : RoomDatabase(){

    abstract fun contactDao(): ContactDao

    companion object {
        private var instance:ContactDatabase?=null

        fun getInstance(context: Context):ContactDatabase?{
            if (instance==null){
                synchronized(ContactDatabase::class){
                    instance= Room.databaseBuilder(
                        context.applicationContext,
                        ContactDatabase::class.java, "contact_database"
                    ).fallbackToDestructiveMigration()
                        .addCallback(roomCallback)
                        .build()
                }
            }
            return instance
        }

        fun  destroyInstance(){
            instance=null
        }

        private val roomCallback =object :RoomDatabase.Callback(){
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                PopulateDbAsyncTask(instance).execute()
            }
        }
    }

    class PopulateDbAsyncTask (db:ContactDatabase?):AsyncTask<Unit, Unit, Unit>(){
        private val contactDao=db?.contactDao()

        override fun doInBackground(vararg param: Unit?) {
            contactDao?.insert((Contact("Juan", "juancvs@gmail.com", "59077176", 3 )))
           }
    }
}