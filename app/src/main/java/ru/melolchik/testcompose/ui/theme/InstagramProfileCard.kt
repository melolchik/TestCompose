package ru.melolchik.testcompose.ui.theme

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun InstagramProfileCard(){

    Card (modifier = Modifier.padding(8.dp),
        shape = RoundedCornerShape(topStart =8.dp, topEnd = 8.dp),
        border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.onBackground),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background)
    )
    {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(all = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically ){
            Box(modifier = Modifier
                .size(50.dp)
                .background(color = Color.Yellow)
            ){}
            UserStatistic(title = "Posts", value = "9849")
            UserStatistic(title = "Followers", value = "576")
            UserStatistic(title = "Following", value = "900")

        }
    }

}

@Composable
private  fun UserStatistic(title : String, value : String){
    Column(modifier = Modifier
        .height(80.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ){
        Text(text = value,
            fontSize = 30.sp,
            fontFamily = FontFamily.Cursive
            )
        Text( text = title,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold)
    }
}

@Preview
@Composable
fun InstagramProfileCardLight(){
    TestComposeTheme(darkTheme = false){
        InstagramProfileCard()
    }
}

@Preview
@Composable
fun InstagramProfileCardDark(){
    TestComposeTheme(darkTheme = true){
        InstagramProfileCard()
    }
}