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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import kotlinx.coroutines.launch

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

data class UiState(
        val flavors: List<Flavor> = emptyList(),
        val cones: List<Cone> = emptyList(),
        val loading: Boolean = false,
        val errorMessage: String = "",
)

class MainViewModel : ViewModel() {

    private val service by lazy { IceCreamService() }

    var uiState by mutableStateOf(UiState())
        private set

    init {
        loadData()
    }

    fun loadData() {

        viewModelScope.launch {
            try {
                uiState = uiState.copy(loading = true)
                val coneList = service.getCones("id")
                uiState = uiState.copy(cones = coneList)
            } catch (e: Exception) {
                uiState = uiState.copy(errorMessage = "Something went wrong. Try again")
            } finally {
                uiState = uiState.copy(loading = false)
            }
        }
    }


    override fun onCleared() {
        super.onCleared()
        service.close()
    }
}

@Composable
fun IceCreamSelector(viewModel: MainViewModel) {

    val uiState = viewModel.uiState

    Column(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally) {

        Spacer(modifier = Modifier.size(150.dp))
        Text(text = "Select Cone")

        if (uiState.loading) {
            CircularProgressIndicator()
        } else {
            LazyRow {
                uiState.cones.map { cone ->
                    item {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Image(
                                    painter = rememberAsyncImagePainter(cone.imageUrl),
                                    contentDescription = null,
                                    modifier = Modifier.size(180.dp)
                            )
                            Text(text = cone.type.toString())
                        }
                    }
                }
            }
        }
    }
}
