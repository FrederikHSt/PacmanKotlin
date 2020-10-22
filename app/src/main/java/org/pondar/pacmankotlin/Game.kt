package org.pondar.pacmankotlin

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.TextView
import android.widget.Toast
import java.util.ArrayList
import java.util.Random
import java.util.*


/**
 *
 * This class should contain all your game logic
 */

class Game(private var context: Context,view: TextView) {

    // POINTS
    private var pointsView: TextView = view
    private var points: Int = 0

    // COORDINATES
    var pacx: Int = 0
    var pacy: Int = 0
    private var randomizer = Random()

    // BITMAPS
    var pacBitmap: Bitmap
    var coinBitmap: Bitmap
    var ghostBitmap: Bitmap

    // COINS
    var coinsInitialized = false
    var coins = ArrayList<GoldCoin>() //the list of goldcoins - initially empty

    // GAMEVIEW & WIDTH/HEIGHT
    private var gameView: GameView? = null
    private var h: Int = 0
    private var w: Int = 0 //height and width of screen

    // GAME OBJECTS
    var pacman = Pacman(3, 3, this) // PACMAN OBJECT
    var coin = GoldCoin(3, 3) // COIN OBJECT
    var ghost = Ghost(7, 7, pacman) // GHOST OBJECT (ENEMY)
    var board: Array<Array<GameObject>> = arrayOf()

    companion object {
        var gridWidth: Int = 8
        var gridHeight: Int = 8
    }

    init {
        pacBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.pacman)
        coinBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.coin)
        ghostBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.ghost)
    }

    fun setGameView(view: GameView) {
        this.gameView = view
    }

    fun setSize(h: Int, w: Int) {
        this.h = h
        this.w = w
    }

    //TODO initialize goldcoins also here
    fun initializeGoldcoins() {
        //DO Stuff to initialize the array list with coins.

        //coins.add(GoldCoin((randomizer.ints(1, 0, 1000)), ints(1, 0, 1000))
        //coins.add(GoldCoin(getRandomNumber(), getRandomNumber()))

        //coins.add(GoldCoin(10, 100))
        //coins.add(GoldCoin(100, 100))
        //coins.add(GoldCoin(250, 300))
        //coins.add(GoldCoin(150, 100))
        //coins.add(GoldCoin(400, 10))
        //coinsInitialized = true
    }

    fun getRandomNumber(): Int {
        val randomNumber = (0..1000).random()
        return randomNumber
    }

    /*fun newGame() {
        pacx = 50
        pacy = 400 //just some starting coordinates - you can change this.
        //reset the points
        coins.removeAll(coins)
        coinsInitialized = false
        points = 0
        pointsView.text = "${context.resources.getString(R.string.points)} $points"
        gameView?.invalidate() //redraw screen
    }*/
    fun newGame() {
        pacman.x = 4
        pacman.y = 4
        coin.toRandomPosition()
        ghost.x = 7
        ghost.y = 7
        points = 0
        pointsView.text = "${context.resources.getString(R.string.points)} $points"
        gameView?.invalidate() // redraw screen
    }


    fun nextTurn() {
        ghost.move()
        doCollisionCheck()
        gameView?.invalidate() //redraw
    }

    //TODO check if the pacman touches a gold coin
    //and if yes, then update the neccesseary data
    //for the gold coins and the points
    //so you need to go through the arraylist of goldcoins and
    //check each of them for a collision with the pacman
    fun doCollisionCheck() {
        if (coin.x == pacman.x && coin.y == pacman.y) {
            points++
            updateScore()
            coin.toRandomPosition()
            gameView?.invalidate() // redraw screen
        }
        if (ghost.x == pacman.x && ghost.y == pacman.y) {
            gameOver()
        }
    }

    fun gameOver() {
        Toast.makeText(context, "Game Over", Toast.LENGTH_SHORT).show()
        newGame()
    }

    fun updateScore() {
        pointsView.text = "${context.resources.getString(R.string.points)} $points"
    }
}