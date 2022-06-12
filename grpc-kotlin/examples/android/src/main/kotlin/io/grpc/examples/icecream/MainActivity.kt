package io.grpc.examples.icecream

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import coil.compose.rememberAsyncImagePainter

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val mainViewModel by viewModels<MainViewModel>()

        setContent {
            Surface(color = MaterialTheme.colors.background) {
                IceCreamSelector(mainViewModel)
            }
        }
    }
}

@Composable
fun IceCreamSelector(viewModel: MainViewModel) {
    val uiState = viewModel.uiState
    Box(
            modifier = Modifier
                    .fillMaxSize()
                    .background(
                            brush = Brush.verticalGradient(
                                    colors = listOf(
                                            Color(0xFF00BCD4),
                                            Color(0xFF2196F3),
                                    )
                            )
                    ),
            contentAlignment = Alignment.Center
    ) {
        Content(uiState, viewModel)
        if (uiState.loading) {
            CircularProgressIndicator(modifier = Modifier.size(80.dp))
        }
    }


}

@Composable
private fun Content(uiState: UiState, viewModel: MainViewModel) {
    Column(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.size(20.dp))
        if (uiState.cones.isEmpty()) {
            Button(onClick = { viewModel.loadCones() }) {
                Text(text = "Load Cones")
            }
        } else {
            LazyRow {
                uiState.cones.map { cone ->
                    item {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Image(
                                    painter = rememberAsyncImagePainter(cone.imageUrl),
                                    contentDescription = null,
                                    modifier = Modifier
                                            .size(140.dp)
                                            .clickable {
                                                viewModel.setCone(cone)
                                            }
                            )
                        }
                    }
                }
            }
        }
        Spacer(modifier = Modifier.size(20.dp))
        if (uiState.flavors.isEmpty()) {
            Button(onClick = { viewModel.loadFlavors() }) {
                Text(text = "Load Cones")
            }
        } else {
            LazyRow {
                uiState.flavors.map { flavor ->
                    item {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Image(
                                    painter = rememberAsyncImagePainter(flavor.imageUrl),
                                    contentDescription = null,
                                    modifier = Modifier
                                            .size(90.dp)
                                            .clickable {
                                                viewModel.setFlavor(flavor)
                                            }
                            )
                            Text(text = flavor.name, color = Color.White)
                            Text(text = "\$${flavor.price}", color = Color.White)
                        }
                    }
                }
            }
        }
        Spacer(modifier = Modifier.size(50.dp))
        if (uiState.selectedCone != null) {
            BoxWithConstraints(modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.CenterHorizontally)) {
                uiState.selectedFlavors.reversed().mapIndexed { index, flavor ->
                    Box(modifier = Modifier
                            .zIndex(99 - index.toFloat())
                            .align(Alignment.TopCenter)
                            .graphicsLayer {
                                translationY = (index * 80).toFloat()
                            }
                    ) {
                        Image(
                                painter = rememberAsyncImagePainter(flavor.imageUrl),
                                contentDescription = null,
                                modifier = Modifier
                                        .size(80.dp)
                        )
                    }
                }
                Box(modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(top = 10.dp)
                        .graphicsLayer {
                            translationY = (uiState.selectedFlavors.size * 80).toFloat()
                        }
                ) {
                    Image(
                            painter = rememberAsyncImagePainter(uiState.selectedCone.imageUrl),
                            contentDescription = null,
                            modifier = Modifier.size(140.dp)
                    )
                }
            }
        }
    }
}
