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
import br.senai.sp.jandira.lionschool.model.CoursesList
import br.senai.sp.jandira.lionschool.model.Registration
import br.senai.sp.jandira.lionschool.model.RegistrationList
import br.senai.sp.jandira.lionschool.model.Students
import br.senai.sp.jandira.lionschool.service.RetrofitFactoryCourses
import br.senai.sp.jandira.lionschool.service.RetrofitFactoryStudent
import br.senai.sp.jandira.lionschool.ui.theme.LionSchoolTheme
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

    call.enqueue(object : Callback<Registration> {
        override fun onResponse(
            call: Call<Registration>,
            response: Response<Registration>
        ) {
            if(response.isSuccessful){
                val studentResponse = response.body()
               if(studentResponse !=null){
                   aluno = studentResponse
                   Log.i("ALUNO", "onResponse: ${aluno.nome}")
               } else {
                   Log.i("ERRO", "onResponse: ${aluno}" )
               }
            }

        }

        override fun onFailure(call: Call<Registration>, t: Throwable) {

        }
    })
    Text(text = "AAAAAAAAA")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview4() {
    LionSchoolTheme {
        StudentGradesActivity("90")
    }
}