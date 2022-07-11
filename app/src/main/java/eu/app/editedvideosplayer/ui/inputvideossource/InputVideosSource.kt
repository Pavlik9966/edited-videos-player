package eu.app.editedvideosplayer.ui.inputvideossource

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import org.koin.androidx.compose.getViewModel

@Composable
fun InputVideosSource(navController: NavHostController) {

    val inputVideosSourceViewModel: InputVideosSourceViewModel = getViewModel()

    Scaffold(
        backgroundColor = Color.White,
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                backgroundColor = Color.Gray,
                title = {
                    Text(text = "Input videos source")
                })
        }, content = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .background(Color.LightGray)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center
            ) {

                val context = LocalContext.current

                Button(
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Gray,
                        contentColor = Color.Black
                    ),
                    modifier = Modifier.width(150.dp),
                    onClick = {
                        navController.navigate("inputVideosList")
                    },
                    shape = RoundedCornerShape(8.dp),
                ) {
                    Icon(
                        contentDescription = null,
                        imageVector = Icons.Outlined.Home,
                        modifier = Modifier.padding(end = 4.dp)
                    )
                    Text(text = "Local")
                }
                Button(
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Gray,
                        contentColor = Color.Black
                    ),
                    modifier = Modifier.width(150.dp),
                    onClick = {
                        Toast.makeText(context, "Remote soon.", Toast.LENGTH_SHORT).show()
                    },
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Icon(
                        contentDescription = null,
                        imageVector = Icons.Outlined.Settings,
                        modifier = Modifier.padding(end = 4.dp)
                    )
                    Text(text = "Remote")
                }
            }
        }
    )
}