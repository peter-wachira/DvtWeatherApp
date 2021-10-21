package com.zalocoders.dvtweatherapp.db

import androidx.room.*
import com.zalocoders.dvtweatherapp.data.Favourite
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouriteLocationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavouriteLocation(favourite:Favourite)

    @Query("SELECT * FROM favourite_locations")
    fun getAllFavouriteLocation(): Flow<List<Favourite>>

    @Delete
    suspend fun deleteFavouriteLocation(favourite: Favourite)

    @Query("DELETE FROM favourite_locations")
    suspend fun deleteAllFavouriteLocations()

}