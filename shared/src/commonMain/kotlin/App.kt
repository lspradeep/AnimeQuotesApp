import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import api.ApiService
import com.lt.compose_views.compose_pager.ComposePager
import com.lt.compose_views.compose_pager.rememberComposePagerState
import com.lt.compose_views.menu_fab.MenuFabItem
import com.lt.compose_views.menu_fab.MenuFloatingActionButton
import com.pradeep.animequotes.MR
import data.QuotesItem
import dev.icerock.moko.resources.compose.painterResource
import io.ktor.client.HttpClient
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.ExperimentalResourceApi

@OptIn(ExperimentalResourceApi::class)
@Composable
fun App() {
    MaterialTheme {
        val images = remember {
            listOf(
                MR.images.goku,
                MR.images.itachi,
                MR.images.zoro
            )
        }
        val pagerState = rememberComposePagerState()
        var currentPageIndex by remember { mutableStateOf(0) }
        var quotesItem by remember { mutableStateOf<QuotesItem?>(null) }
        val apiService = ApiService()

        LaunchedEffect(Unit) {
            while (true) {
                delay(10000)
                if (currentPageIndex == images.lastIndex) {
                    currentPageIndex = 0;
                } else {
                    currentPageIndex++;
                }
                pagerState.setPageIndexWithAnimate(currentPageIndex)
            }
        }

        LaunchedEffect(Unit) {
            val q = apiService.getRandomQuote()
            if (q != null) {
                quotesItem = q
            }
        }
//        var greetingText by remember { mutableStateOf("Hello, World!") }
//        var showImage by remember { mutableStateOf(false) }
//        Column(Modifier.fillMaxSize().background(color = Color.Magenta), horizontalAlignment = Alignment.CenterHorizontally) {
//            Button(onClick = {
//                greetingText = "Hello, ${getPlatformName()}"
//                showImage = !showImage
//            }) {
//                Text(greetingText)
//            }
//            AnimatedVisibility(showImage) {
////                Image(
////                    painterResource("compose-multiplatform.xml"),
////                    null
////                )
//                ImageViewer(
//                    painter = painterResource("zoro.jpg")
//                )
//            }
//        }

        Scaffold(floatingActionButton = {
            MenuFloatingActionButton(
                srcIcon = Icons.Default.MoreVert,
                items = listOf
                    (
                    MenuFabItem(icon = {
                        Icon(imageVector = Icons.Default.List, contentDescription = null)
                    }, "View Favourites"),
                    MenuFabItem(icon = {
                        Icon(imageVector = Icons.Default.Share, contentDescription = null)
                    }, "Share Quote"),
                    MenuFabItem(icon = {
                        Icon(imageVector = Icons.Default.Favorite, contentDescription = null)
                    }, "Add To Favourites")
                ),
                onFabItemClicked = {

                })
        }) {
            Box(
                modifier = Modifier.fillMaxSize().background(color = Color.Black),
                contentAlignment = Alignment.Center
            ) {
                ComposePager(
                    images.size,
                    userEnable = false,
                    composePagerState = pagerState,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Image(
                        painter = painterResource(images[this.index]),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }

                Box(
                    modifier = Modifier.fillMaxSize().align(alignment = Alignment.Center)
                        .background(color = Color.Black.copy(alpha = 0.5f))
                )

                if (quotesItem != null && !quotesItem?.quote.isNullOrBlank()) {
                    Column(
                        modifier = Modifier.align(alignment = Alignment.Center).padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        if (!quotesItem?.anime.isNullOrBlank()) {
                            Text(
                                text = quotesItem?.anime ?: "", style = TextStyle().copy(
                                    fontSize = TextUnit(
                                        20f,
                                        TextUnitType.Sp
                                    ),
                                    color = Color.White,
                                    fontWeight = FontWeight.SemiBold,
                                    fontFamily = FontFamily.Monospace, textAlign = TextAlign.Center
                                )
                            )
                        }

                        Text(
                            text = "\"${quotesItem?.quote}\"", style = TextStyle().copy(
                                fontSize = TextUnit(
                                    20f,
                                    TextUnitType.Sp
                                ),
                                color = Color.White,
                                fontWeight = FontWeight.ExtraBold,
                                fontFamily = FontFamily.Monospace,
                                fontStyle = FontStyle.Italic
                            )
                        )

                        if (!quotesItem?.character.isNullOrBlank()) {
                            Text(
                                text = "- ${quotesItem?.character}", style = TextStyle().copy(
                                    fontSize = TextUnit(
                                        18f,
                                        TextUnitType.Sp
                                    ),
                                    color = Color.White,
                                    fontWeight = FontWeight.Normal,
                                    fontFamily = FontFamily.Monospace
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}

expect fun getPlatformName(): String

expect fun getHttpClient(): HttpClient

//https://github.com/ltttttttttttt/ComposeViews
//https://github.com/qdsfdhvh/compose-imageloader