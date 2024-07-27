package com.example.flexapp.data

import kotlinx.coroutines.flow.Flow

class ProgressRepository(
    private val progressItemDao: ProgressItemDao
)
{


    suspend fun insertProgressItem(progressItem: ProgressItem): Long =
        progressItemDao.insertProgressItem(progressItem)

    suspend fun deleteProgressItem(progressItem: ProgressItem) =
        progressItemDao.deleteProgressItem(progressItem)


    fun getAllProgressItems(): Flow<List<ProgressItem>> =
        progressItemDao.getAllProgressItems()


    fun getProgressItemById(progressItemId: Long): Flow<ProgressItem?> =
        progressItemDao.getProgressItemById(progressItemId)

    //progress item Data point queries

    suspend fun insertDataPoint(progressDataPoint: ProgressDataPoint) =
        progressItemDao.insertDataPoint(progressDataPoint)


    suspend fun deleteDataPoint(progressDataPoint: ProgressDataPoint) =
        progressItemDao.deleteDataPoint(progressDataPoint)


    fun getDataPointsByProgressItemId(progressItemId: Long): Flow<List<ProgressDataPoint>> =
        progressItemDao.getDataPointsByProgressItemId(progressItemId)


    fun getDataPointById(dataId: Long): Flow<ProgressDataPoint?> =
        progressItemDao.getDataPointById(dataId)

}