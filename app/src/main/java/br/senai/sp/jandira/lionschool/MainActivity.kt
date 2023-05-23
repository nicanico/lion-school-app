package br.senai.sp.jandira.lionschool

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.senai.sp.jandira.lionschool.components.CardLion
import br.senai.sp.jandira.lionschool.ui.theme.LionSchoolTheme
import br.senai.sp.jandira.lionschool.ui.theme.Shapes
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LionSchoolTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    HomeLionSchool()
                }
            }
        }
    }
}

@Composable
fun HomeLionSchool() {

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(51, 71, 176))
            .padding(top = 57.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier
                    .size(170.dp)
                    .padding(start = 30.dp, end = 16.dp),
                painter = painterResource(id = R.drawable.lion_school),
                contentDescription = "",

            )
            Column(
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Lion School",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Row( modifier = Modifier
                    .height(1.dp)
                    .width(151.dp)
                    .background(color = Color(255, 194, 62)),
                    ) {
                    }
                Text(
                    text = "Seja bem vindo a maior escola do Brasil com diversos cursos em areas de tecnologia.",
                    modifier = Modifier.padding(end = 65.dp),
                    color = Color(217,217,217),
                    fontSize = 12.sp
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(19.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            CardLion(
                title = "Instituição Reconhecida",
                description = "Com equipamentos de ultima geração."
            )
            CardLion(
                title = "500+ \n" +
                        "Núcleos de Tecnolocia",
                description = "Todos espalhados pelo Brasil"
            )
        }

        Button(
            onClick = {
                openHome(context)
            },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(255, 194, 62)),
            shape = RoundedCornerShape(25.dp)
        )
        {
            Text(
                text = "Comece a Estudar",
                modifier = Modifier.padding(5.dp),
                color = Color(51, 71, 176)
            )
            Card(
                modifier = Modifier
                    .height(30.dp)
                    .width(30.dp),
                shape = RoundedCornerShape(20.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.arrow_start),
                    contentDescription = " ",
                    tint = Color(51, 71, 176)
                )


            }

        }
        // links de reporte
        Row() {
            
        }

        // Redes Sociais
        Row( 
            modifier = Modifier
                .padding(bottom = 35.dp),

        ) {
            Image(
                painter = painterResource(id = R.drawable.facebook),
                contentDescription = "",
                modifier = Modifier.padding(end = 31.dp).size(23.dp)
            )
            Image(
                painter = painterResource(id = R.drawable.instagram),
                contentDescription = "",
                modifier = Modifier.padding(end = 31.dp).size(23.dp)
            )
            Image(
                painter = painterResource(id = R.drawable.twitter),
                contentDescription = "",
                modifier = Modifier.size(23.dp)
            )
        }

    }


}
fun openHome( context: Context){
    val openHome = Intent(context, HomeActivity::class.java)
    context.startActivity(openHome)
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview() {
    LionSchoolTheme {
        HomeLionSchool()
    }
}