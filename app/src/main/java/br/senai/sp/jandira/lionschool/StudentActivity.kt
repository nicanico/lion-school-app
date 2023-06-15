package br.senai.sp.jandira.lionschool

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.senai.sp.jandira.lionschool.model.CoursesList
import br.senai.sp.jandira.lionschool.model.Registration
import br.senai.sp.jandira.lionschool.model.RegistrationList
import br.senai.sp.jandira.lionschool.model.Students
import br.senai.sp.jandira.lionschool.model.SubjectsList
import br.senai.sp.jandira.lionschool.service.RetrofitFactoryCourses
import br.senai.sp.jandira.lionschool.service.RetrofitFactoryStudent
import br.senai.sp.jandira.lionschool.ui.theme.LionSchoolTheme
import coil.compose.AsyncImage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StudentActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LionSchoolTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {

                    val matricula = intent.getStringExtra("matricula")
                    Log.i("NUMERO", "onResponse: $matricula")
                    StudentGradesActivity(matricula.toString())

                }
            }
        }
    }
}

@Composable
fun StudentGradesActivity(matricula: String) {

    Log.i("MATRICULA", "onResponse: ${matricula}")

    val context = LocalContext.current



    var call = RetrofitFactoryCourses().getLionSchoolServiceStudent().getAlunoDoCurso(matricula)

   var aluno by remember {
       mutableStateOf(Registration(
           "",
           "",
           "",
           "",
           "",
           "",
           "",
           "",
           emptyList()
       ))
   }
    var disciplina by remember {
        mutableStateOf(SubjectsList("", "", "", ""))
    }
    call.enqueue(object : Callback<Registration> {
        override fun onResponse(
            call: Call<Registration>,
            response: Response<Registration>
        ) {
            if(response.isSuccessful){
                val studentResponse = response.body()
               if(studentResponse !=null){
                   aluno = studentResponse
                   Log.i("ALUNO", "onResponse: ${aluno.disciplinas}")
                   val calc = sizeGrade(25)
                   Log.i("ALUNO", "onResponse: ${calc}")

               } else {
                   Log.i("ERRO", "onResponse: ${aluno}" )
               }
            }

        }

        override fun onFailure(call: Call<Registration>, t: Throwable) {

        }
    })
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 20.dp)
    ) {
        Button(onClick = {
            goHome(context)
        },
            modifier = Modifier
                .padding(start = 12.dp, bottom = 15.dp)
                .width(80.dp)
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(51, 71, 176))

            ) {
            Icon(
                painter = painterResource(id = R.drawable.go_home),
                contentDescription = " ",
                tint = Color(255, 255, 255, 255)

            )
        }
        Text(
            text = stringResource(id = R.string.date_students),
            modifier = Modifier.padding(start = 16.dp),
            color = Color(156, 156, 156)
        )
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            // verticalArrangement = Arrangement.Center
        ) {

            Surface(
                modifier = Modifier
                    .width(322.dp)
                    .height(113.dp)
                    .padding(top = 10.dp),
                color = Color(255, 194, 62),
                shape = RoundedCornerShape(25.dp)

            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    AsyncImage(
                        model = aluno.foto, contentDescription = "",
                        modifier = Modifier
                            .padding(end = 21.dp)
                            .height(96.dp)
                            .width(97.dp),
                        alignment = Alignment.Center,
                        contentScale = ContentScale.Crop
                    )
                    Text(
                        text = aluno.nome,
                        color = Color(51, 71, 176),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold

                    )
                }
            }
            Text(
                text = stringResource(id = R.string.title_grafic),
                modifier = Modifier.padding(top = 16.dp, end = 176.dp),
                color = Color(156, 156, 156),
                fontSize = 18.sp
            )
            // lazy column
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 30.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                items(aluno.disciplinas){
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 25.dp, top = 15.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = it.sigla ,
                            color = Color(51, 71, 176),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold

                        )
                        Surface(

                        ) {
                            Card(
                                modifier = Modifier
                                    .width(300.dp)
                                    .padding(start = 10.dp),
                                backgroundColor = Color(109, 107, 107, 255),
                                shape = RoundedCornerShape(25.dp)
                            ){
                                Text(text = "  ")
                            }

                            Card(
                                modifier = Modifier
                                    .width(sizeGrade(it.media.toInt()).dp)
                                    .padding(start = 10.dp),
                                backgroundColor = colorBackground(it.status),
                                shape = RoundedCornerShape(25.dp)
                            ){
                                Text(text = "  ")
                            }

                        }
                    }
                }



            }
        }
    }

}

fun goHome(context: Context){
    val openHome = Intent(context, HomeActivity()::class.java)
    context.startActivity(openHome)
}

fun colorBackground (status: String): Color{

    var colorAprovado = Color(76, 175, 80, 255)
    var colorExame = Color(255, 194, 62)
    var colorReprovado = Color(244, 67, 54, 255)

    if (status == "Aprovado"){
        return colorAprovado
    } else if (status == "Reprovado"){
        return colorReprovado
    } else {
        return colorExame
    }
}

fun sizeGrade (media: Int): Float{

    val valor_inicial = media.toFloat() / 100
    val calcular = 300 * valor_inicial
    Log.i("valor", "resultado: ${calcular}")

    return calcular

}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview4() {
    LionSchoolTheme {
        StudentGradesActivity("90")
    }
}