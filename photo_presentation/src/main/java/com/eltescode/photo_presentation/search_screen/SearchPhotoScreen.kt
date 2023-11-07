package com.eltescode.photo_presentation.search_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.eltescode.core_ui.R
import com.eltescode.core_ui.components.texFieldColors_2
import com.eltescode.core_ui.ui.GreenColors
import com.eltescode.core_ui.utils.UiEvent
import com.eltescode.photo_presentation.components.PhotoDialog
import com.eltescode.photo_presentation.components.PhotoItem
import com.eltescode.photo_presentation.utils.SearchPhotoEvent
import com.eltescode.photo_presentation.utils.SearchPhotoScreenState

@Composable
fun SearchPhotoScreen(
    viewModel: SearchPhotoViewModel = hiltViewModel(),
    onSuccess: (String) -> Unit
) {
    val state = viewModel.state

    LaunchedEffect(key1 = true, block = {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.ShowSnackBar -> {}
                is UiEvent.OnNextScreen -> {
                    event.route?.let { onSuccess(it) }
                }

                else -> Unit
            }
        }
    })
    SearchPhotoScreen(
        state = state,
        onEvent = viewModel::onEvent
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchPhotoScreen(
    state: SearchPhotoScreenState,
    onEvent: (SearchPhotoEvent) -> Unit
) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(65.dp)
                    .clip(CutCornerShape(bottomStart = 10.dp, bottomEnd = 30.dp))
                    .background(
                        Brush.linearGradient(
                            listOf(
                                GreenColors.color008c15,
                                GreenColors.colorCfea75,
                                Color.Companion.White
                            )
                        )
                    ),
                contentAlignment = Alignment.TopCenter
            ) {
                TextField(
                    value = state.query,
                    onValueChange = { onEvent(SearchPhotoEvent.OnQueryEntered(it)) },
                    trailingIcon = {
                        IconButton(
                            onClick = { onEvent(SearchPhotoEvent.OnPhotoSearch) },
                            content = {
                                Icon(
                                    imageVector = Icons.Default.Search,
                                    contentDescription = null
                                )
                            }
                        )
                    },
                    colors = texFieldColors_2(),
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text(text = stringResource(id = R.string.search_photo_hint)) }
                )
            }
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                items(state.photos) { photo ->
                    PhotoItem(
                        url = photo.urlS, modifier = Modifier
                            .size(75.dp)
                            .padding(5.dp)
                            .clickable { photo.urlS?.let { onEvent(SearchPhotoEvent.OnPhotoClick(it)) } }
                    )
                }
            }
        }

        if (state.photoDialogState.isDialogVisible) {
            PhotoDialog(
                photoUrl = state.photoDialogState.photoToSetProfilePicture,
                onDismiss = { onEvent(SearchPhotoEvent.OnPhotoDialogDismiss) },
                onButtonClick = {
                    onEvent(SearchPhotoEvent.OnSetProfilePhoto(it))
                },
                modifier = Modifier
            )
        }
    }
}


@Preview
@Composable
private fun SearchPhotoScreen() {
    SearchPhotoScreen(state = SearchPhotoScreenState()) {}
}