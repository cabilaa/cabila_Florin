package com.cabila0046.assessment3.ui.screen

import android.content.res.Configuration
import android.graphics.pdf.models.ListItem
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.cabila0046.assessment3.R
import com.cabila0046.assessment3.model.Tumbuhan
import com.cabila0046.assessment3.network.TumbuhanApi
import com.cabila0046.assessment3.ui.theme.Assessment3Theme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.app_name))
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) { innerPadding ->
        ScreenContent(Modifier.padding(innerPadding))

    }
}
@Composable
fun ScreenContent(modifier: Modifier = Modifier ) {
    val viewModel: MainViewModel = viewModel()
    val data by viewModel.data

    LazyVerticalGrid(
        modifier = modifier.fillMaxSize().padding(4.dp),
        columns = GridCells.Fixed(2),
    ) {
        items(data) { ListItem(tumbuhan = it) }
    }
}
@Composable
fun ListItem(tumbuhan: Tumbuhan) {
        Box(
            modifier = Modifier.padding(4.dp).border(1.dp, Color.Gray),
            contentAlignment = Alignment.Center
        ){
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(TumbuhanApi.getTumbuhanUrl(tumbuhan.imageId))
                .crossfade(true)
                .build(),
            contentDescription = stringResource(R.string.gambar, tumbuhan),
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxWidth().padding(4.dp)
        )
            Column(
                modifier = Modifier.fillMaxWidth().padding(4.dp)
                    .background(Color(red = 0f, green = 0f, blue = 0f, alpha = 0.5f))
            ) {
                Text(text = tumbuhan.nama,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(text = tumbuhan.jenis,
                    fontStyle = FontStyle.Italic,
                    fontSize = 14.sp,
                    color = Color.White
                )
                Text(text = if (tumbuhan.isSelected) "Darat" else "Laut",
                    fontWeight = if (tumbuhan.isSelected) FontWeight.Bold else FontWeight.Normal,
                    fontStyle = FontStyle.Italic,
                    fontSize = 14.sp,
                    color = if (tumbuhan.isSelected) Color.Green else Color.Cyan
                )
            }
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun MainScreenPreview() {
    Assessment3Theme {
        MainScreen()
    }
}