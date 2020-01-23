package com.example.triviaapp.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.example.triviaapp.R
import com.example.triviaapp.model.Result
import kotlinx.android.synthetic.main.answers_item_layout.view.*

class TriviaAdapter(val list:List<Result>) : RecyclerView.Adapter<TriviaAdapter.TriviaHolder>() {

    private lateinit var context:Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TriviaHolder {
        context = parent.context.applicationContext

        val view = LayoutInflater.from(parent.context).inflate(R.layout.answers_item_layout, parent, false)
        return TriviaHolder(view)
    }

    override fun getItemCount(): Int =list.size

    @SuppressLint("NewApi")
    override fun onBindViewHolder(holder: TriviaHolder, position: Int) {
        var question = list.get(position).question
        holder.question.text = Html.fromHtml(question, Html.FROM_HTML_MODE_LEGACY).toString()

        var pickedAnswer = list.get(position).pickedAnswer
        if(pickedAnswer!=null)
            holder.pickedAnswer.text = "Your Answer: "+Html.fromHtml(pickedAnswer, Html.FROM_HTML_MODE_LEGACY).toString()


        var correctAnswer = list.get(position).correctAnswer
        if(correctAnswer!=null)
            holder.correctAnswer.text = "Correct Answer: "+Html.fromHtml(correctAnswer, Html.FROM_HTML_MODE_LEGACY).toString()

        if(Html.fromHtml(correctAnswer, Html.FROM_HTML_MODE_LEGACY).toString() == Html.fromHtml(pickedAnswer, Html.FROM_HTML_MODE_LEGACY).toString())
            holder.constraintLayout.setBackgroundResource(R.drawable.green_item_bg)
        else
            holder.constraintLayout.setBackgroundResource(R.drawable.red_item_bg)

        val transition =
            AnimationUtils.loadAnimation(context, R.anim.transition_animation)
        holder.itemView.startAnimation(transition)

    }

    class TriviaHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var question = itemView.item_question_tv
        var pickedAnswer = itemView.item_picked_answer
        var correctAnswer = itemView.item_correct_answer
        var constraintLayout = itemView.item_constraint_layout
    }

}