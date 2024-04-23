package com.dicoding.asclepius

import android.content.Context
import androidx.lifecycle.asFlow
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.dicoding.asclepius.data.local.AppDao
import com.dicoding.asclepius.data.local.AppDatabase
import com.dicoding.asclepius.data.local.ClassificationResult
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import kotlin.random.Random

@RunWith(AndroidJUnit4::class)
class DatabaseTest {
    private lateinit var dao: AppDao
    private lateinit var database: AppDatabase

    private val imageUri = "image_uri"

    private val mockResults = listOf(
        ClassificationResult(1, Random.nextLong(), imageUri, "Cancer", 0.73f),
        ClassificationResult(2, Random.nextLong(), imageUri, "Non Cancer", 0.28f),
        ClassificationResult(3, Random.nextLong(), imageUri, "Non Cancer", 0.41f),
        ClassificationResult(4, Random.nextLong(), imageUri, "Cancer", 0.52f)
    )

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()

        dao = database.appDao()
    }

    @Throws(IOException::class)
    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun getEmptyDatabas() = runBlocking {
        val results = dao.getAll().asFlow().first()
        assertEquals(0, results.size)
    }

    @Test
    fun insertSingleEntity() = runBlocking {
        dao.insert(mockResults[0])
        val result = dao.getAll().asFlow().first()

        assertEquals(1, result.size)
        with(mockResults[0]) {
            assertEquals(timestamp, result.first().timestamp)
            assertEquals(label, result.first().label)
            assertEquals(score, result.first().score)
        }
    }

    @Test
    fun getEntityById() = runBlocking {
        dao.insert(*mockResults.toTypedArray())
        val result = dao.getClassificationResultById(3).asFlow().first()
        assertEquals(mockResults.firstOrNull { it.id == 3 }, result)
    }

    @Test
    fun deleteEntity() = runBlocking {
        dao.insert(*mockResults.toTypedArray())
        var result = dao.getAll().asFlow().first()
        assertEquals(mockResults.size, result.size)

        dao.delete(mockResults[2])
        result = dao.getAll().asFlow().first()
        assertEquals(mockResults.size - 1, result.size)
        assertTrue(!result.contains(mockResults[2]))
    }
}