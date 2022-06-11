package io.grpc.examples.icecream

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
    ) {
        Column(modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(modifier = Modifier.size(20.dp))
            Text(text = "Select Cone")
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
                                        modifier = Modifier.size(130.dp)
                                )
                                Text(text = cone.type.toString())
                            }
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.size(50.dp))
            Text(text = "Select ice cream (max 3)")
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
                                        modifier = Modifier.size(100.dp)
                                )
                                Text(text = flavor.name)
                                Text(text = "\$${flavor.price}")
                            }
                        }
                    }
                }
            }
        }
        if (uiState.loading) {
            CircularProgressIndicator(modifier = Modifier.size(80.dp))
        }
    }


}
