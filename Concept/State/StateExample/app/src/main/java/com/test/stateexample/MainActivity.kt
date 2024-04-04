package com.test.stateexample

import android.os.Bundle
import android.os.Parcelable
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.mapSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.test.stateexample.ui.theme.StateExampleTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.parcelize.Parcelize

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
                    ListSaverSnippet()
                }
            }
        }
    }
}

@Composable
fun HoistingSnippets(){
    var name by rememberSaveable { mutableStateOf("") }

    ComposableSnippet(name = name) {
        name = it
    }
}

//작동 안되는거
@Composable
fun ComposableSnippet(name: String, onValueChanged: (String) -> Unit) {
    Column(modifier = Modifier.padding(16.dp)) {
        if(name.isNotEmpty())
            Text(
                text = "Hello! $name",
                modifier = Modifier.padding(bottom = 8.dp),
                style = MaterialTheme.typography.bodyMedium
            )
        OutlinedTextField(
            value = name,
            onValueChange = onValueChanged,
            label = { Text("Name")}
        )
    }
}
//작동 되는거
@Composable
fun ComposableSnippet2() {
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
fun FlowSnippet(){
    var name1 by remember { mutableStateOf("플로우 수집 아직 안됬다잉") }
    val a: Flow<String> = flow {
        while(true){
            emit("이게 플로우여")
            delay(5000L)
        }
    }
    //그냥 flow를 collect 했을 경우
    LaunchedEffect(key1 = name1) {
        a.collect{
            name1 = it
        }
    }
    Text(
        text = "Hello! ${name1}",
        modifier = Modifier.padding(bottom = 8.dp),
        style = MaterialTheme.typography.bodyMedium
    )
    runBlocking {
        delay(3000)
    }
    //state로 collect 했을 경우
    name1 = a.collectAsState(initial = "아직 안된 거시여").value
}

//플로우 예제
@Composable
fun FlowSample(){
    var sampleText by remember { mutableStateOf("아직 안됬어") }
    val a: Flow<String> = flow {
        while(true) {
            delay(1000L)
            emit("이게 플로우여")
        }
    }
    sampleText = a.collectAsStateWithLifecycle(initialValue = "아직 들 됬어").value
//    sampleText = a.collectAsState(initial = "아직 덜 됬어").value
    /*LaunchedEffect(key1 = sampleText) {
        a.collect {
            sampleText = it
        }
    }*/

    Text(text = sampleText)
}

//라이브데이터 예제
val name = MutableLiveData<String>()
@Composable
fun LiveDataSnippet(){
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

@Composable
fun SavableSnippet(){
    var name by remember{ mutableStateOf("1") }
    var nameWithSavable by rememberSaveable{ mutableStateOf("1") }
    Column {
        Text(text = name)
        Button(onClick = { name += "1" }) {
            Text(text = "add_button")
        }
    }
}

@Parcelize
data class City(val name: String, val country: String): Parcelable

@Composable
fun ParcelizeSnippet(){
    var selectedCity = rememberSaveable {
        mutableStateOf(City("울산", "울산광역시"))
    }
    Text(text = selectedCity.value.country)
}

data class Region(val name: String, val country: String)

val RegionSaver = run{
    val nameKey = "Name"
    val countryKey = "Country"
    mapSaver(
        save = {mapOf(nameKey to it.name, countryKey to it.country)},
        restore = {Region(it[nameKey] as String, it[countryKey] as String)}
    )
}

@Composable
fun MapSaverSnippet(){
    val selectedCity = rememberSaveable(stateSaver = RegionSaver) {
        mutableStateOf(Region("Madrid", "울산"))
    }
    Text(text = selectedCity.value.toString())
}

val RegionSaver2 = listSaver<Region, Any>(
    save = { listOf(it.name, it.country) },
    restore = {Region(it[0] as String, it[1] as String)} 
)
@Composable
fun ListSaverSnippet(){
    val selectedCity = rememberSaveable(stateSaver = RegionSaver2) {
        mutableStateOf(Region("울산", "광역시"))
    }
    
    Text(text = selectedCity.value.toString())
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    StateExampleTheme {
    }
}