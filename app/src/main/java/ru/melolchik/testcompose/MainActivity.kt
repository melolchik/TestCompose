package ru.melolchik.testcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import ru.melolchik.testcompose.ui.theme.InstagramProfileCard
import ru.melolchik.testcompose.ui.theme.MainViewModel
import ru.melolchik.testcompose.ui.theme.TestComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        //enableEdgeToEdge()
        setContent {
            Test(viewModel = viewModel)

        }
    }
}


@Composable
fun Test(viewModel: MainViewModel) {
    TestComposeTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.background)
        ) {
            val models = viewModel.models.observeAsState(listOf())
            LazyVerticalGrid(columns = GridCells.Fixed(2)) {
               // items(items = )
                items(models.value){ model ->
                    InstagramProfileCard(model){
                        viewModel.changeFollowingStatus(model)
                    }
                }
            }

        }

    }
}


