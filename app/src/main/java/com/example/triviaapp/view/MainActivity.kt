package com.example.triviaapp.view

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.example.triviaapp.R
import com.example.triviaapp.viewmodel.TriviaViewModel
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var compositeDisposable = CompositeDisposable()
    lateinit var triviaViewModel: TriviaViewModel
    lateinit var questionFragment:QuestionFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        triviaViewModel = ViewModelProviders.of(this).get(TriviaViewModel::class.java)
        initializeViews()

        start_button.setOnClickListener {
            //Log.d("Tag_X", category_spinner.selectedItem.toString()+" "+difficulty_spinner.selectedItem.toString())
            getQuestions()
        }

    }

    private fun getQuestions(){
        compositeDisposable.add(
            triviaViewModel.getQuestions(getCategory(), getDifficulty()).subscribe({triviaList->

                if(triviaList.results.size == 10) {
                    var triviaBundle = Bundle()
                    triviaBundle.putParcelable("questions", triviaList)
                    triviaBundle.putInt("count", 0)

                    questionFragment = QuestionFragment()
                    questionFragment.arguments = triviaBundle

                    supportFragmentManager
                        .beginTransaction()
                        //.setCustomAnimations(R.anim.card_flip_in_left,R.anim.card_flip_out_left)
                        .add(R.id.frame_layout, questionFragment)
                        .disallowAddToBackStack()
                        .commit()
                }
                else
                    Toast.makeText(this,"This selection is not available", Toast.LENGTH_SHORT).show()
            },{
                Toast.makeText(this,"This selection is not available", Toast.LENGTH_SHORT).show()
            })
        )
    }

    private fun getDifficulty():String{

        when(difficulty_spinner.selectedItem.toString()){
            "Any Difficulty" -> return ""
            "Easy" -> return "easy"
            "Medium" -> return "medium"
            "Hard" -> return "hard"
        }

        return ""
    }

    private fun getCategory():String{
        when(category_spinner.selectedItem.toString()){
            "Any Category" -> return ""
            "General Knowledge" -> return "9"
            "Entertainment: Books" -> return "10"
            "Entertainment: Film" -> return "11"
            "Entertainment: Music" -> return "12"
            "Entertainment: Musicals & Theatres" -> return "13"
            "Entertainment: Television" -> return "14"
            "Entertainment: Video Games" -> return "15"
            "Entertainment: Board Games" -> return "16"
            "Entertainment: Comics" -> return "29"
            "Entertainment: Japanese Anime & Manga" -> return "31"
            "Entertainment: Cartoon & Animations" -> return "32"
            "Science & Nature" -> return "17"
            "Science: Computers" -> return "18"
            "Science: Mathematics" -> return "19"
            "Science: Gadgets" -> return "30"
            "Mythology" -> return "20"
            "Sports" -> return "21"
            "Geography" -> return "22"
            "History" -> return "23"
            "Politics" -> return "24"
            "Ar" -> return "25"
            "Celebrities" -> return "26"
            "Animals" -> return "27"
            "Vehicles" -> return "29"
        }
        return ""
    }

    private fun initializeViews(){
        //INIT CATEGORIES SPINNER
        ArrayAdapter.createFromResource(
            this,
            R.array.categories,
            android.R.layout.simple_spinner_item).also {adapter->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
            category_spinner.adapter = adapter
        }

        //INIT DIFFICULTY SPINNER
        ArrayAdapter.createFromResource(
            this,
            R.array.difficulties,
            android.R.layout.simple_spinner_item).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
            difficulty_spinner.adapter = adapter
        }
    }
}
