package ru.melolchik.testcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import ru.melolchik.testcompose.ui.theme.TestComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UserInfo(name = "Olga", age = 42)
        }
    }
}


@Composable
fun UserInfo(name: String, age : Int){
    Column {
        repeat(10) {
            Text(text = "Hello $name. You are $age age")
        }

    }
}

@Preview
@Composable
fun Greeting() {
    val name = "Jone"
    Text(
        text = "Hello $name!",
        color = Color.Green
    )
}

@Preview
@Composable
fun UserInfoPreview(){
    UserInfo(name = "John", age = 33)
}
