package com.example.homegenie.di

import com.example.homegenie.repositories.GenieRepository
import com.example.homegenie.repositories.GenieRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


@Module
@InstallIn(ViewModelComponent::class)
abstract class GenieRepositoryModule {

 @Binds
 abstract fun providesCustomerRepository(
  repo: GenieRepositoryImpl
 ): GenieRepository

// @Binds
// abstract fun providesRoomRepository(
//  repo: RoomRepositoryImpl
// ): RoomRepository


}
