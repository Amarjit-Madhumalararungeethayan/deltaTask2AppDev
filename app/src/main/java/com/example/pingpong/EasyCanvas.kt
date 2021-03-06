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

var point = 0
var gLoBalQ = 0
var runGe = false

@SuppressLint("ResourceAsColor")
class PingPongView(context: Context, attrs: AttributeSet?) : View(context, attrs)
{
    //private lateinit var viewModel : ViewEasy

    var xChange = 0f
    var x1 = 480f
    var y1 = 200f
    var radius = 76f


    val score = Paint()
    val bg1 = Paint()
    val voila = Paint()
    val oppo = Paint()
    val ls = Paint()

    init
    {
        bg1.color = Color.BLACK
        bg1.style = Paint.Style.FILL

        score.color = Color.WHITE
        score.style = Paint.Style.FILL_AND_STROKE
        score.textSize = 120f
        score.typeface = Typeface.create("sans-serif-condensed",Typeface.BOLD)
        score.textAlign = Paint.Align.CENTER

        ls.color = Color.YELLOW
        ls.style = Paint.Style.FILL_AND_STROKE
        ls.textSize = 75f
        ls.typeface = Typeface.create("sans-serif-condensed",Typeface.NORMAL)
        ls.textAlign = Paint.Align.CENTER

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
        score.textSize = 120f
        ls.textSize = 75f
        p1 = ((width/2) - (barW/2)).toFloat()

    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onDraw(canvas: Canvas?)
    {
        super.onDraw(canvas)
        //BG
        canvas?.drawRect(0f, 0f, width.toFloat(), height.toFloat(), bg1)

        //Bar Player
        canvas?.drawRoundRect(p1, (height - barH -60f).toFloat(), p1 + barW, (height - 60f).toFloat(),20f,20f, voila)
                                        //left, top, right, bottom
        //Bar Opponent
        canvas?.drawRoundRect(x1 + 100f, 40f, x1 - 100f , 115f,20f,20f, oppo)

        //score
        canvas?.drawText(point.toString(), width.toFloat()/2, height.toFloat()/2, score)

        //hs
        canvas?.drawText("", width.toFloat()/2 , height.toFloat() - 10f, ls)

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
                moveNow(event)
            }
        }

        return true
    }

    private fun moveNow(event: MotionEvent)
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
        runGe = true
        GameT().start()
    }
    fun letsStop()
    {
        runGe = false
        resetNow()
        postInvalidate()
    }

    var dX = 5
    var dY = 5

    private fun resetNow()
    {
        val ran1 = (101..850).random()
        x1 = ran1.toFloat()
        y1 = 118f
        dX = 5
        dY = 5
        point = 0

    }

    val thud1 = MediaPlayer.create(context, R.raw.bally)
    val thud2 = MediaPlayer.create(context, R.raw.bally)
    val thud3 = MediaPlayer.create(context, R.raw.bally)
    val thud4 = MediaPlayer.create(context, R.raw.bally)

    val gg = MediaPlayer.create(context, R.raw.gameover)


    inner class GameT : Thread()
    {

        override fun run()
        {
            while (runGe)
            {
                //changes in center
                x1 += dX
                y1 += dY

                if (y1 >= height - radius - barH - 60f)
                {
                    if (x1 in (p1-80)..(p1+80) + barW)
                    {
                        y1 = height - radius - barH - 60f
                        dY *= -1
                        point += 1

                        thud1.start()

                    }
                    else
                    {
                        runGe = false
                        gLoBalQ = point
                        gg.start()
                        resetNow()
                    }
                }
                if (x1 > width - radius)
                {
                    x1 = width - radius
                    dX *= -1
                    thud2.start()
                }
                else if (y1 < radius + 115f)
                {
                    y1 = radius + 115f
                    dY *= -1
                    thud3.start()
                }
                else if (x1 < radius)
                {
                    x1 = radius
                    dX *= -1
                    thud4.start()
                }


                postInvalidate()
                sleep(7)
            }
        }
    }
}

