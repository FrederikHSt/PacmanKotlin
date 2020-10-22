package org.pondar.pacmankotlin

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.core.content.res.ResourcesCompat


//note we now create our own view class that extends the built-in View class
class GameView : View {

    private var game: Game? = null
    private var h: Int = 0
    private var w: Int = 0 //used for storing our height and width of the view

    fun setGame(game: Game?) {
        this.game = game
    }


    /* The next 3 constructors are needed for the Android view system,
	when we have a custom view.
	 */
    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)


    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    //In the onDraw we put all our code that should be
    //drawn whenever we update the screen.
    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        //Here we get the height and weight
        h = canvas.height
        w = canvas.width
        //update the size for the canvas to the game.
        game?.setSize(h, w)
        Log.d("GAMEVIEW", "h = $h, w = $w")

        //are the coins initiazlied?
        //if (!(game!!.coinsInitialized))
            //game?.initializeGoldcoins()


        //Making a new paint object
        val paint = Paint()
        canvas.drawColor(Color.WHITE) //clear entire canvas to white color

        //draw the pacman
        //canvas.drawBitmap(game!!.pacBitmap, game?.pacx!!.toFloat(),
                //game?.pacy!!.toFloat(), paint)

        canvas.drawBitmap(game!!.pacBitmap, null, RectF(
                game?.pacman?.canvasX(w, game?.pacBitmap!!.width)!!,
                game?.pacman?.canvasY(h, game?.pacBitmap!!.height)!!,
                game?.pacman?.canvasX(w, game?.pacBitmap!!.width)!! + (w / Game.gridWidth),
                game?.pacman?.canvasY(h, game?.pacBitmap!!.height)!! + (h / Game.gridHeight)
        ), paint)

        // Draw coins
        canvas.drawBitmap(game!!.coinBitmap, null, RectF(
                game?.coin?.canvasX(w, game?.coinBitmap!!.width)!!,
                game?.coin?.canvasY(h, game?.coinBitmap!!.height)!!,
                game?.coin?.canvasX(w, game?.coinBitmap!!.width)!! + (w / Game.gridWidth),
                game?.coin?.canvasY(h, game?.coinBitmap!!.height)!! + (h / Game.gridHeight)
        ), paint)

        // Draw the Ghost
        canvas.drawBitmap(
                game!!.ghostBitmap, null, RectF(
                game?.ghost?.canvasX(w, game?.ghostBitmap!!.width)!!,
                game?.ghost?.canvasY(h, game?.ghostBitmap!!.height)!!,
                game?.ghost?.canvasX(w, game?.ghostBitmap!!.width)!! + (w / Game.gridWidth),
                game?.ghost?.canvasY(h, game?.ghostBitmap!!.height)!! + (h / Game.gridHeight)
        ), paint)


        //TODO loop through the list of goldcoins and draw them.
        /*for (GoldCoin in game!!.coins) {
            if (!GoldCoin.taken) {
                //ResourcesCompat.getDrawable(context.resources, R.drawable.coin, null)
                val paint = Paint()
                paint.color = Color.YELLOW
                canvas.drawCircle(GoldCoin.x.toFloat(), GoldCoin.y.toFloat(), 30f, paint)
            } else {
                paint.color = Color.WHITE
                canvas.drawCircle(GoldCoin.x.toFloat(), GoldCoin.y.toFloat(), 30f, paint)
            }
        }*/

        game?.doCollisionCheck()
        super.onDraw(canvas)
    }

}
