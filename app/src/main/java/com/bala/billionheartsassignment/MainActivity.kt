package com.bala.billionheartsassignment

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bala.billionheartsassignment.constants.PERMISSION_DENIED_TITLE
import com.bala.billionheartsassignment.constants.PERMISSION_TITLE
import com.bala.billionheartsassignment.constants.PERM_BUTTON
import com.bala.billionheartsassignment.constants.PERM_BUTTON_DENIED
import com.bala.billionheartsassignment.ui.theme.BillionHeartsAssignmentTheme
import com.bala.billionheartsassignment.ui.views.GalleryView
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BillionHeartsAssignmentTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CheckPermissionAndLoadScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun CheckPermissionAndLoadScreen(modifier: Modifier) {
    PermissionRequest()
}


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PermissionRequest() {
    val permissionState =
        rememberPermissionState(permission = Manifest.permission.READ_EXTERNAL_STORAGE)

    var isLoading by remember { mutableStateOf(true) }
    val context = LocalContext.current

    println(" gallery permission${permissionState.status.isGranted} ${permissionState.status.shouldShowRationale}")
    if (!permissionState.status.isGranted) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(PERMISSION_TITLE)
            Spacer(modifier = Modifier.height(24.dp))

            Button(onClick = {
                // Create an Intent to open the app's settings
               /* if (!permissionState.status.shouldShowRationale) {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                        data = Uri.fromParts("package", context.packageName, null)
                    }
                    // Start the activity with the intent
                    context.startActivity(intent)
                }*/

                permissionState.launchPermissionRequest()

            }) {
                Text(PERM_BUTTON)
            }
        }
    } else {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center     // Center the loader
        ) {
            if (isLoading) ShowLoader()
            GalleryView({
                println("showLoader Did stop loader")
                isLoading = false
            })
        }
    }
}

@Composable
fun ShowLoader() {
    println("showLoader Did it start loader")
    // Display the circular loader
    CircularProgressIndicator(
        color = Color.Cyan,
        modifier = Modifier
            .size(150.dp)
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BillionHeartsAssignmentTheme {

    }
}