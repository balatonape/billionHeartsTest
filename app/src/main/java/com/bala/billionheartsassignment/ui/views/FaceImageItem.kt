package com.bala.billionheartsassignment.ui.views

import android.graphics.BitmapFactory
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.dp
import com.bala.billionheartsassignment.data.FaceImageData
import com.bala.billionheartsassignment.viewmodel.ImageViewModel


@Composable
fun FaceImageItem(faceImageData: FaceImageData, viewModel: ImageViewModel) {
    val bitmap = remember { BitmapFactory.decodeFile(faceImageData.imgPath).asImageBitmap() }
    var showTagEditor by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            bitmap = bitmap,
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )

        // Draw bounding boxes over detected faces
        Canvas(modifier = Modifier
            .fillMaxSize()
            .clickable {
                showTagEditor = true
            }) {
            drawIntoCanvas { canvas ->
                val paint = Paint().asFrameworkPaint().apply {
                    color = android.graphics.Color.BLACK
                    style = android.graphics.Paint.Style.STROKE
                    strokeWidth = 3.dp.toPx()
                }

                // Draw bounding boxes for each detected face
                for (face in faceImageData.faces) {
                    canvas.nativeCanvas.drawRect(face, paint)

                    // To add name if the name exists
                    if (faceImageData.tag?.isNotEmpty() == true) {
                        val minSize = 24.dp.toPx()
                        canvas.nativeCanvas.drawText(
                            faceImageData.tag,
                            face.left,
//                            To position little below the detected bounds
                            face.bottom + minSize,
                            android.graphics.Paint().apply {
                                color = android.graphics.Color.RED
                                textSize = minSize  // Adjust text size if needed
                            })
                    }
                }
            }
        }

        if (showTagEditor) {
            TagDialog({ tag, type ->
                when (type) {
                    EDIT_TYPE.ADD -> viewModel.addTag(faceImageData.imgPath, tag)
                    EDIT_TYPE.UPDATE -> {}
                    EDIT_TYPE.DELETE -> {}
                }

                showTagEditor = false
            }, {
                showTagEditor = false
            })
        }
    }
}
