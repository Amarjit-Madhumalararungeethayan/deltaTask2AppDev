package com.example.pingpong

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.media.MediaPlayer
import android.os.Build
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewModelProvider

var point2 = 0
var runG = false

@SuppressLint("ResourceAsColor")
class gameHard(context: Context, attrs: AttributeSet?) : View(context, attrs)
{
    //lateinit var viewModel: viewHard

    var xChange = 0f
    var x1 = 480f
    var y1 = 200f
    var radius = 70f


    val score = Paint()
    val bg1 = Paint()
    val voila = Paint()
    val levelD = Paint()
    val oppo = Paint()
    val hs = Paint()

    init
    {

        score.color = Color.WHITE
        score.style = Paint.Style.FILL_AND_STROKE
        score.textSize = 120f
        score.typeface = Typeface.create("sans-serif-condensed",Typeface.BOLD)

        hs.color = Color.YELLOW
        hs.style = Paint.Style.FILL_AND_STROKE
        hs.textSize = 175f
        hs.typeface = Typeface.create("sans-serif-condensed",Typeface.BOLD)
        hs.textAlign = Paint.Align.CENTER

        levelD.color = Color.WHITE
        levelD.style = Paint.Style.FILL_AND_STROKE
        levelD.textSize = 75f
        levelD.typeface = Typeface.create("sans-serif-condensed",Typeface.NORMAL)
        levelD.textAlign = Paint.Align.CENTER

        voila.color =  ContextCompat.getColor(context, R.color.voila)
        voila.style = Paint.Style.FILL

        oppo.color =  ContextCompat.getColor(context, R.color.voila)
        oppo.style = Paint.Style.FILL
    }

    var barW = 200
    val barH = 75
    var p1 = 0f
    var lvl = 0

    override fun onSizeChanged(width: Int, height: Int, oldwidth: Int, oldheight: Int)
    {
        super.onSizeChanged(width, height, oldwidth, oldheight)
        score.textSize = 120f
        levelD.textSize = 75f
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
        canvas?.drawRoundRect(p1, (height - barH- 40f - 60f).toFloat(), p1 + barW, (height- 40f - 60f).toFloat(),20f,20f, voila)

        //Bar Opponent
        canvas?.drawRoundRect(x1 + 100f, 40f, x1 - 100f , 115f,20f,20f, oppo)

        //score
        canvas?.drawText(point2.toString(), width.toFloat()/2 , height.toFloat()/2 , score)

        //level
        canvas?.drawText(" Level ${lvl} ", width.toFloat() -170f, 175f, levelD)

        //hs
        canvas?.drawText("", width.toFloat()/2 , height.toFloat() - 10f, hs)

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
        powerReset()

    }
    fun powerUp(){
        if(runG == true)
            barW = 300
    }
    fun powerReset(){
        barW = 200
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


                if (y1 >= height - radius - barH - 40f - 60f) {
                    if (x1 in (p1 - 75)..(p1 + 75) + barW) {
                        y1 = height - radius - barH - 40f - 60f
                        dY *= -1
                        point2 += 1
                        player.start()


                        if (point2 % 1 == 0) {
                            lvl = point2/5 + 1
                            dX*= 1.025f
                            dY*= 1.025f
                        }

                    } else {
                        runG = false
                        gLoBal = point2
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