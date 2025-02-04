package com.example.jomdining.ui

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.jomdining.R
import com.example.jomdining.data.TempMenuItems
import com.example.jomdining.databaseentities.Menu
import java.io.InputStream
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodOrderingModuleScreen(
    viewModel: JomDiningViewModel,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        topBar = {
            JomDiningTopAppBar(
                title = "JomDining"
            )
        },
        containerColor = Color(0xFFCEDFFF)
    ) { innerPadding ->
        Column(
            modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Row(
                modifier = modifier.fillMaxSize()
            ) {
                Box(
                    modifier = Modifier
                        .weight(0.6f)
                        .fillMaxSize()
                ) {
                    Spacer(modifier = Modifier.height(16.dp))
                    MenuItemGrid(
                        viewModel = viewModel,
                        modifier = modifier
                    )
                }
                SecondItem(
                    modifier = Modifier
                        .weight(0.4f)
                        .fillMaxHeight()
                )
            }
        }
    }
}

@Composable
fun MenuItemGrid(
    viewModel: JomDiningViewModel,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color(0xFFCEDFFF)
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = modifier
            .background(backgroundColor)
    ) {
        items(viewModel.menuUi.menuItems) { menuItem ->
            MenuItemCard(menuItem)
        }
    }
}

@Composable
fun MenuItemCard(
    menuItem: Menu,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = modifier.padding(
            start = 16.dp,
            end = 16.dp,
            top = 16.dp,
            bottom = 16.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        )
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                val imagePath = menuItem.menuItemImagePath
                Log.d("IMAGE_PATH", "Current image path is $imagePath")
                val assetManager = LocalContext.current.assets
                val inputStream: InputStream?
                try {
                    inputStream = assetManager.open(imagePath)
                    Log.d("IMAGE_FILE", "Image file exists in assets: $inputStream")
                } catch (e: Exception) {
                    Log.e("IMAGE_FILE_ERROR", "Image file does not exist in assets: $e")
                }
                Image(
                    painter = rememberAsyncImagePainter(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data("file:///android_asset/$imagePath")
                            .build()
                    ),
                    contentDescription = menuItem.menuItemName,
                    modifier = modifier
                        .padding(bottom = 4.dp)
                        .size(width = 120.dp, height = 120.dp)
                        .aspectRatio(1f)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            }
            Row {
                Text(
                    text = stringResource(
                        R.string.menu_item_name_placeholder,
                        menuItem.menuItemName
                    ),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(
                        top = 8.dp,
                        bottom = 8.dp
                    )
                )
            }
            Row {
                Text(
                    text = String.format(Locale.getDefault(), "RM %.2f", menuItem.menuItemPrice),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    color = Color(74, 22, 136)
                )
            }
        }
    }
}

//@Composable
//fun OrderItemCard(
//    orderItem: OrderItem,
//    modifier: Modifier = Modifier
//) {
//    Card {
//        Row {
//            Box {
//                /*
//                                Image {
//                    // painter = painterResource(id = )
//                }
//                 */
//            }
//        }
//    }
//}

@Composable
fun SecondItem(
    modifier: Modifier
) {
    Box(
        modifier = modifier
            .background(Color.Black)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Second Item")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JomDiningTopAppBar(
    title: String,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = {
            Text(title)
        },
        modifier = modifier,
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary
        )
    )
}

@Preview
@Composable
fun MenuItemCardPreview() {
    val menuItem =
        Menu(
            menuItemID = 1,
            menuItemName = stringResource(R.string.chicken_chop),
            menuItemPrice = 12.34,
            menuItemType = stringResource(R.string.main_course),
            menuItemImagePath = "file:///android_asset/chickenChop.png"
        )
    MenuItemCard(
        menuItem = menuItem,
    )
}

@Composable
fun TestMenuItemGrid(
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color(0xFFCEDFFF)
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        // verticalArrangement = Arrangement.spacedBy(0.dp),
        // horizontalArrangement = Arrangement.spacedBy(24.dp),
        modifier = modifier
            .background(backgroundColor)
    ) {
        items(TempMenuItems.menuItems) { menuItem ->
            MenuItemCard(menuItem)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(
    name = "pixel_tablet_landscape",
    device = "spec: width=2560dp, height=1600dp, orientation=landscape",
    showBackground = true,
    backgroundColor = 0xCEDFFF
)
@Composable
fun FoodOrderingModulePreview(
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            JomDiningTopAppBar(
                title = "JomDining"
            )
        },
        containerColor = Color(0xFFCEDFFF)
    ) { innerPadding ->
        Column(
            modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Row(
                modifier = modifier.fillMaxSize()
            ) {
                Box(
                    modifier = Modifier
                        .weight(0.6f)
                        .fillMaxSize()
                ) {
                    TestMenuItemGrid(modifier = modifier)
                }
                SecondItem(
                    modifier = Modifier
                        .weight(0.4f)
                        .fillMaxSize()
                )
            }
        }
    }
}