package org.pondar.pacmankotlin

import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    //reference to the game class.
    private var game: Game? = null
    //private var counter: Int = 0
    private var myTimer: Timer = Timer()
    private var countTimer: Timer = Timer()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setContentView(R.layout.activity_main)
        game = Game(this,pointsView,countView, hiscoreView)

        /*MAKING TIMER FOR MOVEMENT & TIMECOUNTER*/
        game?.running = true
        myTimer.schedule(object: TimerTask() {
            override fun run() {
                moveTimerMethod()
            }
        }, 0, 200)

        countTimer.schedule(object: TimerTask() {
            override fun run() {
                countTimerMethod()
            }
        }, 0, 1000)

        game?.setGameView(gameView)
        gameView.setGame(game)
        game?.newGame()
        /*BUTTON LOGIC*/
        pause.setOnClickListener {
            game?.running = false
        }
        resume.setOnClickListener {
            game?.running = true
        }
        moveRight.setOnClickListener {
            game?.direction = 3
        }
        moveLeft.setOnClickListener {
            game?.direction = 1
        }
        moveUp.setOnClickListener {
            game?.direction = 2
        }
        moveDown.setOnClickListener {
            game?.direction = 4
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.action_settings) {
            Toast.makeText(this, "settings clicked", Toast.LENGTH_LONG).show()
            return true
        } else if (id == R.id.action_newGame) {
            Toast.makeText(this, "New Game clicked", Toast.LENGTH_LONG).show()
            game?.newGame()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onStop() {
        super.onStop()
        myTimer.cancel()
        countTimer.cancel()
    }

    private fun moveTimerMethod() {
        this.runOnUiThread(timerTick)
    }

    private fun countTimerMethod() {
        this.runOnUiThread(countUp)
    }

    private val timerTick = Runnable {
        if (game!!.running) {
            if (game?.direction == 1) {
                game?.pacman?.doMove("LEFT")
            } else if (game?.direction == 2) {
                game?.pacman?.doMove("UP")
            } else if (game?.direction == 3) {
                game?.pacman?.doMove("RIGHT")
            } else if (game?.direction == 4) {
                game?.pacman?.doMove("DOWN")
            }
        }
    }

    private val countUp = Runnable {
        if (game!!.running) {
            game?.count()
        }
        if (game!!.counter == 0) {
            countTimer.cancel()
            game?.winGame()
            game?.newGame()
        }
    }
}
