package org.pondar.pacmankotlin

class Pacman constructor(override var x: Int, override var y: Int, var game: Game): GameObject {

    fun doMove(direction: String) {
        if (direction == "RIGHT") {
            move(1,0)
        } else if (direction == "LEFT") {
            move(-1, 0)
        } else if (direction == "UP") {
            move(0, -1)
        } else if (direction == "DOWN") {
            move(0, 1)
        }
    }

    private fun move(xx: Int, yy: Int) {
        this.x += xx
        this.y += yy
        this.x = this.x.coerceIn(0 until Game.gridHeight)
        this.y = this.y.coerceIn(0 until Game.gridWidth)
        game.nextTurn()
    }
}