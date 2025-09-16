package com.example.homegenie.di

import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object GenieModule {

    @Singleton
    @Provides
    fun providesFirestoreDb(): FirebaseFirestore = Firebase.firestore

//    @Provides
//    @Singleton
//    fun providesErazorDao(erazorDatabase: ErazorDatabase): ErazorDao {
//        return erazorDatabase.erazorDao()
//    }
//
//    @Provides
//    @Singleton
//    fun providesErazorDatabase(@ApplicationContext appContext: Context): ErazorDatabase {
//        return Room.databaseBuilder(
//            appContext,
//            ErazorDatabase::class.java,
//            "erazor_database"
//        ).build()
//    }

}
