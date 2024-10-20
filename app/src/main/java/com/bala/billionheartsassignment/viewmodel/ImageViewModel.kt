package com.bala.billionheartsassignment.viewmodel

import android.content.ContentResolver
import android.graphics.BitmapFactory
import android.graphics.RectF
import android.provider.MediaStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bala.billionheartsassignment.data.FaceImageData
import com.bala.billionheartsassignment.data.db.Tag
import com.bala.billionheartsassignment.data.db.TagsDao
import com.google.mediapipe.framework.image.BitmapImageBuilder
import com.google.mediapipe.tasks.components.containers.Detection
import com.google.mediapipe.tasks.vision.facedetector.FaceDetector
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

// injecting dependency using daggerHilt
@HiltViewModel
class ImageViewModel @Inject constructor(
    private val contentResolver: ContentResolver,
    private val faceDetector: FaceDetector,
    private val tagsDao: TagsDao
) : ViewModel() {
    var showLoading = MutableStateFlow(true)
        private set

    var imageList = MutableStateFlow<List<FaceImageData>>(mutableListOf())
        private set

    init {
        loadImages()
    }

    private fun loadImages() {
        viewModelScope.launch(Dispatchers.IO) {
            showLoading.value = true
            println("showLoader inside view model setting to true ")
            val imgList = mutableListOf<FaceImageData>()
            withContext(Dispatchers.IO) {
                val uriExternal = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                val projection = arrayOf(MediaStore.Images.Media._ID, MediaStore.Images.Media.DATA)

                val cursor = contentResolver.query(uriExternal, projection, null, null, null)


                cursor?.use {
                    val columnIndexData = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                    while (cursor.moveToNext()) {
                        val imagePath = cursor.getString(columnIndexData)
                        val faces = detectFaces(imagePath)

                        if (faces.isNotEmpty()) {
                            val faceRects = faces.map { face ->
//                            Adding scale conversion to show the box according to the image size

                                val scaledRect = RectF(0f, 0f, 320f, 490f)
                                val originalRect = face.boundingBox()
                                val scaleX = scaledRect.width() / originalRect.width()
                                val scaleY = scaledRect.height() / originalRect.height()

                                val newRectF = RectF(
                                    originalRect.left * scaleX,
                                    originalRect.top * scaleY,
                                    originalRect.right * scaleX,
                                    originalRect.bottom * scaleY
                                )

                                return@map newRectF
                            }
                            imgList.add(FaceImageData(imagePath, faceRects))
                        }
                    }

                }
            }
            imageList.value = imgList
            showLoading.value = false
//            Will check if any names already present will show on the image
            getUpdatedTagsList()
        }
    }

    private fun detectFaces(imagePath: String): List<Detection> {
        val bitmap = BitmapFactory.decodeFile(imagePath)
        val mpImage = BitmapImageBuilder(bitmap).build()

        val result = faceDetector.detect(mpImage)
        // Retrieve detected faces
        return result?.detections() ?: emptyList()
    }

    fun addTag(imagePath: String, tagName: String) {
        viewModelScope.launch {
            tagsDao.addTag(Tag(null, imagePath, tagName))
//            call to update the list so that UI will show the name
            getUpdatedTagsList()
        }
    }

    private fun getUpdatedTagsList() {
//        Launching with default coroutine dispatcher
        viewModelScope.launch {
            val lists = tagsDao.getTagsList()

            val updateList = imageList.value.toMutableList()

            lists.forEach { tag ->
                val index = updateList.indexOfFirst { it.imgPath == tag.imgPath }
                if (index != -1) {
                    updateList[index] = updateList[index].copy(tag = tag.name)
                }
            }

            imageList.value = updateList
        }
    }
}