package com.example.triviaapp.network

import com.example.triviaapp.model.TriviaPojo
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface TriviaService {
    //https://opentdb.com/api.php?amount=10&category=&difficulty=&type=multiple
    @GET("/api.php?amount=10")
    fun getQuestions(
        @Query("category")  category:String,
        @Query("difficulty") difficulty:String,
        @Query("type") type:String
    ) : Observable<TriviaPojo>
}