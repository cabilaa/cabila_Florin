package com.cabila0046.assessment3.ui.screen

import android.content.res.Configuration
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.cabila0046.assessment3.R
import com.cabila0046.assessment3.ui.theme.Assessment3Theme


@Composable
fun DialogHapus(
    onDismissRequest: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
            text = { Text(text = stringResource(id = R.string.pesan_hapus)) },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(text = stringResource(id = R.string.hapus))
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismissRequest() }) {
                Text(text = stringResource(id = R.string.batal))
            }
        },
        onDismissRequest = { onDismissRequest() },
        )
}

@Preview(showSystemUi = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showSystemUi = true)
@Composable
fun DialogHapusPreview() {
    Assessment3Theme {
        DialogHapus(
            onDismissRequest = { },
            onConfirm = { }
        )
    }
}

