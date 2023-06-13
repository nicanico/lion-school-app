package br.senai.sp.jandira.lionschool.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitFactoryCourses {

    private val BASE_URL = "https://api-lion-school.cyclic.app/v1/lion-school/"
    // em caso de haver mais de uma url base, devemos ter duas bases e dois retrofitFactory

    // converte o objeto em json e converte o json em objeto - responsavel pela fabrica
    private val retrofitFactory = Retrofit
        .Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val BASE_URL2 = "https://api-lion-school-2023.cyclic.app/v1/lion-school/"
    // em caso de haver mais de uma url base, devemos ter duas bases e dois retrofitFactory

    // converte o objeto em json e converte o json em objeto - responsavel pela fabrica
    private val retrofitFactory2 = Retrofit
        .Builder()
        .baseUrl(BASE_URL2)
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    fun getLionSchoolService(): LionSchoolService {
        return retrofitFactory.create(LionSchoolService::class.java)
    }
    fun getLionSchoolServiceStudent(): LionSchoolService {
        return retrofitFactory2.create(LionSchoolService::class.java)
    }
}