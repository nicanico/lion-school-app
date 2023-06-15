package br.senai.sp.jandira.lionschool

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.senai.sp.jandira.lionschool.model.Course
import br.senai.sp.jandira.lionschool.model.CoursesList
import br.senai.sp.jandira.lionschool.model.StudentsList
import br.senai.sp.jandira.lionschool.service.RetrofitFactoryCourses
import br.senai.sp.jandira.lionschool.ui.theme.LionSchoolTheme
import coil.compose.AsyncImage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StudentsActivity() : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LionSchoolTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val sigla = intent.getStringExtra("sigla")
                    Log.i("SIGLA", "onResponse: $sigla")
                    StudentsLionSchool(sigla.toString())

                }
            }
        }
    }
}

@Composable
fun StudentsLionSchool(sigla: String) {

    val context = LocalContext.current

    var listStudent by remember {
        mutableStateOf(listOf<br.senai.sp.jandira.lionschool.model.Students>())
    }

    var titleCouse by remember {
        mutableStateOf("")
    }

    var searchState by remember {
        mutableStateOf("")
    }


    var call = RetrofitFactoryCourses().getLionSchoolService().getAlunosDoCurso(sigla = sigla)

    call.enqueue(object : Callback<StudentsList> {
        override fun onResponse(
            call: Call<StudentsList>,
            response: Response<StudentsList>
        ) {
            listStudent = response.body()!!.alunos

            titleCouse = listStudent[1].curso
            titleCouse.split("00", "-")
            Log.i("ds2t", "onResponse: ${titleCouse}")
            Log.i("ds2t", "onResponse: ${listStudent}")
        }

        override fun onFailure(call: Call<StudentsList>, t: Throwable) {

        }
    })
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 27.dp)
        ) {
            Row() {
                OutlinedTextField(value = searchState, onValueChange = {
                    searchState = it
                    Log.i("ds2t", "onResponse: $it")
                }, modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 19.dp, end = 19.dp),
                    shape = RoundedCornerShape(size = 30.dp),
                    label = { Text(text = stringResource(id = R.string.input_search_label_student))},
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_search),
                            contentDescription = "",
                            )
                    }
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = titleCouse,
                    fontSize = 29.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(51, 71, 176),
                    textAlign = TextAlign.Center
                )

                Text(
                    text = stringResource(id = R.string.title_students),
                    modifier = Modifier.padding(12.dp),
                    color = Color(255, 194, 62),
                    fontSize = 15.sp
                )
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    items(listStudent){
                        Log.i("ds2t", "onResponse: ${it.matricula}")
                        val matricula = it.matricula
                        Button(onClick = {
                            Log.i("ds2t", "onResponse: ${it.nome}")
                            openStudend(context, matricula)

                        }, modifier = Modifier
                            .height(150.dp)
                            .fillMaxWidth()
                            .padding(bottom = 15.dp, start = 20.dp, end = 20.dp),
                            colors = ButtonDefaults.buttonColors(backgroundColor = checkStatus(it.status)),
                            shape = RoundedCornerShape(20.dp),

                        ) {
                            Row(
                                modifier = Modifier.fillMaxSize(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                AsyncImage(
                                    model = it.foto, contentDescription = "",
                                    modifier = Modifier.height(116.dp).width(117.dp),
                                    alignment = Alignment.Center,
                                    contentScale = ContentScale.Crop
                                )
                                Text(
                                    text = it.nome,
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 21.sp,
                                    textAlign = TextAlign.Center
                                )
                            }

                        }
                    }
                }

            }

        }

}


fun checkStatus(status: String): Color {
    var statusAluno = status.toString()
    var colorFinalizado = Color(255, 194, 62)
    var colorCursando = Color(51, 71, 176)

    if(statusAluno == "Cursando"){
        return colorCursando
    } else {
        return colorFinalizado
    }
}

fun openStudend(context: Context, matricula: String){
    val openStudend = Intent(context, StudentActivity()::class.java)
    openStudend.putExtra("matricula", matricula.toString())
    context.startActivity(openStudend)
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview3() {
    LionSchoolTheme {
        StudentsLionSchool(sigla = "ds")
    }
}