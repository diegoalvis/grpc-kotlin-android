package io.grpc.examples.icecream

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val iceCreamService by lazy { IceCreamService() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Surface(color = MaterialTheme.colors.background) {
                IceCreamSelector(iceCreamService)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        iceCreamService.close()
    }
}

@Composable
fun IceCreamSelector(service: IceCreamService) {

    val scope = rememberCoroutineScope()

    val nameState = remember { mutableStateOf(TextFieldValue()) }

    val a  = service.getCones("id")


    Column(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally) {

        Spacer(modifier = Modifier.size(150.dp))
        Text(text = "Select Cone")

        LazyRow {

            (0..10).map {
                item {
                    Text(text = "Element $it")
                }
            }
        }
        OutlinedTextField(nameState.value, { nameState.value = it })

//        Button({ scope.launch { se.sayHello(nameState.value.text) } }, Modifier.padding(10.dp)) {
////            Text(stringResource(R.string.send_request))
//        }
//
//        if (se.responseState.value.isNotEmpty()) {
////            Text(stringResource(R.string.server_response), modifier = Modifier.padding(top = 10.dp))
//            Text(se.responseState.value)
//        }
    }
}
