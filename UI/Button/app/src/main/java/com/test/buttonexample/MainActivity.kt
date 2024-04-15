package com.test.buttonexample

import android.graphics.drawable.shapes.Shape
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MultiChoiceSegmentedButtonRow
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.VectorPainter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.test.buttonexample.ui.theme.ButtonExampleTheme
import kotlinx.coroutines.selects.select

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ButtonExampleTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    MaterialButtonSnippets()
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
    ) {
        //onClick
        Button(
            onClick = {
                Toast.makeText(localContext, "test", Toast.LENGTH_SHORT).show()
            }
        ) {
            Text("OnClick")
        }
        //Enabled
        Button(
            onClick = { /*TODO*/ },
            enabled = false
        ) {
            Text("Enabled")
        }
        //Shape
        Button(
            onClick = {},
            shape = RoundedCornerShape(10)
        ) {
            Text("RoundedCornerShape")
        }
        Button(
            onClick = {},
            shape = CircleShape
        ) {
            Text("CircleShape")
        }
        //Color
        Button(
            onClick = {},
            colors = ButtonDefaults.buttonColors(Color.Red)
        ) {
            Text("RedButton")
        }

        Button(
            onClick = {},
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 10.dp)
        ) {
            Text(text = "Elevation")
        }
        //Stroke
        Button(
            onClick = {},
            border = BorderStroke(5.dp, Color.Black)
        ) {
            Text("BorderStroke")
        }
        //ContentPadding
        Button(
            onClick = {},
            contentPadding = PaddingValues(50.dp)
        ) {
            Text("BB")
        }

        //InteractionResource
        val interactionSource = remember { MutableInteractionSource() }
        val isPressed by interactionSource.collectIsPressedAsState()
        val scale by animateFloatAsState(targetValue = if (isPressed) 0.9f else 1f, label = "scale")
        Button(
            modifier = Modifier.scale(scale),
            onClick = {},
            interactionSource = interactionSource
        ) {
            AnimatedVisibility(visible = isPressed) {
                if (isPressed) {
                    Row {
                        Icon(painter = painterResource(id = R.drawable.icon_test), contentDescription = "")
                        Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
                    }
                }
            }
            Text("ㅇㅇㅇㅇㅇ")
        }
    }
}

@Composable
fun MaterialButtonSnippets() {
    val icon = R.drawable.icon_test
    Column {
        ElevatedButton(onClick = { /*TODO*/ }) {
            Text("ElevatedButton")
        }

        Button(onClick = { /*TODO*/ }) {
            Text(text = "FilledButton")
        }
        FilledTonalButton(onClick = { /*TODO*/ }) {
            Text("FilledTonalButton")
        }
        OutlinedButton(onClick = { /*TODO*/ }) {
            Text(text = "OutlinedButton")
        }
        TextButton(onClick = { /*TODO*/ }) {
            Text(text = "TextButton")
        }
        IconButton(onClick = { /*TODO*/ }) {
            Icon(painter = painterResource(id = icon), contentDescription = "")
        }
        FilledIconButton(onClick = { /*TODO*/ }) {
            Icon(painter = painterResource(id = icon), contentDescription = "")
        }

        FloatingActionButton(onClick = { /*TODO*/ }) {
            Icon(painter = painterResource(id = icon), contentDescription = "")
        }
        ExtendedFloatingActionButton(onClick = { /*TODO*/ }) {
            Icon(painter = painterResource(id = icon), contentDescription = "")
            Spacer(modifier = Modifier.width(10.dp))
            Text(text = "FAB")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun SegmentedButtonSnippets() {
    var selectedIndex by remember { mutableStateOf(-1) }
    val options = listOf("Day", "Month", "Week")
    val checkedList = remember { mutableStateListOf<Int>() }
    Column {
        SingleChoiceSegmentedButtonRow {
            options.forEachIndexed { index, label ->
                SegmentedButton(
                    selected = index == selectedIndex,
                    onClick = {
                        if(selectedIndex == index)
                            selectedIndex = -1
                        else
                            selectedIndex = index
                    },
                    shape = SegmentedButtonDefaults.itemShape(index = index, count = options.size)
                ) {
                    Text(label)
                }
            }
        }

        MultiChoiceSegmentedButtonRow {
            options.forEachIndexed { index, s ->
                SegmentedButton(
                    checked = index in checkedList,
                    onCheckedChange = {
                        if(index in checkedList){
                            checkedList.remove(index)
                        } else{
                            checkedList.add(index)
                        }
                    },
                    shape = SegmentedButtonDefaults.itemShape(index = index, count = options.size)
                ) {
                    Text(text = s)
                }
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

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MaterialPreview() {
    ButtonExampleTheme {
        MaterialButtonSnippets()
    }
}