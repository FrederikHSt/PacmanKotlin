package org.pondar.pacmankotlin

import android.graphics.Bitmap
import android.graphics.drawable.Drawable

//Here you need to fill out what should be in a GoldCoin and what should the constructor be
class GoldCoin(arrayX: Int, arrayY: Int) {
    var x = 0
    var y = 0

    var taken = false

    init {
        x = arrayX
        y = arrayY
    }


}