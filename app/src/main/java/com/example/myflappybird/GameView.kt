package com.example.myflappybird

import android.content.Context
import android.graphics.*
import android.os.Handler
import android.os.Looper
import android.util.DisplayMetrics
import android.view.View
import android.view.WindowManager

class GameView(context: Context?) : View(context) {
    private lateinit var runnable: Runnable
    private val updateTime: Long = 30
    private var background: Bitmap
    private var point: Point
    private var windowManager: WindowManager
    private var displayMetrics: DisplayMetrics
    private var height: Int? = null
    private var width: Int? = null
    private var rect: Rect
    private var birds: ArrayList<Bitmap> = arrayListOf()
    private var birdFrame = 0

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
        if(birdFrame == 0)
            birdFrame = 1
        if(birdFrame == 1)
            birdFrame = 2
        if(birdFrame == 2)
            birdFrame = 3
        if(birdFrame == 3)
            birdFrame = 4
        if(birdFrame == 4)
            birdFrame = 5
        if(birdFrame == 5)
            birdFrame = 6
        if(birdFrame == 6)
            birdFrame = 7
        if(birdFrame == 7)
            birdFrame = 0

        val birdWidth = (width!! /2) - (birds[0].width/2)
        val birdHeight = (height!! /2) - (birds[0].height/2)

        canvas?.drawBitmap(birds[birdFrame], birdWidth.toFloat(), birdHeight.toFloat(), null)
        runnable = Runnable {
            Handler(Looper.getMainLooper()).postDelayed(runnable, updateTime)
        }
    }
}
