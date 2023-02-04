package de.fsaurenbach.chess

import clicked
import com.soywiz.korge.input.*
import com.soywiz.korge.view.*
import fieldSize
import globalHeight
import globalWith
import lastClicked
import rectsBoard
import whiteTurn
import kotlin.math.abs

class Piece(var type: String, var color: String, pieceX: Int, pieceY: Int, image: Image) :
    Container() {
    private var piece = image
    private var oldPositionPair: Pair<Int, Int>? = null
    var moved = true

    var pawnFirstMove = false

    init {
        if (type == "Pawn") {
            pawnFirstMove = true
        }
        piece.scaledWidth = fieldSize / 1.000001
        piece.scaledHeight = fieldSize / 1.000001
        piece.xy(pieceX * globalWith / 8, pieceY * globalHeight / 8)
        addChild(piece)
        centerPiece(piece)
        move(piece)
    }

    fun setOldPositionPair(pair: Pair<Int, Int>) {
        oldPositionPair = pair
    }

    fun getOldPositionPair(): Pair<Int, Int>? {
        return oldPositionPair
    }

    private fun centerPiece(piece: Image) {
        piece.onCollision {
            if (it.name == "Board") {
                piece.centerOn(it)
            }
        }
    }

    private fun move(piece: Image) {

        piece.onUp {
            val lastClickedPiece: Piece? = (stage?.findViewByName(lastClicked) as Piece?)
            if (!clicked) {
                lastClicked = this.name!!
                clicked = true
            }
            if (lastClickedPiece != null) {
                println("This is the last clicked piece: $lastClicked")
                println("This is this piece: ${this.name}")
                val newX = this.getOldPositionPair()?.first
                val newY = this.getOldPositionPair()?.second
                val oldX = lastClickedPiece.getOldPositionPair()!!.first
                val oldY = lastClickedPiece.getOldPositionPair()!!.second
                if (
                    clicked &&
                        (lastClicked != this.name!!) &&
                        (lastClickedPiece.color != this.color)
                ) {
                    when (lastClickedPiece.color) {
                        "White" -> {
                            when (lastClickedPiece.type) {
                                "Pawn" -> {
                                    if (
                                        (newX == oldX + 1 || newX == oldX - 1) &&
                                            newY == oldY + 1 &&
                                            whiteTurn
                                    ) {
                                        this.removeFromParent()
                                        rectsBoard[newX to newY]?.let {
                                            lastClickedPiece.centerOn(it)
                                        }
                                        lastClickedPiece.setOldPositionPair(newX to newY)
                                        lastClickedPiece.moved = false
                                        clicked = false
                                        whiteTurn = false
                                    } else {
                                        clicked = false
                                        lastClicked = ""
                                    }
                                }
                                "Rook" -> {
                                    newX!!
                                    newY!!
                                    var canMove = true

                                    if (newX == oldX && newY != oldY && newY > oldY) {
                                        for (i in oldY + 1 until newY) {
                                            val square = rectsBoard[oldX to i]
                                            println("Square: $oldX to $i")
                                            square?.onCollision {
                                                if (it is Piece) {
                                                    canMove = false
                                                }
                                            }
                                        }
                                    } else if (newX == oldX && newY < oldY) {

                                        for (i in oldY - 1 downTo newY + 1) {
                                            val square = rectsBoard[oldX to i]
                                            println("Square: $oldX to $i")
                                            square?.onCollision {
                                                if (it is Piece) {
                                                    println(it.name)
                                                    println("local: $oldX to $i")
                                                    canMove = false
                                                }
                                            }
                                        }
                                    } else if (newY == oldY && newX > oldX) {
                                        for (i in oldX + 1 until newX) {
                                            val square = rectsBoard[i to oldY]
                                            println("Square: $i to $oldY")
                                            square?.onCollision {
                                                if (it is Piece) {
                                                    canMove = false
                                                }
                                            }
                                        }
                                    } else if (newY == oldY && newX < oldX) {
                                        for (i in oldX - 1 downTo newX + 1) {
                                            val square = rectsBoard[i to oldY]
                                            println("Square: $i to $oldY")
                                            square?.onCollision {
                                                if (it is Piece) {
                                                    canMove = false
                                                }
                                            }
                                        }
                                    } else {
                                        canMove = false
                                    }

                                    if (canMove && whiteTurn) {
                                        this.removeFromParent()
                                        lastClickedPiece.setOldPositionPair(newX to newY)
                                        rectsBoard[newX to newY]?.let {
                                            lastClickedPiece.centerOn(it)
                                        }
                                        lastClickedPiece.moved = false
                                        whiteTurn = false
                                    }
                                }
                                "Knight" -> {
                                    if (
                                        (newX == oldX + 1 || newX == oldX - 1) &&
                                            (newY == oldY + 2 || newY == oldY - 2) &&
                                            whiteTurn
                                    ) {
                                        this.removeFromParent()
                                        lastClickedPiece.setOldPositionPair(newX to newY)
                                        rectsBoard[newX to newY]?.let {
                                            lastClickedPiece.centerOn(it)
                                        }
                                        lastClickedPiece.moved = true
                                        whiteTurn = false
                                    } else if (
                                        (newX == oldX + 2 || newX == oldX - 2) &&
                                            (newY == oldY + 1 || newY == oldY - 1) &&
                                            whiteTurn
                                    ) {
                                        this.removeFromParent()
                                        lastClickedPiece.setOldPositionPair(newX to newY)
                                        rectsBoard[newX to newY]?.let {
                                            lastClickedPiece.centerOn(it)
                                        }
                                        lastClickedPiece.moved = true
                                        whiteTurn = false
                                    }
                                }
                                "Bishop" -> {
                                    var canMove = true
                                    newX!!
                                    newY!!
                                    val dx = newX - oldX
                                    val dy = newY - oldY
                                    if (abs(dx) == abs(dy)) {
                                        var row = oldY
                                        var col = oldX
                                        while (row != newY - 1 && col != newX - 1) {
                                            row += if (dy > 0) 1 else -1
                                            col += if (dx > 0) 1 else -1
                                            val square = rectsBoard[col to row]
                                            square!!.onCollision {
                                                if (it.name != lastClickedPiece.name) {
                                                    if (it is Piece) {
                                                        canMove = false
                                                    }
                                                }
                                            }
                                        }
                                    } else {
                                        canMove = false
                                    }

                                    if (canMove && whiteTurn) {
                                        this.removeFromParent()
                                        lastClickedPiece.setOldPositionPair(newX to newY)
                                        rectsBoard[newX to newY]?.let {
                                            lastClickedPiece.centerOn(it)
                                        }
                                        lastClickedPiece.moved = true
                                        whiteTurn = false
                                    }
                                }
                                "Queen" -> {

                                    newX!!
                                    newY!!
                                    var canMove = true

                                    if (newX == oldX && newY != oldY && newY > oldY) {
                                        for (i in oldY + 1 until newY) {
                                            val square = rectsBoard[oldX to i]
                                            println("Square: $oldX to $i")
                                            square?.onCollision {
                                                if (it is Piece) {
                                                    canMove = false
                                                }
                                            }
                                        }
                                    } else if (newX == oldX && newY < oldY) {

                                        for (i in oldY - 1 downTo newY + 1) {
                                            val square = rectsBoard[oldX to i]
                                            println("Square: $oldX to $i")
                                            square?.onCollision {
                                                if (it is Piece) {
                                                    println(it.name)
                                                    println("local: $oldX to $i")
                                                    canMove = false
                                                }
                                            }
                                        }
                                    } else if (newY == oldY && newX > oldX) {
                                        for (i in oldX + 1 until newX) {
                                            val square = rectsBoard[i to oldY]
                                            println("Square: $i to $oldY")
                                            square?.onCollision {
                                                if (it is Piece) {
                                                    canMove = false
                                                }
                                            }
                                        }
                                    } else if (newY == oldY && newX < oldX) {
                                        for (i in oldX - 1 downTo newX + 1) {
                                            val square = rectsBoard[i to oldY]
                                            println("Square: $i to $oldY")
                                            square?.onCollision {
                                                if (it is Piece) {
                                                    canMove = false
                                                }
                                            }
                                        }
                                    }

                                    /* Bishop like movement */

                                    /* Code for moving to the right and down */
                                    else if (newX > oldX && newY > oldY) {
                                        for (i in oldX + 1 until newX) {
                                            for (j in oldY + 1 until newY) {
                                                val square = rectsBoard[i to j]
                                                println("Square: $i to $j")
                                                square?.onCollision {
                                                    if (it is Piece) {
                                                        canMove = false
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    /* Code for moving to the left and down */
                                    else if (newX < oldX && newY > oldY) {
                                        for (i in oldX - 1 downTo newX + 1) {
                                            for (j in oldY + 1 until newY) {
                                                val square = rectsBoard[i to j]
                                                println("Square: $i to $j")
                                                square?.onCollision {
                                                    if (it is Piece) {
                                                        canMove = false
                                                    }
                                                }
                                            }
                                        }
                                    }

                                    /* Code for moving to the right and up */
                                    else if (newX > oldX && newY < oldY) {
                                        for (i in oldX + 1 until newX) {
                                            for (j in oldY - 1 downTo newY + 1) {
                                                val square = rectsBoard[i to j]
                                                println("Square: $i to $j")
                                                square?.onCollision {
                                                    if (it is Piece) {
                                                        canMove = false
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    /* Code for moving to the left and up */
                                    else if (newX < oldX && newY < oldY) {
                                        for (i in oldX - 1 downTo newX + 1) {
                                            for (j in oldY - 1 downTo newY + 1) {
                                                val square = rectsBoard[i to j]
                                                println("Square: $i to $j")
                                                square?.onCollision {
                                                    if (it is Piece) {
                                                        canMove = false
                                                    }
                                                }
                                            }
                                        }
                                    } else {
                                        canMove = false
                                    }

                                    if (canMove && whiteTurn) {
                                        this.removeFromParent()
                                        lastClickedPiece.setOldPositionPair(newX to newY)
                                        rectsBoard[newX to newY]?.let {
                                            lastClickedPiece.centerOn(it)
                                        }
                                        lastClickedPiece.moved = false
                                        whiteTurn = false
                                    }
                                }
                                "King" -> {
                                    /* Code for moving to the right and down */
                                    if (newX == oldX + 1 && newY == oldY + 1) {
                                        this.removeFromParent()
                                        lastClickedPiece.setOldPositionPair(newX to newY)
                                        rectsBoard[newX to newY]?.let {
                                            lastClickedPiece.centerOn(it)
                                        }
                                        lastClickedPiece.moved = true
                                        whiteTurn = false
                                    }


                                    /* Code for moving to the left and down */
                                    else if (newX == oldX - 1 && newY == oldY + 1) {
                                        this.removeFromParent()
                                        lastClickedPiece.setOldPositionPair(newX to newY)
                                        rectsBoard[newX to newY]?.let {
                                            lastClickedPiece.centerOn(it)
                                        }
                                        lastClickedPiece.moved = true
                                        whiteTurn = false
                                    }
                                    /* Code for moving to the right and up */
                                    else if (newX == oldX + 1 && newY == oldY - 1) {
                                        this.removeFromParent()
                                        lastClickedPiece.setOldPositionPair(newX to newY)
                                        rectsBoard[newX to newY]?.let {
                                            lastClickedPiece.centerOn(it)
                                        }
                                        lastClickedPiece.moved = true
                                        whiteTurn = false
                                    }
                                    /* Code for moving to the left and up */
                                    else if (newX == oldX - 1 && newY == oldY - 1) {
                                        this.removeFromParent()
                                        lastClickedPiece.setOldPositionPair(newX to newY)
                                        rectsBoard[newX to newY]?.let {
                                            lastClickedPiece.centerOn(it)
                                        }
                                        lastClickedPiece.moved = true
                                        whiteTurn = false
                                    }
                                }
                            }
                        }
                        "Black" -> {
                            when (lastClickedPiece.type) {
                                "Pawn" -> {
                                    if (
                                        (newX == oldX + 1 || newX == oldX - 1) &&
                                            newY == oldY - 1 &&
                                            !whiteTurn
                                    ) {
                                        this.removeFromParent()
                                        rectsBoard[newX to newY]?.let {
                                            lastClickedPiece.centerOn(it)
                                        }
                                        lastClickedPiece.setOldPositionPair(newX to newY)
                                        lastClickedPiece.moved = false
                                        clicked = false
                                        whiteTurn = true
                                    }
                                }
                                "Rook" -> {
                                    newX!!
                                    newY!!
                                    var canMove = true

                                    if (newX == oldX && newY != oldY && newY > oldY) {
                                        for (i in oldY + 1 until newY) {
                                            val square = rectsBoard[oldX to i]
                                            println("Square: $oldX to $i")
                                            square?.onCollision {
                                                if (it is Piece) {
                                                    canMove = false
                                                }
                                            }
                                        }
                                    } else if (newX == oldX && newY < oldY) {

                                        for (i in oldY - 1 downTo newY + 1) {
                                            val square = rectsBoard[oldX to i]
                                            println("Square: $oldX to $i")
                                            square?.onCollision {
                                                if (it is Piece) {
                                                    println(it.name)
                                                    println("local: $oldX to $i")
                                                    canMove = false
                                                }
                                            }
                                        }
                                    } else if (newY == oldY && newX > oldX) {
                                        for (i in oldX + 1 until newX) {
                                            val square = rectsBoard[i to oldY]
                                            println("Square: $i to $oldY")
                                            square?.onCollision {
                                                if (it is Piece) {
                                                    canMove = false
                                                }
                                            }
                                        }
                                    } else if (newY == oldY && newX < oldX) {
                                        for (i in oldX - 1 downTo newX + 1) {
                                            val square = rectsBoard[i to oldY]
                                            println("Square: $i to $oldY")
                                            square?.onCollision {
                                                if (it is Piece) {
                                                    canMove = false
                                                }
                                            }
                                        }
                                    } else {
                                        canMove = false
                                    }

                                    if (canMove && !whiteTurn) {
                                        this.removeFromParent()
                                        lastClickedPiece.setOldPositionPair(newX to newY)
                                        rectsBoard[newX to newY]?.let {
                                            lastClickedPiece.centerOn(it)
                                        }
                                        lastClickedPiece.moved = false
                                        clicked = false
                                        whiteTurn = true
                                    }
                                }
                                "Knight" -> {
                                    if (
                                        (newX == oldX + 1 || newX == oldX - 1) &&
                                            (newY == oldY + 2 || newY == oldY - 2) &&
                                            !whiteTurn
                                    ) {
                                        this.removeFromParent()
                                        lastClickedPiece.setOldPositionPair(newX to newY)
                                        rectsBoard[newX to newY]?.let {
                                            lastClickedPiece.centerOn(it)
                                        }
                                        lastClickedPiece.moved = true
                                        whiteTurn = true
                                    } else if (
                                        (newX == oldX + 2 || newX == oldX - 2) &&
                                            (newY == oldY + 1 || newY == oldY - 1) &&
                                            !whiteTurn
                                    ) {
                                        this.removeFromParent()
                                        lastClickedPiece.setOldPositionPair(newX to newY)
                                        rectsBoard[newX to newY]?.let {
                                            lastClickedPiece.centerOn(it)
                                        }
                                        lastClickedPiece.moved = true
                                        whiteTurn = true
                                    }
                                }
                                "Bishop" -> {
                                    var canMove = true
                                    val b = 9
                                    if (abs(newX!! - oldX) == abs(newY!! - oldY)) {
                                        if (newX > oldX && newY > oldY) {
                                            for (i in b until abs(newX - oldX) + 1) {
                                                if (
                                                    rectsBoard[oldX + i to oldY + i]?.name != null
                                                ) {
                                                    canMove = false
                                                }
                                            }
                                        } else if (newX > oldX && newY < oldY) {
                                            for (i in b until abs(newX - oldX) + 1) {
                                                if (
                                                    rectsBoard[oldX + i to oldY - i]?.name != null
                                                ) {
                                                    canMove = false
                                                }
                                            }
                                        } else if (newX < oldX && newY > oldY) {
                                            for (i in b until abs(newX - oldX) + 1) {
                                                if (
                                                    rectsBoard[oldX - i to oldY + i]?.name != null
                                                ) {
                                                    canMove = false
                                                }
                                            }
                                        } else if (newX < oldX && newY < oldY) {
                                            for (i in b until abs(newX - oldX) + 1) {
                                                if (
                                                    rectsBoard[oldX - i to oldY - i]?.name != null
                                                ) {
                                                    canMove = false
                                                }
                                            }
                                        }
                                    } else {
                                        canMove = false
                                    }
                                    if (canMove && !whiteTurn) {
                                        this.removeFromParent()
                                        lastClickedPiece.setOldPositionPair(newX to newY)
                                        rectsBoard[newX to newY]?.let {
                                            lastClickedPiece.centerOn(it)
                                        }
                                        lastClickedPiece.moved = true
                                        whiteTurn = true
                                    }
                                }
                                "Queen" -> {
                                    newX!!
                                    newY!!
                                    var canMove = true

                                    if (newX == oldX && newY != oldY && newY > oldY) {
                                        for (i in oldY + 1 until newY) {
                                            val square = rectsBoard[oldX to i]
                                            println("Square: $oldX to $i")
                                            square?.onCollision {
                                                if (it is Piece) {
                                                    canMove = false
                                                }
                                            }
                                        }
                                    } else if (newX == oldX && newY < oldY) {

                                        for (i in oldY - 1 downTo newY + 1) {
                                            val square = rectsBoard[oldX to i]
                                            println("Square: $oldX to $i")
                                            square?.onCollision {
                                                if (it is Piece) {
                                                    println(it.name)
                                                    println("local: $oldX to $i")
                                                    canMove = false
                                                }
                                            }
                                        }
                                    } else if (newY == oldY && newX > oldX) {
                                        for (i in oldX + 1 until newX) {
                                            val square = rectsBoard[i to oldY]
                                            println("Square: $i to $oldY")
                                            square?.onCollision {
                                                if (it is Piece) {
                                                    canMove = false
                                                }
                                            }
                                        }
                                    } else if (newY == oldY && newX < oldX) {
                                        for (i in oldX - 1 downTo newX + 1) {
                                            val square = rectsBoard[i to oldY]
                                            println("Square: $i to $oldY")
                                            square?.onCollision {
                                                if (it is Piece) {
                                                    canMove = false
                                                }
                                            }
                                        }
                                    }

                                    /* Bishop like movement */

                                    /* Code for moving to the right and down */
                                    else if (newX > oldX && newY > oldY) {
                                        for (i in oldX + 1 until newX) {
                                            for (j in oldY + 1 until newY) {
                                                val square = rectsBoard[i to j]
                                                println("Square: $i to $j")
                                                square?.onCollision {
                                                    if (it is Piece) {
                                                        canMove = false
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    /* Code for moving to the left and down */
                                    else if (newX < oldX && newY > oldY) {
                                        for (i in oldX - 1 downTo newX + 1) {
                                            for (j in oldY + 1 until newY) {
                                                val square = rectsBoard[i to j]
                                                println("Square: $i to $j")
                                                square?.onCollision {
                                                    if (it is Piece) {
                                                        canMove = false
                                                    }
                                                }
                                            }
                                        }
                                    }

                                    /* Code for moving to the right and up */
                                    else if (newX > oldX && newY < oldY) {
                                        for (i in oldX + 1 until newX) {
                                            for (j in oldY - 1 downTo newY + 1) {
                                                val square = rectsBoard[i to j]
                                                println("Square: $i to $j")
                                                square?.onCollision {
                                                    if (it is Piece) {
                                                        canMove = false
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    /* Code for moving to the left and up */
                                    else if (newX < oldX && newY < oldY) {
                                        for (i in oldX - 1 downTo newX + 1) {
                                            for (j in oldY - 1 downTo newY + 1) {
                                                val square = rectsBoard[i to j]
                                                println("Square: $i to $j")
                                                square?.onCollision {
                                                    if (it is Piece) {
                                                        canMove = false
                                                    }
                                                }
                                            }
                                        }
                                    } else {
                                        canMove = false
                                    }

                                    if (canMove && whiteTurn) {
                                        this.removeFromParent()
                                        lastClickedPiece.setOldPositionPair(newX to newY)
                                        rectsBoard[newX to newY]?.let {
                                            lastClickedPiece.centerOn(it)
                                        }
                                        lastClickedPiece.moved = false
                                        whiteTurn = false
                                    }
                                }
                            }
                        }
                    }

                    clicked = false
                    lastClicked = ""
                }
            }
        }
    }
}
