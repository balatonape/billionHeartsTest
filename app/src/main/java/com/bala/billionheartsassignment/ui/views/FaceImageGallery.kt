package com.bala.billionheartsassignment.ui.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.bala.billionheartsassignment.viewmodel.ImageViewModel


@Composable
fun GalleryView(onLoaded: () -> Unit, viewModel: ImageViewModel = hiltViewModel()) {
    val imgList by viewModel.imageList.collectAsState()
    val isLoading by viewModel.showLoading.collectAsState()

    LaunchedEffect(isLoading) {
        if (!isLoading) onLoaded()
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        items(imgList) { faceImageData ->
            Column(modifier = Modifier.fillParentMaxSize()) {
                FaceImageItem(faceImageData, viewModel)
            }
        }
    }
}