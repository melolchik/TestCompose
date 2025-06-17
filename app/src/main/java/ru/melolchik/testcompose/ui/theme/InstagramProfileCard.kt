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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun InstagramProfileCard(){

    Card (shape = RoundedCornerShape(topStart =8.dp, topEnd = 8.dp),
        border = BorderStroke(width = 1.dp, color = Color.Black),
        colors = CardDefaults.cardColors(containerColor = Color.White)
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
            TwoBoxes()
            TwoBoxes()
            TwoBoxes()

        }
    }

}

@Composable
private  fun TwoBoxes(){
    Column(modifier = Modifier
        .height(80.dp)
        .background(color = Color.Green),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ){
        Box(modifier = Modifier
            .size(25.dp)
            .background(color = Color.Blue)
        ){}
        Box(modifier = Modifier
            .size(25.dp)
            .background(color = Color.Red)
        ){}
    }
}