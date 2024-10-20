package com.bala.billionheartsassignment.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface TagsDao {
    @Insert
    suspend fun addTag(tag: Tag)

    @Update
    suspend fun updateTag(tag: Tag)

    @Delete
    suspend fun deleteTag(tag: Tag)

//    @Query("Select * from Tag where imgPath = :imgPath")
//    suspend fun getTag(imgPath: String): Tag

    @Query("Select * from tags")
    suspend fun getTagsList(): List<Tag>

}