package br.senai.sp.jandira.lionschool.service

import br.senai.sp.jandira.lionschool.model.CoursesList
import br.senai.sp.jandira.lionschool.model.Registration
import br.senai.sp.jandira.lionschool.model.RegistrationList
import br.senai.sp.jandira.lionschool.model.StudentsList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface LionSchoolService {

    @GET("cursos")
    fun getCursos (): Call<CoursesList>

    @GET("alunos")
    fun getAlunosDoCurso (@Query("curso") sigla: String): Call<StudentsList>

    @GET("alunos/{matricula}")
    fun getAlunoDoCurso (@Path("matricula") matricula: String): Call<Registration>

}