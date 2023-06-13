package br.senai.sp.jandira.lionschool.model

data class Registration(
    val nome: String,
    val foto: String,
    val matricula: String,
    val sexo: String,
    val status: String,
    val nomeCurso: String,
    val sigla: String,
    val icone: String,
    val disciplinas: List<SubjectsList>,

)