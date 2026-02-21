package com.dino.message.threadfeature.presentation.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.carousel.HorizontalMultiBrowseCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.dino.message.threadfeature.domain.model.PostMedia

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Carousel(
    modifier: Modifier = Modifier,
    items: List<PostMedia>,
) {
    if (items.isEmpty()) return

    HorizontalMultiBrowseCarousel(
        state = rememberCarouselState { items.count() },
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(top = 16.dp, bottom = 16.dp),
        preferredItemWidth = 186.dp,
        itemSpacing = 8.dp,
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) { i ->
        val item = items[i]
        AsyncImage(
            modifier = Modifier
                .height(205.dp)
                .maskClip(MaterialTheme.shapes.extraLarge),
            model = item.url,
            contentDescription = "Post media ${item.id}",
            contentScale = ContentScale.Crop
        )
    }
}
