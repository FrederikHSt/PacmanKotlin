package org.pondar.pacmankotlin

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.TextView
import android.widget.Toast
import java.util.*

/*GAME LOGIC*/
class Game(private var context: Context,
           private var pointsView: TextView,
           private var countView: TextView, private var hiscoreView: TextView) {

    private var points: Int = 0
    private var pointsText: String = points.toString()

    var counter: Int = 0
    private var counterText: String = counter.toString()

    var hiscore: Int = 0
    private  var hiscoreText: String = hiscore.toString()

    // PACMAN MOVEMENT
    var pacx: Int = 0
    var pacy: Int = 0
    var running = false
    var direction = 0

    // BITMAPS
    var pacBitmap: Bitmap
    var coinBitmap: Bitmap
    var ghostBitmap: Bitmap

    // GAMEVIEW & WIDTH/HEIGHT
    private var gameView: GameView? = null
    private var h: Int = 0
    private var w: Int = 0

    // GAME OBJECTS
    var pacman = Pacman(3, 3, this) // PACMAN OBJECT
    var coin = GoldCoin(5, 5) // COIN OBJECT
    var ghost = Ghost(7, 7, pacman) // GHOST OBJECT (ENEMY)

    /*SETTING GRID SIZE*/
    companion object {
        var gridWidth: Int = 20
        var gridHeight: Int = 20
    }

    /*BITMAPS INITIALIZED*/
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

    /*CALLED WHEN 'NEW GAME' IS PRESSED IN MENU*/
    fun newGame() {
        pacman.x = 5
        pacman.y = 3
        coin.toRandomPosition()
        ghost.x = 15
        ghost.y = 15
        points = 0
        direction = 0
        counter = 60
        updateCountText()
        updateScore()
        gameView?.invalidate() // redraw screen
    }

    /*CALLED AFTER EACH MOVE FOR GHOST MOVE AND COLLISION CHECK*/
    fun nextTurn() {
        ghost.move()
        doCollisionCheck()
        gameView?.invalidate() //redraw
    }

    /*CHECK IF PACMAN IS TOUCHING ANY OTHER OBJECTS*/
    fun doCollisionCheck() {
        // COIN COLLISION
        if (coin.x == pacman.x && coin.y == pacman.y) {
            points++
            updateScore()
            coin.toRandomPosition()
            gameView?.invalidate() // redraw screen
        }
        // GHOST COLLISION
        if (ghost.x == pacman.x && ghost.y == pacman.y) {
            gameOver()
        }
    }

    fun count() {
        counter--
        updateCountText()
    }

    fun winGame() {
        if (hiscore < points) {
            hiscore = points
            updateHiscore()
        }
        Toast.makeText(context, "Time ran out", Toast.LENGTH_SHORT).show()
    }

    private fun updateCountText() {
        counterText = "Time: $counter"
        countView.text = counterText
    }

    /*GHOST COLLISION -> GAME OVER*/
    private fun gameOver() {
        hiscore = points
        updateHiscore()
        Toast.makeText(context, "Game Over", Toast.LENGTH_SHORT).show()
        newGame()
    }

    /*UPDATE POINTS ON SCREEN*/
    private fun updateScore() {
        pointsText = "Points: $points"
        pointsView.text = pointsText
    }

    private fun updateHiscore() {
        hiscoreText = "Hiscore: $hiscore"
        hiscoreView.text = hiscoreText
    }
}