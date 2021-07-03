package com.example.pingpong

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.media.MediaPlayer
import android.media.SoundPool
import android.os.Build
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat

var point = 0
var gLoBalQ = 0

@SuppressLint("ResourceAsColor")
class PingPongView(context: Context, attrs: AttributeSet?) : View(context, attrs)
{
    var xChange = 0f

    var x1 = 480f
    var y1 = 200f
    var radius = 76f


    val score = Paint()
    val bg1 = Paint()
    val voila = Paint()
    val oppo = Paint()

    init
    {
        bg1.color = Color.BLACK
        bg1.style = Paint.Style.FILL

        score.color = Color.WHITE
        score.style = Paint.Style.FILL_AND_STROKE
        score.textSize = 120f
        score.typeface = Typeface.create("sans-serif-condensed",Typeface.BOLD)

        voila.color =  ContextCompat.getColor(context, R.color.voila)
        voila.style = Paint.Style.FILL

        oppo.color =  ContextCompat.getColor(context, R.color.voila)
        oppo.style = Paint.Style.FILL

    }

    val barW = 200
    val barH = 75
    var p1 = 0f

    override fun onSizeChanged(width: Int, height: Int, oldwidth: Int, oldheight: Int)
    {
        super.onSizeChanged(width, height, oldwidth, oldheight)
        score.textSize = 80f
        p1 = ((width/2) - (barW/2)).toFloat()

    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onDraw(canvas: Canvas?)
    {
        super.onDraw(canvas)
        //BG
        canvas?.drawRect(0f, 0f, width.toFloat(), height.toFloat(), bg1)

        //Bar Player
        canvas?.drawRoundRect(p1, (height - barH - 40f).toFloat(), p1 + barW, (height - 40f).toFloat(),20f,20f, voila)
                                        //left, top, right, bottom
        //Bar Opponent
        canvas?.drawRoundRect(x1 + 100f, 40f, x1 - 100f , 115f,20f,20f, oppo)

        //score
        canvas?.drawText(point.toString(), width.toFloat() - 200f, 200f, score)

        //Ball
        canvas?.drawCircle(x1, y1, radius, voila)

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
    var runG = false

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

    var dX = 5
    var dY = 5

    private fun resetNow()
    {
        val ran1 = (101..850).random()
        val ran2 = (101..201).random()
        x1 = ran1.toFloat()
        y1 = ran2.toFloat()
        dX = 5
        dY = 5
        point = 0

    }
    val player = MediaPlayer.create(context, R.raw.boi)
    val thud = MediaPlayer.create(context, R.raw.bounce)
    val gg = MediaPlayer.create(context, R.raw.gameover)

    inner class GameThread : Thread()
    {
        override fun run()
        {
            while (runG)
            {
                //changes in center
                x1 += dX
                y1 += dY

                if (y1 >= height - radius - barH -40f)
                {
                    if (x1 in (p1-80)..(p1+80) + barW)
                    {
                        y1 = height - radius - barH -40f
                        dY *= -1
                        point += 1

                        player.start()

                    }
                    else
                    {
                        runG = false
                        gLoBalQ = point
                        gg.start()
                        resetNow()
                    }
                }
                if (x1 > width - radius)
                {
                    x1 = width - radius
                    dX *= -1
                    thud.start()
                }
                else if (y1 < radius + 115f)
                {
                    y1 = radius + 115f
                    dY *= -1
                    player.start()
                }
                else if (x1 < radius)
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