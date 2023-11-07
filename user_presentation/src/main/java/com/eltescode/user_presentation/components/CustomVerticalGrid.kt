package com.eltescode.user_presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import com.eltescode.user_presentation.utils.UserScreens


@Composable
fun CustomLazyVerticalGrid(
    modifier: Modifier = Modifier,
    cardData: Array<UserScreens>,
    content: @Composable (Int, String) -> Unit
) {
    LazyVerticalGrid(
        columns = object : GridCells {
            override fun Density.calculateCrossAxisCellSizes(
                availableSize: Int,
                spacing: Int
            ): List<Int> {
                val firstColumn = (availableSize - spacing) * 1 / 2
                val secondColumn = availableSize - spacing - firstColumn
                return listOf(firstColumn, secondColumn)
            }
        },
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier,
    ) {
        cardData.forEachIndexed { index, item ->
            if (index == 0) {
                item(span = { GridItemSpan(maxLineSpan) }) {
                    content(item.screenNameRes, item.route)
                }
            } else {
                item(span = { GridItemSpan(1) }) {
                    content(item.screenNameRes, item.route)
                }
            }
        }
    }
}

