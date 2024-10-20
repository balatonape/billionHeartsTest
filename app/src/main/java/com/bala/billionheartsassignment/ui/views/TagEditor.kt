package com.bala.billionheartsassignment.ui.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bala.billionheartsassignment.constants.ADD_NAME
import com.bala.billionheartsassignment.constants.CANCEL
import com.bala.billionheartsassignment.constants.INPUT_HINT

//Can be extended for update or removal of tag, by passing type as part of the function
@Composable
fun TagDialog(onSuccess: (tag: String, type: EDIT_TYPE) -> Unit, onClose: () -> Unit) {
    var name by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onClose,
        confirmButton = {
            Button(onClick = {
                onSuccess(name, EDIT_TYPE.ADD)
            }) {
                Text(ADD_NAME)
            }
        },
        dismissButton = {
            Button(onClick = onClose) {
                Text(CANCEL)
            }
        },
        text = {
            Column {
                Text(INPUT_HINT)
                Spacer(modifier = Modifier.height(8.dp))
                TextField(value = name, onValueChange = { name = it })
            }
        }
    )
}

enum class EDIT_TYPE {
    ADD,
    UPDATE,
    DELETE
}