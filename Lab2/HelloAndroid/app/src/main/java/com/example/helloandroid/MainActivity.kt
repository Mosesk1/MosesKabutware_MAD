package com.example.helloandroid

//import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.magnifier
//import androidx.compose.foundation.layout.BoxScopeInstance.align
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.textInputServiceFactory
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight.Companion.Black
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.helloandroid.ui.theme.*
import java.util.Date

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Greeting()
        }
    }
}

@Composable
fun Greeting() {
    var name by remember { mutableStateOf("") }
    var textFieldName by remember { mutableStateOf("") }
    var firstImage by remember { mutableStateOf(false) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        NameTextField(name = textFieldName, changed = { textFieldName = it })
        SayHi {
            name = textFieldName
            firstImage = !firstImage
        }
        Image(
            painter = painterResource(
                id = if (firstImage) {
                    R.drawable.image_1
                } else {
                    R.drawable.image_2
                }
            ),
            contentDescription = stringResource(id = R.string.ball),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(200.dp)
                .clip(CircleShape)
        )
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .padding(top = 400.dp, start = 60.dp, end = 60.dp, bottom = 50.dp)
            .fillMaxWidth()
            .height(60.dp)
            .background(myG)
    ) {
        MessageText(newName = name)
    }

}

@Composable
fun SayHi(clicked: () -> Unit) {
    Button(
        onClick = clicked,
        shape = RoundedCornerShape(50.dp),
        modifier = Modifier
            .padding(top = 10.dp, bottom = 20.dp)
            .width(100.dp)
            .height(60.dp)
    ) {
        Text(
            stringResource(id = R.string.button),
            modifier = Modifier,
            color = Color.White,
            fontSize = 18.sp,
            textAlign = TextAlign.Center
        )

    }
}

@Composable
fun NameTextField(name: String, changed: (String) -> Unit) {
    TextField(
        value = name,
        label = { Text(stringResource(id = R.string.Name)) },
        onValueChange = changed,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp, bottom = 10.dp)
//    modifier.clickable { textInputServiceFactory="" }
    )
}

@Composable
fun MessageText(newName: String) {
    if (newName.isNotEmpty()) {
        Text(
            stringResource(R.string.greeting) + " " + newName,
            color = Color.White,
            fontSize = 24.sp,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    HelloAndroidTheme {
        Greeting()
    }
}