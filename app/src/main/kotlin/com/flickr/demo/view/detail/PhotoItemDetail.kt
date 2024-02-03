package com.flickr.demo.view.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.flickr.demo.common.data.PhotoItem
import com.flickr.demo.common.ui.onClick
import com.flickr.demo.common.ui.rememberAnnotatedString
import com.flickr.demo.common.ui.rememberDatetime

@Composable
fun PhotoItemDetail(
    photoItem: PhotoItem,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        AsyncImage(
            model = photoItem.media.url.string,
            contentDescription = photoItem.description.string,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier.fillMaxWidth(),
        )

        Column(
            modifier = Modifier.padding(horizontal = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(text = photoItem.title.string)

            val published = rememberAnnotatedString(
                htmlText = photoItem.description.string,
                linkColor = MaterialTheme.colorScheme.primary,
            )

            val context = LocalContext.current

            ClickableText(
                text = published,
                onClick = { offset ->
                    published.onClick(context = context, offset = offset)
                },
            )

            Text(text = photoItem.author.string)

            Text(text = rememberDatetime(photoItem.published.string))
        }
    }
}
