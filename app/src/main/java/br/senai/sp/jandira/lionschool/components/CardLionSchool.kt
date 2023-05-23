package br.senai.sp.jandira.lionschool.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CardLion(title: String,
             description: String
        ) {
    Surface( modifier = Modifier
        .height(169.dp)
        .width(148.dp),
        color = Color(255, 194, 62),
        shape = RoundedCornerShape(size = 15.dp),

    ) {
        Column( modifier = Modifier
            .padding(
                top = 12.dp,
                start = 10.dp,
                end = 15.dp
            )
        ) {
            Text(
                text = title,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
            Text(
                text = description,
                fontSize = 10.sp,
                color = Color.White
            )
        }
    }
}

@Preview
@Composable
fun CardPreviw() {
    CardLion("Instituição Reconhecida", "Com equipamentos de ultima geração.")
}