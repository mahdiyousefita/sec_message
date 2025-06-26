package com.dino.order.profilefeature.presentation.component

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp

@Composable
fun PillShapeCard(
    colors: CardColors = CardDefaults.cardColors(),
    elevation: CardElevation = CardDefaults.cardElevation(),
    border: BorderStroke? = null,
    onclick: () -> Unit,
    content: @Composable (ColumnScope.() -> Unit)
){
    Card(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 80.dp)
            .size(900.dp, 490.dp)
            .graphicsLayer(
                rotationZ = 40f,
                clip = true
            ),
        shape = RoundedCornerShape(percent = 50),
        colors = colors,
        elevation = elevation,
        border = border
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()

                .clickable(onClick = onclick)
        ){
            Column(modifier = Modifier.fillMaxSize()) {
                content()
            }
        }
    }
}