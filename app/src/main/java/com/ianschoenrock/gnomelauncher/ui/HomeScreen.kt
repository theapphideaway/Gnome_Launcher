package com.ianschoenrock.gnomelauncher.ui

import android.content.Intent
import android.content.pm.ResolveInfo
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.drawablepainter.rememberDrawablePainter
import com.ianschoenrock.gnomelauncher.R
import com.ianschoenrock.gnomelauncher.ui.theme.darkCard

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(){
    val showAppDrawer = remember { mutableStateOf(false) }
    val showMenu = remember { mutableStateOf(false)}
    val selectedIndex = remember { mutableStateOf(0)}
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
    if(!showAppDrawer.value) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.weight(1f),)
            Box(
                modifier = Modifier.background(Color.Transparent).clip(RoundedCornerShape(16.dp, 16.dp, 0.dp, 0.dp))) {
                Row(modifier = Modifier.background(darkCard.copy(alpha = 0.6f)),
                    horizontalArrangement = Arrangement.Center
                ) {
                    dockApps.forEach {
                        Image(
                            painter = rememberDrawablePainter(
                                drawable = it.activityInfo.loadIcon(
                                    pManager
                                )
                            ),
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
                    Column(verticalArrangement = Arrangement.Center) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_baseline_apps_24),
                            contentDescription = "",
                            modifier = Modifier
                                .size(100.dp)
                                .padding(8.dp)
                                .clickable {
                                    showAppDrawer.value = true
                                }
                        )
                    }
                }
            }
        }
    }
    if(showAppDrawer.value){
        Column(
            Modifier.fillMaxSize()
                .background(darkCard.copy(alpha = 0.6f))
                .padding(16.dp)
                .clickable {
                    showAppDrawer.value = false
                }, horizontalAlignment = Alignment.CenterHorizontally){

            LazyVerticalGrid(
                cells = GridCells.Adaptive(minSize = 120.dp)
            ) {
                items(myApps.size) { index ->
                    Row() {
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Image(
                                painter = rememberDrawablePainter(
                                    drawable = myApps[index].activityInfo.loadIcon(
                                        pManager
                                    )
                                ),
                                contentDescription = "app_icon",
                                modifier = Modifier
                                    .padding(16.dp)
                                    .size(60.dp)
                                    .clickable {
                                        val clickedIntent =
                                            pManager.getLaunchIntentForPackage(myApps[index].activityInfo.packageName.toString())
                                        println(myApps[index].activityInfo.packageName)
                                        context.startActivity(clickedIntent)
                                    }.pointerInput(Unit) {
                                        detectTapGestures(
                                            onLongPress = {
                                                selectedIndex.value = index
                                                showMenu.value = true
                                            }
                                        )
                                    }
                            )
                            Text(
                                myApps[index].loadLabel(pManager).toString(),
                                style = TextStyle(
                                    fontSize = 18.sp,
                                    textAlign = TextAlign.Center,
                                    color = Color.White
                                ),
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                    if(showMenu.value && selectedIndex.value == index){
                        DropdownMenu(
                            expanded = showMenu.value,
                            onDismissRequest = { showMenu.value = false }
                        ) {
                            DropdownMenuItem(onClick = {}) {
                                Text("Add to Home")
                            }
                            DropdownMenuItem(onClick = {}) {
                                Text("Info")
                            }
                            Divider()
                            DropdownMenuItem(onClick = {}) {
                                Text("Uninstall")
                            }
                        }
                    }

                }
            }
        }
    }
}