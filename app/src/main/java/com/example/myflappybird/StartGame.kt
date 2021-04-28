package com.example.myflappybird

import android.app.Activity
import android.os.Bundle

class StartGame : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val gameView = GameView(this)
        setContentView(gameView)
    }
}
