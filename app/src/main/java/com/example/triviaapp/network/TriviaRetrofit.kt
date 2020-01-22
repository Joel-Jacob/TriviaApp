package com.example.triviaapp.network

import com.example.triviaapp.model.TriviaPojo
import com.example.triviaapp.util.Constants
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class TriviaRetrofit{
    private lateinit var triviaService: TriviaService

    init {
        triviaService = createService(retrofitInstance())
    }

    private fun retrofitInstance():Retrofit{
        return Retrofit
            .Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    private fun createService(retrofit: Retrofit):TriviaService{
        return retrofit.create(TriviaService::class.java)
    }

    fun getTriviaQuestions(category:String, difficulty:String):Observable<TriviaPojo>{
        return triviaService.getQuestions(category, difficulty, Constants.TYPE)
    }
}