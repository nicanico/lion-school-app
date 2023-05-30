package br.senai.sp.jandira.lionschool

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
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
                    StudentsLionSchool()
                }
            }
        }
    }
}

@Composable
fun StudentsLionSchool() {

    var listStudent by remember {
        mutableStateOf(listOf<br.senai.sp.jandira.lionschool.model.Students>())
    }

    var call = RetrofitFactoryCourses().getLionSchoolService().getAlunosDoCurso(sigla = "DS")

    call.enqueue(object : Callback<StudentsList> {
        override fun onResponse(
            call: Call<StudentsList>,
            response: Response<StudentsList>
        ) {
            listStudent = response.body()!!.alunos
            Log.i("ds2t", "onResponse: $listStudent")

        }

        override fun onFailure(call: Call<StudentsList>, t: Throwable) {

        }
    })

    Text(text = "AAAAAA")
}

fun queroSigla(sigla: String){
    Log.i("Sigla", sigla)
    var si = sigla
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview3() {
    LionSchoolTheme {
        StudentsLionSchool()
    }
}