package br.senai.sp.jandira.lionschool

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
                    .width(273.dp)
                    .padding(start = 19.dp),
                    shape = RoundedCornerShape(size = 30.dp),
                    label = { Text(text = stringResource(id = R.string.input_search_label))},
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
                    .fillMaxSize(),
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
                    color = Color(255, 194, 62),
                    fontSize = 15.sp
                )
            }



        }

}




@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview3() {
    LionSchoolTheme {
        StudentsLionSchool(sigla = "ds")
    }
}