package com.test.stateexample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.test.stateexample.ui.theme.StateExampleTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StateExampleTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LiveDataSample ()
                }
            }
        }
    }
}
//작동 안되는거
@Composable
fun Greeting() {
    var name by remember { mutableStateOf("") }
    Column(modifier = Modifier.padding(16.dp)) {
        if(name.isNotEmpty())
            Text(
                text = "Hello! $name",
                modifier = Modifier.padding(bottom = 8.dp),
                style = MaterialTheme.typography.bodyMedium
            )
        OutlinedTextField(
            value = name,
            onValueChange = { name = it},
            label = { Text("Name")}
        )
    }
}
//작동 되는거
@Composable
fun Greeting2() {
    var name by remember { mutableStateOf("") }
    Column(modifier = Modifier.padding(16.dp)) {
        if(name.isNotEmpty())
            Text(
                text = "Hello! $name",
                modifier = Modifier.padding(bottom = 8.dp),
                style = MaterialTheme.typography.bodyMedium
            )
        OutlinedTextField(
            value = name,
            onValueChange = { name = it},
            label = { Text("Name")}
        )
    }
}

//플로우 예제
@Composable
fun FlowSample(){
    val a: Flow<String> = flow {
        delay(1000L)
        emit("이게 플로우여")
    }
    a.collectAsState(initial = "")
}

//라이브데이터 예제
val name = MutableLiveData<String>()
@Composable
fun LiveDataSample(){
    val name1 by name.observeAsState()
    Column (modifier = Modifier.padding(16.dp)) {
        if(name1 != null)
            Text(
                text = "Hello! $name1",
                modifier = Modifier.padding(bottom = 8.dp),
                style = MaterialTheme.typography.bodyMedium
            )
        OutlinedTextField(
            value = name1 ?: "",
            onValueChange = { name.postValue(it)},
            label = { Text("Name")}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    StateExampleTheme {
        Greeting()
    }
}