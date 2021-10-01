package com.ianschoenrock.gnomelauncher

import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.drawablepainter.rememberDrawablePainter
import com.ianschoenrock.gnomelauncher.ui.theme.GnomeLauncherTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HomePage()
        }
    }
}

@Composable
fun HomePage() {
    val context = LocalContext.current
    val pManager = context.packageManager
    val mainIntent = Intent(Intent.ACTION_MAIN, null)
    mainIntent.addCategory(Intent.CATEGORY_LAUNCHER)
    val myApps = pManager.queryIntentActivities(mainIntent, 0)
    LazyColumn(modifier = Modifier.fillMaxSize().background(Color.Transparent)){
        items(myApps.count()){ index ->
            Image(
                painter = rememberDrawablePainter(drawable = myApps[index].activityInfo.loadIcon(pManager)),
                contentDescription = "app_icon",
                modifier = Modifier.padding(16.dp).clickable {
                    val clickedIntent = pManager.getLaunchIntentForPackage(myApps[index].activityInfo.packageName.toString())
                    println(myApps[index].activityInfo.packageName)
                    context.startActivity(clickedIntent)
                }
            )
        }
       // myApps.forEach { app ->

//            Text(app.loadLabel(pManager).toString(),style = TextStyle(fontSize = 24.sp), modifier = Modifier.padding(16.dp).clickable {
//                val clickedIntent = pManager.getLaunchIntentForPackage(app.activityInfo.packageName.toString())
//                println(app.activityInfo.packageName)
//                context.startActivity(clickedIntent)
//            })
        //}
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    GnomeLauncherTheme {
        HomePage()
    }
}