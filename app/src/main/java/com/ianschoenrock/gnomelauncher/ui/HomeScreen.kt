package com.ianschoenrock.gnomelauncher.ui

import android.content.Intent
import android.content.pm.ResolveInfo
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.drawablepainter.rememberDrawablePainter
import com.ianschoenrock.gnomelauncher.R
import com.ianschoenrock.gnomelauncher.ui.theme.darkCard

@Composable
fun HomeScreen(){
    val context = LocalContext.current
    val pManager = context.packageManager
    val mainIntent = Intent(Intent.ACTION_MAIN, null)
    mainIntent.addCategory(Intent.CATEGORY_LAUNCHER)
    val myApps = pManager.queryIntentActivities(mainIntent, 0)
    var count = 0
    val dockApps = ArrayList<ResolveInfo>()
    myApps.forEach {
        if(count < 5){
            count++
            dockApps.add(it)
        }
    }
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.weight(1f))
        Card(shape = RoundedCornerShape(16.dp, 16.dp, 0.dp, 0.dp),){
            Row(modifier = Modifier.background(darkCard), horizontalArrangement = Arrangement.Center){
                dockApps.forEach {
                    Image(
                        painter = rememberDrawablePainter(drawable = it.activityInfo.loadIcon(pManager)),
                        contentDescription = "app_icon",
                        modifier = Modifier
                            .padding(16.dp)
                            .clickable {
                                val clickedIntent =
                                    pManager.getLaunchIntentForPackage(it.activityInfo.packageName.toString())
                                println(it.activityInfo.packageName)
                                context.startActivity(clickedIntent)
                            }
                    )

                }
                Column( verticalArrangement = Arrangement.Center){
                    Image(
                        painter = painterResource(id = R.drawable.ic_baseline_apps_24),
                        contentDescription = "",
                        modifier = Modifier.size(100.dp).padding(8.dp)
                    )
                }

            }
        }

    }

}