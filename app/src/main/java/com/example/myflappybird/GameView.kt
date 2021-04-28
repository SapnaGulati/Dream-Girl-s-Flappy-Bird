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

    init {
        fun run() {
            invalidate()
        }
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
        runnable = Runnable {
            Handler(Looper.getMainLooper()).postDelayed(runnable, updateTime)
        }
    }
}
