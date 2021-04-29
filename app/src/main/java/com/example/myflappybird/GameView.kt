package com.example.myflappybird

import android.content.Context
import android.graphics.*
import android.media.MediaPlayer
import android.os.Handler
import android.os.Looper
import android.util.DisplayMetrics
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import java.util.concurrent.Executors
import kotlin.properties.Delegates
import kotlin.random.Random

class GameView(context: Context?) : View(context) {
    private var runnable: Runnable = Runnable {
        invalidate()
    }
    private var mediaPlayerHit: MediaPlayer? = null
    private var mediaPlayerPt: MediaPlayer? = null
    private var mediaPlayerSwoosh: MediaPlayer? = null
    private var mediaPlayerWing: MediaPlayer? = null
    private val updateTime: Long = 50
    private var background: Bitmap
    private var topTube: Bitmap
    private var bottomTube: Bitmap
    private var windowManager: WindowManager
    private var displayMetrics: DisplayMetrics
    private var height: Int? = null
    private var width: Int? = null
    private var birdWidth by Delegates.notNull<Int>()
    private var birdHeight by Delegates.notNull<Int>()
    private var rect: Rect
    private var birds: ArrayList<Bitmap> = arrayListOf()
    private var birdFrame = 0
    private var velocity = 0
    private var gravity = 3
    private var gameState = false
    private var gap = 400
    private var tubeVelocity = 8
    private var minTubeOffset by Delegates.notNull<Int>()
    private var maxTubeOffset by Delegates.notNull<Int>()
    private var numberOfTubes = 4
    private var distanceBetweenTubes by Delegates.notNull<Int>()
    private var random: Random
    private val tubeX = (0 until numberOfTubes).toList().toTypedArray()
    private val topTubeY = (0 until numberOfTubes).toList().toTypedArray()

    init {
        birds.add(BitmapFactory.decodeResource(resources, R.drawable.frame_0))
        birds.add(BitmapFactory.decodeResource(resources, R.drawable.frame_1))
        birds.add(BitmapFactory.decodeResource(resources, R.drawable.frame_2))
        birds.add(BitmapFactory.decodeResource(resources, R.drawable.frame_3))
        birds.add(BitmapFactory.decodeResource(resources, R.drawable.frame_4))
        birds.add(BitmapFactory.decodeResource(resources, R.drawable.frame_5))
        birds.add(BitmapFactory.decodeResource(resources, R.drawable.frame_6))
        birds.add(BitmapFactory.decodeResource(resources, R.drawable.frame_7))

        background = BitmapFactory.decodeResource(resources, R.drawable.background)
        topTube = BitmapFactory.decodeResource(resources, R.drawable.top_pipe)
        bottomTube = BitmapFactory.decodeResource(resources, R.drawable.pipe)

        mediaPlayerHit = MediaPlayer.create(context, R.raw.gallery_audio_hit)
        mediaPlayerPt = MediaPlayer.create(context, R.raw.gallery_audio_point)
        mediaPlayerSwoosh = MediaPlayer.create(context, R.raw.gallery_audio_swoosh)
        mediaPlayerWing = MediaPlayer.create(context, R.raw.gallery_audio_wing)

        windowManager = context?.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = windowManager.defaultDisplay
        displayMetrics = DisplayMetrics()
        display.getMetrics(displayMetrics)
        width = displayMetrics.widthPixels
        height = displayMetrics.heightPixels
        birdWidth = (width!! /2) - (birds[0].width/2)
        birdHeight = (height!! /2) - (birds[0].height/2)

        topTube = Bitmap.createScaledBitmap(topTube, topTube.width * 6/5, height!! , true)
        bottomTube = Bitmap.createScaledBitmap(bottomTube, bottomTube.width * 6/5, height!!, true)

        rect = Rect(0, 0, width!!, height!! + 102)
        distanceBetweenTubes = width!! * 3/4
        minTubeOffset = gap/2
        maxTubeOffset = height!! - minTubeOffset - gap
        random = Random
        for (i in 0 until numberOfTubes) {
            tubeX[i] = width!! + i*distanceBetweenTubes
            topTubeY[i] = minTubeOffset + random.nextInt(maxTubeOffset - minTubeOffset + 1)
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawBitmap(background, null, rect, null)
        birdFrame = when (birdFrame) {
            0 -> 1
            1 -> 2
            2 -> 3
            3 -> 4
            4 -> 5
            5 -> 6
            6 -> 7
            else -> 0
        }

        if (gameOver()) {
            val executorService = Executors.newSingleThreadExecutor()
            val task = executorService.submit(runnable)
            task.cancel(true)
        }

        if (gameState) {
            if (birdHeight < height!! - birds[0].height + 89|| velocity < 0 ) {
                velocity += gravity
                birdHeight += velocity
            }
            for (i in 0 until numberOfTubes) {
                tubeX[i] -= tubeVelocity
                if (tubeX[i] < -topTube.width) {
                    tubeX[i] += numberOfTubes * distanceBetweenTubes
                    topTubeY[i] = minTubeOffset + random.nextInt(maxTubeOffset - minTubeOffset + 1)
                }
                canvas?.drawBitmap(topTube, tubeX[i].toFloat(), (topTubeY[i] - topTube.height).toFloat(), null)
                canvas?.drawBitmap(bottomTube, tubeX[i].toFloat(), (topTubeY[i] + gap).toFloat(), null)
            }
        }

        canvas?.drawBitmap(birds[birdFrame], birdWidth.toFloat(), birdHeight.toFloat(), null)
            Handler(Looper.getMainLooper()).postDelayed(runnable, updateTime)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val action = event!!.action
        mediaPlayerWing?.start()
        if(action == MotionEvent.ACTION_DOWN) {
            velocity = -30
            gameState = true
        }
        return true
    }

    private fun gameOver(): Boolean {
        if (birdHeight < 0) {
            mediaPlayerHit?.start()
//            val gameOver = GameOver(context)
        }

//        if playery > GROUNDY - 25 or playery < 0:
//        GAME_SOUNDS['hit'].play()
//        return True
//
//        for pipe in upperPipes:
//        pipeHeight = GAME_SPRITES['pipe'][0].get_height()
//        if (playery < pipeHeight + pipe['y'] and abs(playerx - pipe['x']) < GAME_SPRITES['pipe'][0].get_width()):
//        GAME_SOUNDS['hit'].play()
//        return True
//
//        for pipe in lowerPipes:
//        if (playery + GAME_SPRITES['player'].get_height() > pipe['y']) and abs(playerx - pipe['x']) < GAME_SPRITES['pipe'][0].get_width():
//        GAME_SOUNDS['hit'].play()
//        return True
        return false
    }
}