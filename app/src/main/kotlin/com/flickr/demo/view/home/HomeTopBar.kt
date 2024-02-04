package com.flickr.demo.view.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.flickr.demo.common.scalars.Tags
import com.flickr.demo.common.view.R

@Composable
fun HomeTopBar(searchTags: Tags, onQueryChange: (String) -> Unit, modifier: Modifier = Modifier) {
    CenterAlignedTopAppBar(
        title = {
            SearchBar(
                query = searchTags.string,
                onQueryChange = onQueryChange,
                onSearch = { },
                active = false,
                onActiveChange = { },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.TwoTone.Search,
                        contentDescription = stringResource(id = R.string.search),
                    )
                },
                content = { },
            )
        },
        modifier = modifier,
    )
}
