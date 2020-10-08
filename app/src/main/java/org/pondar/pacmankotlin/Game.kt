package org.pondar.pacmankotlin

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.TextView
import java.util.ArrayList
import java.util.Random


/**
 *
 * This class should contain all your game logic
 */

class Game(private var context: Context,view: TextView) {

    private var pointsView: TextView = view
    private var points: Int = 0

    private var randomizer = Random()

    //bitmap of the pacman
    var pacBitmap: Bitmap

    //var coinBitmap: Bitmap
    var pacx: Int = 0
    var pacy: Int = 0


    //did we initialize the coins?
    var coinsInitialized = false

    //the list of goldcoins - initially empty
    var coins = ArrayList<GoldCoin>()

    //a reference to the gameview
    private var gameView: GameView? = null
    private var h: Int = 0
    private var w: Int = 0 //height and width of screen


    init {
        pacBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.pacman)
        //coinBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.coin)
    }

    fun setGameView(view: GameView) {
        this.gameView = view
    }

    //TODO initialize goldcoins also here
    fun initializeGoldcoins() {
        //DO Stuff to initialize the array list with coins.

        //coins.add(GoldCoin((randomizer.ints(1, 0, 1000)), ints(1, 0, 1000))
        coins.add(GoldCoin(getRandomNumber(), getRandomNumber()))

        //coins.add(GoldCoin(10, 100))
        //coins.add(GoldCoin(100, 100))
        //coins.add(GoldCoin(250, 300))
        //coins.add(GoldCoin(150, 100))
        //coins.add(GoldCoin(400, 10))
        coinsInitialized = true
    }

    fun getRandomNumber(): Int {
        val randomNumber = (0..1000).random()
        return randomNumber
    }

    fun newGame() {
        pacx = 50
        pacy = 400 //just some starting coordinates - you can change this.
        //reset the points
        coins.removeAll(coins)
        coinsInitialized = false
        points = 0
        pointsView.text = "${context.resources.getString(R.string.points)} $points"
        gameView?.invalidate() //redraw screen
    }

    fun setSize(h: Int, w: Int) {
        this.h = h
        this.w = w
    }

    fun movePacmanRight(pixels: Int) {
        //still within our boundaries?
        if (pacx + pixels + pacBitmap.width < w) {
            pacx = pacx + pixels
            doCollisionCheck()
            gameView!!.invalidate()
        }
    }

    fun movePacmanLeft(pixels: Int) {
        if (pacx + pixels + pacBitmap.width < w) {
            pacx = pacx - pixels
            doCollisionCheck()
            gameView!!.invalidate()
        }
    }

    fun movePacmanUp(pixels: Int) {
        if (pacy + pixels + pacBitmap.height < h) {
            pacy = pacy - pixels
            doCollisionCheck()
            gameView!!.invalidate()
        }
    }

    fun movePacmanDown(pixels: Int) {
        if (pacy + pixels + pacBitmap.height < h) {
            pacy = pacy + pixels
            doCollisionCheck()
            gameView!!.invalidate()
        }
    }

    //TODO check if the pacman touches a gold coin
    //and if yes, then update the neccesseary data
    //for the gold coins and the points
    //so you need to go through the arraylist of goldcoins and
    //check each of them for a collision with the pacman
    fun doCollisionCheck() {
        for (GoldCoin in coins) {
            //if (!GoldCoin.taken) {
            if (GoldCoin.x.toFloat() == pacx.toFloat() || GoldCoin.y.toFloat() == pacy.toFloat()) {
                points += 10
                //coins.remove(GoldCoin)
                coins.add(GoldCoin(getRandomNumber(), getRandomNumber()))
                GoldCoin.taken = true
                updateScore()


                //pointsView.text = "${context.resources.getString(R.string.points)} $points"
            }
            //}

        }
    }

    fun updateScore() {
        pointsView.text = "${context.resources.getString(R.string.points)} $points"
    }
}