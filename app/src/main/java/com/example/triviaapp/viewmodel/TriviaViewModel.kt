package com.example.triviaapp.viewmodel

import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.Observer
import com.example.triviaapp.model.TriviaPojo
import com.example.triviaapp.network.TriviaRetrofit
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class TriviaViewModel(application: Application) : AndroidViewModel(application) {

    val triviaRetrofit = TriviaRetrofit()

    fun getQuestions(category:String, difficulty:String): Observable<TriviaPojo> {
        return triviaRetrofit
            .getTriviaQuestions(category, difficulty)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}