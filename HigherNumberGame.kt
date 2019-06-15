package com.example.badgames

import android.content.Context
import android.content.SharedPreferences
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.TextView
import android.widget.Toast
import java.util.*
import kotlinx.android.synthetic.main.activity_higher_number_game.*

class HigherNumberGame : AppCompatActivity() {
    var SCORE = 0
    var BEST_SCORE = SCORE

    val PREFERENCE_NAME = "AppPrefs"
    val PREFERENCE_BESTSCORE = "BestScore"
    lateinit var preference: SharedPreferences
    lateinit var mp3_beep: MediaPlayer
    lateinit var mp3_pointScored: MediaPlayer
    lateinit var mp3_winnerSound: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_higher_number_game)
        leftButton.visibility = View.INVISIBLE
        rightButton.visibility = View.INVISIBLE

        preference = this.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
        bestScoreText.text = "Best Score: ${getBestScorePref()}"
        BEST_SCORE = getBestScorePref()

        //sounds
        mp3_beep = MediaPlayer.create(this, R.raw.beep)
        mp3_pointScored = MediaPlayer.create(this, R.raw.point_scored)
        mp3_winnerSound = MediaPlayer.create(this, R.raw.winner_sound_effect)

    }
    fun startGame(v: View){
        timerView.visibility = View.VISIBLE
        timer(timerView)
        leftButton.visibility = View.VISIBLE
        rightButton.visibility = View.VISIBLE
        startGameButton.visibility = View.INVISIBLE
        changeNumbers()
    }


    fun leftButton(v: View){
        checkIfCorrectAnswer(true)
    }

    fun rightButton(v: View){
        checkIfCorrectAnswer(false)
    }

    private fun checkIfCorrectAnswer(isLeft: Boolean){
        if (isLeft && leftButton.text.toString().toInt() > rightButton.text.toString().toInt() || !isLeft && leftButton.text.toString().toInt() < rightButton.text.toString().toInt()) {
            SCORE++
            mp3_pointScored.start()
        }
        else
            SCORE--

        updateScoreText()
        changeNumbers()
    }

    private fun getBestScorePref() : Int{
        return preference.getInt(PREFERENCE_BESTSCORE, 0)
    }
    private fun setBestScorePref(score:Int){
        val editor = preference.edit()
        editor.putInt(PREFERENCE_BESTSCORE, score)
        editor.apply()
    }

    fun Context.longToast(msg: CharSequence) = Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    fun Context.shortToast(msg: CharSequence) = Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()

    private fun timer(timerTextView: TextView){


        object : CountDownTimer(10000, 500) {

            var oldText = timerTextView.text
            override fun onTick(millisUntilFinished: Long) {
                timerTextView.text = "${millisUntilFinished / 1000}"
                if (oldText != timerTextView.text)
                    mp3_beep.start()

                oldText = timerTextView.text
            }

            override fun onFinish() {
                leftButton.visibility = View.INVISIBLE
                rightButton.visibility = View.INVISIBLE
                // checking if new record
                if(SCORE > BEST_SCORE){
                    BEST_SCORE = SCORE
                    mp3_winnerSound.start()
                    longToast("NEW BEST SCORE!!!")
                }
                SCORE = 0
                updateBestScoreText()
                updateScoreText()
                startGameButton.visibility = View.VISIBLE
            }
        }.start()

    }

    fun updateBestScoreText(){
        bestScoreText.text = "Best Score: $BEST_SCORE"
        setBestScorePref(BEST_SCORE)
    }
    fun updateScoreText(){
        scoreText.text = "Score: $SCORE"
    }

    private fun changeNumbers(){
        val random = Random()
        var randomInt = random.nextInt(100)
        leftButton.text = randomInt.toString()
        randomInt = random.nextInt(100)
        rightButton.text = randomInt.toString()

        while(rightButton.text == leftButton.text) {
            randomInt = random.nextInt(100)
            rightButton.text = randomInt.toString()
        }
    }
}
