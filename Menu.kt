package com.example.badgames

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*


class Menu : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }


    fun showInfo(v: View){
        if (v == getHigherNumberInfoBtn){
            if (getHigherNumberInfoText.visibility == View.VISIBLE)
                getHigherNumberInfoText.visibility = View.INVISIBLE
            else
                getHigherNumberInfoText.visibility = View.VISIBLE
        }
    }

    fun playGame(v: View){
        if(v == getHigherNumberBtn){
            //start getHigherNumber game activity
            val myIntent = Intent(this, HigherNumberGame::class.java)
            startActivity(myIntent)
        }
    }
}
