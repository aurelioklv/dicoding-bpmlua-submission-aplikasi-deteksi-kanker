package com.dicoding.asclepius.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AppDao {
    @Query("SELECT * FROM classificationresult ORDER BY timestamp DESC")
    fun getAll(): LiveData<List<ClassificationResult>>

    @Query("SELECT * FROM classificationresult WHERE id = :id")
    fun getClassificationResultById(id: Int): LiveData<ClassificationResult>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg classificationResults: ClassificationResult)

    @Delete
    suspend fun delete(classificationResult: ClassificationResult)
}