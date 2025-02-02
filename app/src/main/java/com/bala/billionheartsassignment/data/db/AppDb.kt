package com.bala.billionheartsassignment.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Tag::class], version = 1, exportSchema = false)
abstract class AppDb : RoomDatabase() {
    abstract fun getTagsDao(): TagsDao
}