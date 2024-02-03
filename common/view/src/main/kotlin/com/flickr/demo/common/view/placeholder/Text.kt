package com.flickr.demo.common.view.placeholder

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color

@Composable
fun TextWithShimmer(text: String?, shimmer: Boolean, modifier: Modifier = Modifier) {
    Text(
        text = text ?: "",
        modifier = modifier
            .fillMaxWidth()
            .smallPlaceholder(shimmer),
    )
}

@SuppressLint("ComposeModifierComposed")
fun Modifier.smallPlaceholder(visible: Boolean): Modifier {
    return composed {
        placeholder(
            visible = visible,
            color = Color.Transparent,
            shape = MaterialTheme.shapes.small,
            highlight = PlaceholderHighlight.shimmer(
                highlightColor = LocalContentColor.current.copy(alpha = .5f)
            )
        )
    }
}
