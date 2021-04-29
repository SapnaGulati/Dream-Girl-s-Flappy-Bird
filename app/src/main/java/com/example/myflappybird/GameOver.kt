package com.example.myflappybird

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.media.MediaPlayer
import android.view.View

class GameOver(context: Context?) : View(context){
    private var runnable: Runnable = Runnable {
        invalidate()
    }
    private var gOver: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.gameover)
    private var mediaPlayerDie: MediaPlayer? = null
    private var height: Int? = null
    private var width: Int? = null

    init {
        mediaPlayerDie = MediaPlayer.create(context, R.raw.gallery_audio_die)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
//        canvas?.drawBitmap(gOver, tubeX[i].toFloat(), (topTubeY[i] - topTube.height).toFloat(), null)
    }
}
