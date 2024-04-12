package com.test.buttonexample

import android.graphics.drawable.shapes.Shape
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.test.buttonexample.ui.theme.ButtonExampleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ButtonExampleTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    ButtonSnippets()
                }
            }
        }
    }
}

@Composable
fun ButtonSnippets() {
    val localContext = LocalContext.current
    Column(
        modifier = Modifier.fillMaxSize()
        ,verticalArrangement = Arrangement.SpaceAround) {
        Row(modifier = Modifier.fillMaxWidth()
            ,horizontalArrangement = Arrangement.SpaceAround) {
            Button(
                //클릭 시 이벤트
                onClick = {
                    Toast.makeText(localContext, "test", Toast.LENGTH_SHORT).show()
                }
            ) {
                Text("OnClick")
            }
            Button(
                onClick = { /*TODO*/ },
                enabled = false
            ) {
                Text("Enabled")
            }
            Button(
                onClick ={},
                shape = RoundedCornerShape(10)
            ){
                Text("RoundedCornerShape")
            }
        }
        Row(modifier = Modifier.fillMaxWidth()
            ,horizontalArrangement = Arrangement.SpaceAround) {
            Button(
                onClick ={},
                shape = CircleShape
            ){
                Text("CircleShape")
            }

            Button(
                onClick = {},
                colors = ButtonDefaults.buttonColors(Color.Red)
            ){
                Text("RedButton")
            }

            Button(
                onClick = {},
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 10.dp)
            ){
                Text(text = "Elevation")
            }
        }
        Row(modifier = Modifier.fillMaxWidth()
            ,horizontalArrangement = Arrangement.SpaceAround) {
            Button(
                onClick ={},
                border = BorderStroke(5.dp, Color.Black)
            ){
                Text("BorderStroke")
            }

            Button(
                onClick ={},
                contentPadding = PaddingValues(50.dp)
            ){
                Text("B")
                Text("B")
            }

            Button(
                onClick ={},
                interactionSource = MutableInteractionSource()
            ){
                Text("B")
                Text("B")
            }
        }
    }

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    ButtonExampleTheme {
        ButtonSnippets()
    }
}