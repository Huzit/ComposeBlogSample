package com.sang.datastore

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.lifecycleScope
import com.sang.datastore.ui.theme.DataStoreTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

//정의
val Context.datastore: DataStore<Preferences> by preferencesDataStore(name = "sample")
//읽기
//쓰기

class MainActivity : ComponentActivity() {
    //key value는 pk
    val intKey = intPreferencesKey("key1")
    val stringKey = stringPreferencesKey("key2")
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DataStoreTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    Greeting("Android")
                }
            }
        }
        runBlocking {
            editDatastore()
            readDatastoreWithMap()
        }
        
    }
    
    private suspend fun editDatastore(){
        this.datastore.edit { pref ->
            pref[intKey] = 1000
            pref[stringKey] = "hello world kotlin"
        }
    }
    
    suspend fun readDatastoreWithMap(){
        var intValue = 0
        var stringValue = ""
        datastore.data.map { pref ->
            stringValue = pref[stringKey] ?: "empty"
            intValue = pref[intKey] ?: 0
        }.collect{
            Log.d("readDatastoreWithMap in coroutine", "intValue : $intValue / stringValue : $stringValue")
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    DataStoreTheme {
        Greeting("Android")
    }
}