package com.cabila0046.assessment3.ui.screen

import android.content.res.Configuration
import android.graphics.Bitmap
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil.compose.rememberAsyncImagePainter
import com.cabila0046.assessment3.R
import com.cabila0046.assessment3.ui.theme.Assessment3Theme

@Composable
fun TumbuhanDialog(
    bitmap: Bitmap?,
    imageUrl: String? = null,
    nameInitial: String = "",
    speciesInitial: String = "",
    habitatInitial: String = "",
    onDismissRequest: () -> Unit,
    onConfirmation: (String, String, String) -> Unit
) {
    var name by remember { mutableStateOf(nameInitial) }
    var species by remember { mutableStateOf(speciesInitial) }
    var habitat by remember { mutableStateOf(habitatInitial) }


    val radioOptions = listOf(
        stringResource(id = R.string.darat),
        stringResource(id = R.string.air)
    )



    Dialog(onDismissRequest = { onDismissRequest()}) {
        Card(
            modifier = Modifier.padding(16.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                ) {
                    if (bitmap != null) {
                        Log.d("TokohDialog", "Menampilkan bitmap dari kamera")
                        Image(
                            bitmap = bitmap.asImageBitmap(),
                            contentDescription = null,
                            modifier = Modifier.fillMaxWidth().aspectRatio(1f)
                        )
                    }
                    else if (!imageUrl.isNullOrEmpty()) {
                        Log.d("TokohDialog", "Menampilkan gambar dari url")
                        Image(
                            painter = rememberAsyncImagePainter(imageUrl),
                            contentDescription = null,
                            modifier = Modifier.fillMaxWidth().aspectRatio(1f)
                        )
                    }
                }
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text(text = stringResource(id = R.string.name)) },
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Words,
                        imeAction = ImeAction.Next
                    ),
                    modifier = Modifier.padding(top = 8.dp)
                )
                OutlinedTextField(
                    value = species,
                    onValueChange = { species = it },
                    label = { Text(text = stringResource(id = R.string.species)) },
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Words,
                        imeAction = ImeAction.Next
                    ),
                    modifier = Modifier.padding(top = 8.dp)
                )

                Row(
                    modifier = Modifier
                        .padding(top = 6.dp)
                        .border(1.dp, Color.Gray, RoundedCornerShape(4.dp))
                        .padding(8.dp)
                ) {
                    radioOptions.forEach { text ->
                        HabitatOption(
                            label = text,
                            isSelected = habitat == text,
                            modifier = Modifier
                                .selectable(
                                    selected = habitat == text,
                                    onClick = { habitat = text },
                                    role = Role.RadioButton
                                )
                                .weight(1f)
                                .padding(4.dp)
                        )
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    OutlinedButton(
                        onClick = {onDismissRequest() },
                        modifier = Modifier.padding(8.dp)
                    ) {
                        Text(text = stringResource(R.string.batal))
                    }
                    OutlinedButton(
                        onClick = {
                            onConfirmation(name, species, habitat)
                                  },
                        enabled = name.isNotEmpty() && species.isNotEmpty() && habitat.isNotEmpty(),
                        modifier = Modifier.padding(8.dp)
                    ) {
                        Text(text = stringResource(R.string.simpan))
                    }
                }
                }
            }
        }
    }

@Composable
fun HabitatOption(
    label: String,
    isSelected: Boolean,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .padding(vertical = 8.dp, horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        RadioButton(
            selected = isSelected,
            onClick = null
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center
        )
    }
}


@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun AddDialogPreview() {
    Assessment3Theme {
        TumbuhanDialog (
            bitmap = null,
            nameInitial = "teratai",
            speciesInitial = "Genus Nyamphaea",
            habitatInitial = "Air",
            onDismissRequest = {},
            onConfirmation = {_,_,_->}
        )
    }
}