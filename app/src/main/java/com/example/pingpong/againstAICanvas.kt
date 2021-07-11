package com.example.pingpong

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.media.MediaPlayer
import android.os.Build
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat

var playerPoint = 0
var aiPoint = 0
var incP = 0
var incA = 0

class againstAICanvas(context: Context, attrs: AttributeSet?) : View(context, attrs) {
    var xChange = 0f
    var x1 = 480f
    var y1 = 220f
    var radius = 60f

    var xp = 100f
    var yp = 200f
    var rp = 25f


    val score = Paint()
    val bg1 = Paint()
    val voila = Paint()
    val oppo = Paint()
    val hs = Paint()
    var pointCircle = Paint()

    init
    {

        score.color = Color.WHITE
        score.style = Paint.Style.FILL_AND_STROKE
        score.textSize = 120f
        score.typeface = Typeface.create("sans-serif-condensed", Typeface.BOLD)

        hs.color = Color.YELLOW
        hs.style = Paint.Style.FILL_AND_STROKE
        hs.textSize = 175f
        hs.typeface = Typeface.create("sans-serif-condensed", Typeface.BOLD)
        hs.textAlign = Paint.Align.CENTER

        voila.color =  ContextCompat.getColor(context, R.color.voila)
        voila.style = Paint.Style.FILL

        oppo.color =  ContextCompat.getColor(context, R.color.voila)
        oppo.style = Paint.Style.FILL

        pointCircle.color = Color.GREEN
        pointCircle.style = Paint.Style.FILL_AND_STROKE
    }

    var barW = 200
    val barH = 75
    var p1 = 0f
    var lvl = 0

    override fun onSizeChanged(width: Int, height: Int, oldwidth: Int, oldheight: Int)
    {
        super.onSizeChanged(width, height, oldwidth, oldheight)
        score.textSize = 120f
        hs.textSize = 100f
        p1 = ((width/2) - (barW/2)).toFloat()

    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onDraw(canvas: Canvas?)
    {
        super.onDraw(canvas)
        //BG
        canvas?.drawRect(0f, 0f, width.toFloat(), height.toFloat(), bg1)

        //Bar Player
        canvas?.drawRoundRect(p1, (height - barH - 60f).toFloat(), p1 + barW, (height - 60f).toFloat(),20f,20f, voila)

        //Bar Opponent
        canvas?.drawRoundRect(x1 + 100f, 80f, x1 - 100f , 155f,20f,20f, oppo)

        //Ball
        canvas?.drawCircle(x1, y1, radius, voila)

        //players points
        if(playerPoint != 0) {
            for (i in 1..playerPoint){
                canvas?.drawCircle(xp, height.toFloat() - yp - i*(70), rp, pointCircle)
            }
        }
        //AIs points
        if(aiPoint != 0) {
            for (i in 1..aiPoint){
                canvas?.drawCircle(width.toFloat() - xp, yp + i*(70), rp, pointCircle)
            }
        }


    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean
    {
        when (event?.action)
        {
            MotionEvent.ACTION_DOWN -> xChange = event.x
            MotionEvent.ACTION_MOVE ->
            {
                handleMove(event)
            }
        }

        return true
    }

    private fun handleMove(event: MotionEvent)
    {

        p1 = p1 - (xChange - event.x)
        xChange = event.x
        p1 = if (p1 < 0) 0f
        else if (p1 > width - barW)
        {
            (width - barW).toFloat()
        } else {
            p1
        }
        invalidate() //kinda loops the onDraw method
    }


    fun letsGo()
    {
        runG = true
        GameThread().start()
    }
    fun letsStop()
    {
        runG = false
        resetNow()
        postInvalidate()
    }

    var dX = 5.0f
    var dY = 5.0f

    private fun resetNow()
    {
        val ran1 = (101..850).random()
        val ran2 = (101..201).random()
        x1 = ran1.toFloat()
        y1 = ran2.toFloat()
        dX = 5.0f
        dY = 5.0f
        point2 = 0
        lvl = 1
        end = true

    }

    val player = MediaPlayer.create(context, R.raw.boi)
    val thud = MediaPlayer.create(context, R.raw.bounce)
    val gg = MediaPlayer.create(context, R.raw.gameover)

    inner class GameThread : Thread() {
        override fun run() {
            while (runG) {
                //changes in center
                x1 += dX
                y1 += dY


                if (y1 >= height - radius - barH - 60f) {
                    if (x1 in (p1 - 75)..(p1 + 75) + barW) {
                        y1 = height - radius - barH - 60f
                        dY *= -1
                        playerPoint += 1
                        player.start()


                    } else {
                        runG = false
                        gLoBal = point2
                        gg.start()
                        aiPoint += 1
                        resetNow()
                    }
                }
                if (x1 > width - radius)
                {
                    x1 = width - radius
                    dX *= -1
                    thud.start()

                }
                else if (y1 < radius + 155f)
                {
                    y1 = radius + 155f
                    dY *= -1
                    player.start()


                } else if (x1 < radius)
                {
                    x1 = radius
                    dX *= -1
                    thud.start()
                }
                postInvalidate()
                sleep(7)
            }
        }
    }
}