package com.example.triviaapp.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.triviaapp.R
import com.example.triviaapp.model.TriviaPojo
import kotlinx.android.synthetic.main.questions_fragment_layout.*


class QuestionFragment() : Fragment() {
    lateinit var chosenAnswer: String
    lateinit var questionFragment: QuestionFragment
    lateinit var endFragment: EndFragment
    var answers = ArrayList<String>()
    private lateinit var timer: CountDownTimer

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.questions_fragment_layout, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        chosenAnswer = ""

        val bundle = arguments
        val triviaPojo = bundle?.getParcelable<TriviaPojo>("questions")
        var count = bundle?.getInt("count")


        if (count!! > 0) {
            answers = bundle?.getStringArrayList("answers") as ArrayList<String>
        }

        //Log.d("TAG_X", count?.let { it.toString() })

        initalizeViews(count, triviaPojo)

        setOnClicks()


        timer = object : CountDownTimer(30000, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                var timeLeft = millisUntilFinished / 1000

                if (timeLeft >= 10)
                    questions_timer_tv.text = ("00:" + timeLeft)
                else if (timeLeft > 0)
                    questions_timer_tv.text = ("00:0" + timeLeft)
                else
                    questions_timer_tv.text = ("00:00")
            }

            override fun onFinish() {
                if (chosenAnswer == "") {
                    chosenAnswer = "NO ANSWER"
                    questions_next_button.performClick()
                } else {
                    questions_next_button.performClick()
                }
            }


        }.start()

        questions_next_button.setOnClickListener {
            if (chosenAnswer != "") {
                count += 1
                answers.add(chosenAnswer)

                //Log.d("TAG_X", chosenAnswer)
                val triviaBundle = Bundle()

                triviaBundle.putParcelable("questions", triviaPojo)
                //Log.d("TAG_X",count?.let { it.toString() })
                count.let { it1 -> triviaBundle.putInt("count", it1) }
                triviaBundle.putStringArrayList("answers", answers)

                getNewFragment(count, triviaBundle)

                timer.cancel()
            } else
                Toast.makeText(this.context, "You must select an answer", Toast.LENGTH_SHORT).show()

        }

    }

    override fun onPause() {
        super.onPause()
        timer.cancel()
    }

    override fun onDestroy() {
        super.onDestroy()
        timer.cancel()
    }

    private fun getNewFragment(count: Int, triviaBundle: Bundle) {
        if (count < 10) {
            questionFragment = QuestionFragment()
            questionFragment.arguments = triviaBundle

            val fragmentTransaction =
                getActivity()?.getSupportFragmentManager()?.beginTransaction()?.setCustomAnimations(
                    R.anim.slide_right_to_left, R.anim.slide_left_to_right, 0, 0
                )
            fragmentTransaction?.replace(R.id.frame_layout, questionFragment)
            fragmentTransaction?.disallowAddToBackStack()
            fragmentTransaction?.commit()
        } else {
            endFragment = EndFragment()
            endFragment.arguments = triviaBundle

            val fragmentTransaction =
                getActivity()?.getSupportFragmentManager()?.beginTransaction()
                    ?.setCustomAnimations(R.anim.slide_right_to_left, R.anim.slide_left_to_right)
            fragmentTransaction?.replace(R.id.frame_layout, endFragment)
            fragmentTransaction?.disallowAddToBackStack()
            fragmentTransaction?.commit()
        }
    }


    private fun setOnClicks() {
        question_button_1.setOnClickListener {
            chosenAnswer = question_button_1.text.toString()
            question_button_1.setBackgroundResource(R.drawable.chosen_button_bg)

            question_button_2.setBackgroundResource(R.drawable.button_bg)
            question_button_3.setBackgroundResource(R.drawable.button_bg)
            question_button_4.setBackgroundResource(R.drawable.button_bg)
        }

        question_button_2.setOnClickListener {
            chosenAnswer = question_button_2.text.toString()
            question_button_2.setBackgroundResource(R.drawable.chosen_button_bg)

            question_button_1.setBackgroundResource(R.drawable.button_bg)
            question_button_3.setBackgroundResource(R.drawable.button_bg)
            question_button_4.setBackgroundResource(R.drawable.button_bg)
        }

        question_button_3.setOnClickListener {
            chosenAnswer = question_button_3.text.toString()
            question_button_3.setBackgroundResource(R.drawable.chosen_button_bg)

            question_button_1.setBackgroundResource(R.drawable.button_bg)
            question_button_2.setBackgroundResource(R.drawable.button_bg)
            question_button_4.setBackgroundResource(R.drawable.button_bg)
        }

        question_button_4.setOnClickListener {
            chosenAnswer = question_button_4.text.toString()
            question_button_4.setBackgroundResource(R.drawable.chosen_button_bg)

            question_button_1.setBackgroundResource(R.drawable.button_bg)
            question_button_2.setBackgroundResource(R.drawable.button_bg)
            question_button_3.setBackgroundResource(R.drawable.button_bg)
        }

    }

    @SuppressLint("NewApi")
    private fun initalizeViews(
        count: Int?,
        triviaPojo: TriviaPojo?
    ) {
        questions_number_tv.text = (count?.plus(1)).toString()

        var question = (count?.let { triviaPojo?.results?.get(it)?.question })
        var answer1 = (count?.let { triviaPojo?.results?.get(it)?.incorrectAnswers?.get(0) })
        var answer2 = (count?.let { triviaPojo?.results?.get(it)?.incorrectAnswers?.get(1) })
        var answer3 = (count?.let { triviaPojo?.results?.get(it)?.incorrectAnswers?.get(2) })
        var answer4 = (count?.let { triviaPojo?.results?.get(it)?.correctAnswer })

        if ((Html.fromHtml(question, Html.FROM_HTML_MODE_LEGACY).toString()).length > 100)
            questions_question_tv.setTextSize(24f)

        questions_question_tv.text = Html.fromHtml(question, Html.FROM_HTML_MODE_LEGACY).toString()

        question_button_1.text = Html.fromHtml(answer1, Html.FROM_HTML_MODE_LEGACY).toString()
        question_button_2.text = Html.fromHtml(answer2, Html.FROM_HTML_MODE_LEGACY).toString()
        question_button_3.text = Html.fromHtml(answer3, Html.FROM_HTML_MODE_LEGACY).toString()
        var correctAnswer = Html.fromHtml(answer4, Html.FROM_HTML_MODE_LEGACY).toString()


        val rnds = (0..3).random()

        when (rnds) {
            0 -> {
                var temp = question_button_1.text
                question_button_4.text = temp
                question_button_1.text = correctAnswer
            }

            1 -> {
                var temp = question_button_2.text
                question_button_4.text = temp
                question_button_2.text = correctAnswer
            }

            2 -> {
                var temp = question_button_3.text
                question_button_4.text = temp
                question_button_3.text = correctAnswer
            }

            3 -> {
                question_button_4.text = correctAnswer
            }
        }
    }
}