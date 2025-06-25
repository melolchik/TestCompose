package ru.melolchik.testcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.launch
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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Test(viewModel: MainViewModel) {
    TestComposeTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.background)
        ) {
            val models = viewModel.models.observeAsState(listOf())           
            LazyColumn {
                // items(items = )
               
                items(models.value, key = {it.id}) { model ->
                    val dismissState = rememberDismissState()

                    if(dismissState.isDismissed(DismissDirection.EndToStart)){
                        viewModel.delete(model)
                    }
                    SwipeToDismiss(state = dismissState, 
                        background = {
                                     Box(modifier = Modifier.padding(16.dp)
                                                            .background(color = Color.Red.copy(alpha = 0.5f))
                                                            .fillMaxSize()
                                                            ,
                                         contentAlignment = Alignment.CenterEnd
                                         ){
                                         Text(text = "Delete Item",
                                             modifier = Modifier.padding(16.dp),
                                             color = Color.White)
                                     }
                        }
                        ,dismissContent = {
                            InstagramProfileCard(model) {
                                viewModel.changeFollowingStatus(model)
                            }
                        },
                        directions = setOf(DismissDirection.EndToStart)
                    )

                }
            }
           

        }


    }
}


