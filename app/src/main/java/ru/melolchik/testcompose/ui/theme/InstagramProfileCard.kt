package ru.melolchik.testcompose.ui.theme

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.melolchik.testcompose.R


@Composable
fun InstagramProfileCard(mainViewModel: MainViewModel){
    Log.d("RECOMPOSITION", "InstagramProfileCard")
    val isFollowed =  mainViewModel.isFollowing.observeAsState(initial = false)

    Card (modifier = Modifier.padding(8.dp),
        shape = RoundedCornerShape(topStart =8.dp, topEnd = 8.dp),
        border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.onBackground),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background)
    )
    {
        Log.d("RECOMPOSITION", "Card")
        Column (modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
        ) {
            Row(modifier = Modifier
                .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically ){
                Image(modifier = Modifier
                    .clip(shape = CircleShape)
                    .background(color = Color.White)
                    .padding(8.dp)
                    .size(50.dp),
                    painter = painterResource(id = R.drawable.ic_instagram),
                    contentDescription = "",

                    )
                UserStatistic(title = "Posts", value = "9849")
                UserStatistic(title = "Followers", value = "576")
                UserStatistic(title = "Following", value = "900")

            }
            Text(text = "Instagram",
                fontSize = 24.sp,
                fontStyle = FontStyle.Italic,
                fontFamily = FontFamily.Cursive
            )
            Text(text = "#YoursToMake",
                fontSize = 16.sp)
            Text(text = "www.facebook.com//",
                fontSize = 16.sp)

            Spacer(modifier = Modifier.height(16.dp))
            FollowButton(isFollowed = isFollowed){
                mainViewModel.changeFollowingStatus()
            }
        }

    }

}
@Composable
private fun FollowButton(isFollowed  : State<Boolean>, clickListener: () -> Unit){

    Log.d("RECOMPOSITION", "FollowButton")
    Button(onClick = {
                        clickListener()
                     },
        colors = ButtonDefaults.buttonColors(
            containerColor = if(isFollowed.value) {
                MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
            } else{
                MaterialTheme.colorScheme.primary
            }
        )) {
        Text(text = if(isFollowed.value) "Unfollow" else "Follow")
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

