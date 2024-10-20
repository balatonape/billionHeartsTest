package com.bala.billionheartsassignment.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


// for now added for single image tag we can extend for supporting multiple tags
@Entity(tableName = "tags")
data class Tag(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int?,
    @ColumnInfo(name = "img_path") val imgPath: String,
    @ColumnInfo(name = "name") val name: String
)
