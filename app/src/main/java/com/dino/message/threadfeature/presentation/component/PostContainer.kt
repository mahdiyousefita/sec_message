package com.dino.message.threadfeature.presentation.component

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun PostContainer(
    modifier: Modifier = Modifier,
    content: @Composable (ColumnScope.() -> Unit)
) {
    Card(
        modifier = modifier
    ) {
        content()
    }
}

@Preview(showSystemUi = true)
@Composable
private fun Preview(){
    PostContainer(
    ){
        Text("Hello World")
    }
}