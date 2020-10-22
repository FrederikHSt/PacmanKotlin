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

    //private var direction = 0
    private var counter: Int = 0

    private var myTimer: Timer = Timer()
    private var countTimer: Timer = Timer()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setContentView(R.layout.activity_main)
        game = Game(this,pointsView,countView)

        /*MAKING TIMER FOR MOVEMENT*/
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

        moveRight.setOnClickListener {
            /*if (game?.doCollisionCheck() != null) {
                direction = 3
            }*/
            game?.direction = 3
        }
        moveLeft.setOnClickListener {
            /*if (game?.doCollisionCheck() != null) {
                direction = 1
            }*/
            game?.direction = 1
        }
        moveUp.setOnClickListener {
            /*if (game?.doCollisionCheck() != null) {
                direction = 2
            }*/
            game?.direction = 2
        }
        moveDown.setOnClickListener {
            /*if (game?.doCollisionCheck() != null) {
                direction = 4
            }*/
            game?.direction = 4
        }


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
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
            //counter++
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
    }
}
