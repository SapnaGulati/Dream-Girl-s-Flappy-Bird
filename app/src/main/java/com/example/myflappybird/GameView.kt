package com.example.myflappybird

import android.content.Context
import android.graphics.*
import android.os.Handler
import android.os.Looper
import android.util.DisplayMetrics
import android.view.View
import android.view.WindowManager

class GameView(context: Context?) : View(context) {
    private var runnable: Runnable = Runnable {
        invalidate()
    }
    private val updateTime: Long = 50
    private var background: Bitmap
    private var point: Point
    private var windowManager: WindowManager
    private var displayMetrics: DisplayMetrics
    private var height: Int? = null
    private var width: Int? = null
    private var birdWidth: Int? = null
    private var birdHeight: Int? = null
    private var rect: Rect
    private var birds: ArrayList<Bitmap> = arrayListOf()
    private var birdFrame = 0
    private var velocity = 0
    private var gravity = 3

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
        windowManager = context?.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = windowManager.defaultDisplay
        displayMetrics = DisplayMetrics()
        display.getMetrics(displayMetrics)
        width = displayMetrics.widthPixels
        height = displayMetrics.heightPixels
        point = Point()
        rect = Rect(0, 0, width!!, height!!)
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

        birdWidth = (width!! /2) - (birds[0].width/2)
        birdHeight = (height!! /2) - (birds[0].height/2)

        canvas?.drawBitmap(birds[birdFrame], birdWidth!!.toFloat(), birdHeight!!.toFloat(), null)
            Handler(Looper.getMainLooper()).postDelayed(runnable, updateTime)
    }
}