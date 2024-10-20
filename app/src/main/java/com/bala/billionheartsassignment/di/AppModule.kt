package com.bala.billionheartsassignment.di

import android.content.ContentResolver
import android.content.Context
import androidx.room.Room
import com.bala.billionheartsassignment.data.db.AppDb
import com.bala.billionheartsassignment.data.db.TagsDao
import com.google.mediapipe.tasks.core.BaseOptions
import com.google.mediapipe.tasks.core.Delegate
import com.google.mediapipe.tasks.vision.core.RunningMode
import com.google.mediapipe.tasks.vision.facedetector.FaceDetector
import com.google.mediapipe.tasks.vision.facedetector.FaceDetector.FaceDetectorOptions
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideContentResolver(@ApplicationContext context: Context): ContentResolver {
        return context.contentResolver
    }

    @Provides
    @Singleton
    fun provideFaceDetector(@ApplicationContext context: Context): FaceDetector {
        val baseOptionsBuilder = BaseOptions.builder()
        baseOptionsBuilder.setDelegate(Delegate.CPU)
        val modelName = "blaze_face_short_range.tflite"

        baseOptionsBuilder.setModelAssetPath(modelName)

        val options = FaceDetectorOptions.builder()
            .setBaseOptions(baseOptionsBuilder.build())
            .setRunningMode(RunningMode.IMAGE) // This mode is for static image detection
            .setMinDetectionConfidence(0.5f)   // Minimum confidence for face detection
            .build()

        return FaceDetector.createFromOptions(context, options)
    }

    @Provides
    @Singleton
    fun provideAppDB(@ApplicationContext context: Context): AppDb{
        return Room.databaseBuilder(context, AppDb::class.java, "app_db").build()
    }

    @Provides
    @Singleton
    fun provideNotesDao(db: AppDb): TagsDao {
        return db.getTagsDao()
    }

}