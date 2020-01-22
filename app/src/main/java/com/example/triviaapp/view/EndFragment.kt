package com.example.triviaapp.view

import android.annotation.SuppressLint
import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.triviaapp.R
import com.example.triviaapp.adapter.TriviaAdapter
import com.example.triviaapp.model.TriviaPojo
import kotlinx.android.synthetic.main.end_screen_fragment_layout.*
import java.util.ArrayList

class EndFragment : Fragment() {
    var numCorrect = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.end_screen_fragment_layout, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var bundle = arguments
        var triviaPojo = bundle?.getParcelable<TriviaPojo>("questions")
        var answers = bundle?.getStringArrayList("answers")

        getCorrectNum(triviaPojo, answers)
        end_correct_tv.text = numCorrect.toString()+"/10"

        recycler_view.layoutManager = LinearLayoutManager(this.context, RecyclerView.VERTICAL, false)
        recycler_view.addItemDecoration(DividerItemDecoration(this.context, LinearLayoutManager.VERTICAL))


        if (triviaPojo != null) {
            recycler_view.adapter = TriviaAdapter(triviaPojo.results)
        }

        end_return_button.setOnClickListener {
            Log.d("TAG_X", "popping")

            val intent = Intent(this.context, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        }

    }

    @SuppressLint("NewApi")
    private fun getCorrectNum(triviaPojo: TriviaPojo?, answers: ArrayList<String>?) {
        if (triviaPojo != null) {
            lateinit var tempAnswer:String
            for (x in 0 until 10) {
                tempAnswer = triviaPojo.results.get(x).correctAnswer
                triviaPojo.results.get(x).pickedAnswer = answers?.get(x)
                //Log.d("TAG_X", "my: "+answers?.get(x)+" right: "+Html.fromHtml(tempAnswer, Html.FROM_HTML_MODE_LEGACY).toString())
                if (answers?.get(x) == Html.fromHtml(tempAnswer, Html.FROM_HTML_MODE_LEGACY).toString())
                    numCorrect++
            }

        }
    }
}