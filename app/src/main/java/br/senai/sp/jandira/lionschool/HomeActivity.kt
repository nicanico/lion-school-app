package br.senai.sp.jandira.lionschool

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.senai.sp.jandira.lionschool.model.CoursesList
import br.senai.sp.jandira.lionschool.service.RetrofitFactoryCourses
import br.senai.sp.jandira.lionschool.ui.theme.LionSchoolTheme
import coil.compose.AsyncImage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LionSchoolTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    HomeShcool()
                }
            }
        }
    }
}

@Composable
fun HomeShcool() {

    var searchState by remember {
        mutableStateOf("")
    }

    var listCourse by remember {
        mutableStateOf(listOf<br.senai.sp.jandira.lionschool.model.Course>())
    }

    var siglaDoCurso by remember {
        mutableStateOf("")
    }

    val context = LocalContext.current

    var call = RetrofitFactoryCourses().getLionSchoolService().getCursos()

    call.enqueue(object : Callback<CoursesList> {
        override fun onResponse(
            call: Call<CoursesList>,
            response: Response<CoursesList>
        ) {
            listCourse = response.body()!!.curso
            Log.i("ds2t", "onResponse: $listCourse")

        }

        override fun onFailure(call: Call<CoursesList>, t: Throwable) {

        }
    })

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 27.dp)
        // horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Row() {
            OutlinedTextField(value = searchState, onValueChange = {
                searchState = it
                Log.i("ds2t", "onResponse: $it")
            }, modifier = Modifier
                .fillMaxWidth()
                .padding(start = 19.dp, end = 19.dp ),
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
            modifier = Modifier.padding(start = 19.dp, top = 28.dp),
        ) {
                Text(
                    text = stringResource(id = R.string.home_welcome),
                    color = Color(156, 156, 156),
                    fontSize = 24.sp
                )
                Text(
                    text = stringResource(id = R.string.home_description),
                    color = Color(156, 156, 156),
                    fontSize = 15.sp

                )

            }

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

        }
        Column(
            modifier = Modifier.padding(top = 46.dp)
        ) {
            Text(
                text = stringResource(id = R.string.home_courses),
                modifier = Modifier.padding(start = 19.dp),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(255, 194, 62)
            )
            Row( modifier = Modifier
                .height(1.dp)
                .fillMaxWidth()
                .padding(start = 15.dp, end = 24.dp)
                .background(
                    color = Color(255, 194, 62)
                )
            ) {
            }
            LazyColumn(
                modifier = Modifier.fillMaxWidth().padding(top = 15.dp),
                horizontalAlignment = Alignment.CenterHorizontally,

            ){
                items(listCourse){
                    Button(
                        onClick = {
                            Log.i("ds2t", "onResponse: ${it.sigla}")
                            siglaDoCurso = it.sigla

                            openStudends(context, siglaDoCurso)
                        },
                        modifier = Modifier
                            .width(380.dp)
                            .height(150.dp)
                            .padding(vertical = 4.dp),
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color(51, 71, 176))

                        ) {
                        Row() {
                            AsyncImage(
                                model = it.icone, contentDescription = "",
                                modifier = Modifier.padding(end = 21.dp),
                                colorFilter = ColorFilter.tint(Color.White)
                            )
                            Column() {
                                Text(
                                    text = it.sigla,
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 40.sp
                                )
                                Text(
                                    text = it.nome,
                                    color = Color.White,
                                    fontSize = 13.sp
                                )
                                Row() {
                                    Text(
                                        text = stringResource(id = R.string.charge_courses),
                                        color = Color.White,
                                        fontSize = 13.sp
                                    )
                                    Text(
                                        text =  it.carga,
                                        color = Color.White,
                                        fontSize = 13.sp
                                    )
                                    Text(
                                        text =  stringResource(id = R.string.hours_courses),
                                        color = Color.White,
                                        fontSize = 13.sp
                                    )
                                }

                            }
                        }
                    }
                }
            }
        }

    }
}

fun openStudends(context: Context, siglaDoCurso: String){
    val openStuden = Intent(context, StudentsActivity()::class.java)
    openStuden.putExtra("sigla", siglaDoCurso.toString())
    context.startActivity(openStuden)
}




@Preview(showBackground = true)
@Composable
fun DefaultPreview2() {
    LionSchoolTheme {
        HomeShcool()
    }
}