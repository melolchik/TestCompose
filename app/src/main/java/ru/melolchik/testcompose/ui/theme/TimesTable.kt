package ru.melolchik.testcompose.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun TimesTable(){
    Column (modifier = Modifier
        .background(color = Color.Yellow)
        //.padding(all = 16.dp)
        .fillMaxSize())
    {
        for(i in 1 ..9){
            Row(modifier = Modifier
                .fillMaxWidth()
                .weight(1f)

            ){
                for(j in 1 ..9){

                    val color = if( (i + j).mod(2) == 1) Color.White else Color.Yellow
                    Box(modifier = Modifier.fillMaxHeight()
                        .weight(1f)
                        .border(width = 1.dp, color = Color.Gray)
                        .background(color = color),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "${i * j}")
                    }
                }
            }
        }
    }
}