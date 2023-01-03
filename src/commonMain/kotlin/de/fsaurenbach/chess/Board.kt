package de.fsaurenbach.chess

import clicked
import com.soywiz.korge.input.*
import com.soywiz.korge.view.*
import com.soywiz.korim.color.*
import fieldSize
import lastClicked
import rectsBoard
import whiteKingCheck
import whitePieces
import whiteTurn
import kotlin.math.*

class Board : Container() {

    private val white = Colors["#f0d9b5"]
    private val black = Colors["#b58863"]
    private var rect: SolidRect? = null

    init {
        for (i in 0..7) for (j in 0..7) {
            rect = SolidRect(fieldSize, fieldSize, if ((j + i) % 2 != 0) black else white)
            rect!!.xy(i * fieldSize, j * fieldSize)
            rect!!.name = "Board"
            addChild(rect!!)
            rectsBoard[i to j] = rect!!
            rect.onUp {
                move(i to j)
            }
            rect!!.onCollision {
                if (it is Piece) {
                    if (it.moved) {
                        it.setOldPositionPair(i to j)
                        it.moved = false
                    }
                }
            }
        }

        for (i in 0..7) {
            text("${8 - i}", textSize = 25.0, color = if (i % 2 != 0) black else white).xy(
                8 * fieldSize - 15.0, 1 + i * fieldSize
            ).addTo(this)
        }
        val letters = "abcdefgh"
        for (i in 0..7) {
            text(letters[i] + "", textSize = 25.0, color = if (i % 2 != 0) black else white)

                .xy(3 + i * fieldSize, 8 * fieldSize - 30.0).addTo(this)
        }

    }

    private fun move(pair: Pair<Int, Int>) {
        val whiteKing: Piece = (stage!!.findViewByName("WhiteKing") as Piece)
        val blackKing: Piece = (stage!!.findViewByName("BlackKing") as Piece)

        if (clicked) {
            blackKingInCheck(blackKing)
            val lastClickedPiece: Piece = (stage!!.findViewByName(lastClicked) as Piece?)!!


            val newX = pair.first
            val newY = pair.second
            val oldX = lastClickedPiece.getOldPositionPair()!!.first
            val oldY = lastClickedPiece.getOldPositionPair()!!.second

            println("oldX: $oldX, oldY: $oldY, newX: $newX, newY: $newY")
            clicked = false
            when (lastClickedPiece.color) {
                "White" -> {
                    when (lastClickedPiece.type) {
                        "Pawn" -> {
                            if (newX == oldX && newY == oldY + 1 && whiteTurn) {

                                lastClickedPiece.pawnFirstMove = false
                                lastClickedPiece.setOldPositionPair(newX to newY)
                                rectsBoard[pair]?.let { lastClickedPiece.centerOn(it) }
                                lastClickedPiece.moved = true
                                whiteTurn = false
                            } else if ((newX == oldX) && (newY == (oldY + 2)) && lastClickedPiece.pawnFirstMove && whiteTurn) {
                                lastClickedPiece.pawnFirstMove = false

                                lastClickedPiece.setOldPositionPair(newX to newY)
                                rectsBoard[pair]?.let { lastClickedPiece.centerOn(it) }
                                lastClickedPiece.moved = true
                                whiteTurn = false

                            }
                        }

                        "Rook" -> {
                            var canMove = true
                            var row = oldY
                            while (row <= newY) {
                                var col = oldX
                                while (col <= newX) {
                                    val square = rectsBoard[col to row]
                                    square!!.onCollision {
                                        if (it.name != lastClickedPiece.name) {
                                            if (it is Piece) {
                                                canMove = false
                                            }

                                        }
                                    }
                                    col++
                                }
                                row++
                            }



                            if (newY == oldY && newX != oldX && whiteTurn && canMove) {
                                lastClickedPiece.setOldPositionPair(newX to newY)
                                rectsBoard[pair]?.let { lastClickedPiece.centerOn(it) }
                                lastClickedPiece.moved = true
                                whiteTurn = false
                            } else if (newX == oldX && newY != oldY && whiteTurn && canMove) {
                                lastClickedPiece.setOldPositionPair(newX to newY)
                                rectsBoard[pair]?.let { lastClickedPiece.centerOn(it) }
                                lastClickedPiece.moved = true
                                whiteTurn = false
                            }
                        }

                        "Knight" -> {
                            if ((newX == oldX + 1 || newX == oldX - 1) && (newY == oldY + 2 || newY == oldY - 2) && whiteTurn) {
                                lastClickedPiece.setOldPositionPair(newX to newY)
                                rectsBoard[pair]?.let { lastClickedPiece.centerOn(it) }
                                lastClickedPiece.moved = true
                                whiteTurn = false
                            } else if ((newX == oldX + 2 || newX == oldX - 2) && (newY == oldY + 1 || newY == oldY - 1) && whiteTurn) {
                                lastClickedPiece.setOldPositionPair(newX to newY)
                                rectsBoard[pair]?.let { lastClickedPiece.centerOn(it) }
                                lastClickedPiece.moved = true
                                whiteTurn = false
                            }
                        }

                        "Bishop" -> {
                            var canMove = true
                            val dx = newX - oldX
                            val dy = newY - oldY
                            if (abs(dx) == abs(dy)) {
                                var row = oldY
                                var col = oldX
                                while (row != newY && col != newX) {
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
                                lastClickedPiece.setOldPositionPair(newX to newY)
                                rectsBoard[pair]?.let { lastClickedPiece.centerOn(it) }
                                lastClickedPiece.moved = true
                                whiteTurn = false
                            }
                        }

                        "Queen" -> {
                            var canMove = true
                            val dx = newX - oldX
                            val dy = newY - oldY/* Rook like movement */

                            if (((newY == oldY && newX != oldX) || (newY != oldY && newX == oldX)) && whiteTurn) {
                                var row = oldY
                                while (row <= newY) {
                                    var col = oldX
                                    while (col <= newX) {
                                        val square = rectsBoard[col to row]
                                        square!!.onCollision {
                                            if (it.name != lastClickedPiece.name) {
                                                if (it is Piece) {
                                                    canMove = false
                                                }

                                            }
                                        }
                                        col++
                                    }
                                    row++
                                }
                            }

                            /* Bishop like movement */
                            else if (newY != oldY && newX != oldX && whiteTurn) {
                                if (abs(dx) == abs(dy)) {
                                    var row = oldY
                                    var col = oldX
                                    while (row != newY && col != newX) {
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

                            } else {
                                canMove = false
                            }



                            if (canMove && whiteTurn) {
                                lastClickedPiece.setOldPositionPair(newX to newY)
                                rectsBoard[pair]?.let { lastClickedPiece.centerOn(it) }
                                lastClickedPiece.moved = true
                                whiteTurn = false
                            }
                        }

                        "King" -> {
                            var canMove = true
                            val dx = newX - oldX
                            val dy = newY - oldY
                            println("dx: $dx, dy: $dy")/* Rook like movement but only one square at a time*/

                            if (((newY == oldY && newX != oldX) || (newY != oldY && newX == oldX))) {
                                if (abs(dx) == 1 || abs(dy) == 1) {
                                    var row = oldY
                                    while (row <= newY) {
                                        var col = oldX
                                        while (col <= newX) {
                                            val square = rectsBoard[col to row]
                                            square!!.onCollision {
                                                if (it.name != lastClickedPiece.name) {
                                                    if (it is Piece) {
                                                        canMove = false
                                                        return@onCollision
                                                    }

                                                }
                                            }
                                            col++
                                        }
                                        row++
                                    }
                                } else {
                                    canMove = false
                                }
                            }

                            /* Bishop like movement but only one square at a time*/
                            else if (newY != oldY && newX != oldX) {

                                if (abs(dx) == abs(dy)) {
                                    if (abs(dx) == 1) {
                                        var row = oldY
                                        var col = oldX
                                        while (row != newY && col != newX) {
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
                                } else {
                                    canMove = false
                                }

                            } else {
                                canMove = false
                            }
                            if (canMove && whiteTurn) {
                                lastClickedPiece.setOldPositionPair(newX to newY)
                                rectsBoard[pair]?.let { lastClickedPiece.centerOn(it) }
                                lastClickedPiece.moved = true
                                whiteTurn = false
                            }
                        }
                    }
                }

                "Black" -> {
                    when (lastClickedPiece.type) {
                        "Pawn" -> {
                            if (newX == oldX && newY == oldY - 1 && !whiteTurn) {
                                lastClickedPiece.pawnFirstMove = false
                                lastClickedPiece.setOldPositionPair(newX to newY)
                                rectsBoard[pair]?.let { lastClickedPiece.centerOn(it) }
                                lastClickedPiece.moved = true
                                whiteTurn = true
                            } else if (newX == oldX && newY == oldY - 2 && lastClickedPiece.pawnFirstMove && !whiteTurn) {
                                lastClickedPiece.pawnFirstMove = false

                                lastClickedPiece.setOldPositionPair(newX to newY)
                                rectsBoard[pair]?.let { lastClickedPiece.centerOn(it) }
                                lastClickedPiece.moved = true
                                whiteTurn = true

                            }
                        }

                        "Rook" -> {
                            var canMove = true
                            var row = oldY
                            // Move checker but for the black rook
                            while (row >= newY) {
                                var col = oldX
                                while (col >= newX) {
                                    val square = rectsBoard[col to row]
                                    square!!.onCollision {
                                        if (it.name != lastClickedPiece.name) {
                                            if (it is Piece) {
                                                canMove = false
                                            }

                                        }
                                    }
                                    col--
                                }
                                row--
                            }

                            if (newY == oldY && newX != oldX && !whiteTurn && canMove) {
                                lastClickedPiece.setOldPositionPair(newX to newY)
                                rectsBoard[pair]?.let { lastClickedPiece.centerOn(it) }
                                lastClickedPiece.moved = true
                                whiteTurn = true
                            } else if (newX == oldX && newY != oldY && !whiteTurn && canMove) {
                                lastClickedPiece.setOldPositionPair(newX to newY)
                                rectsBoard[pair]?.let { lastClickedPiece.centerOn(it) }
                                lastClickedPiece.moved = true
                                whiteTurn = true
                            }
                        }

                        "Knight" -> {
                            if ((newX == oldX + 1 || newX == oldX - 1) && (newY == oldY + 2 || newY == oldY - 2) && !whiteTurn) {
                                lastClickedPiece.setOldPositionPair(newX to newY)
                                rectsBoard[pair]?.let { lastClickedPiece.centerOn(it) }
                                lastClickedPiece.moved = true
                                whiteTurn = true
                            } else if ((newX == oldX + 2 || newX == oldX - 2) && (newY == oldY + 1 || newY == oldY - 1) && !whiteTurn) {
                                lastClickedPiece.setOldPositionPair(newX to newY)
                                rectsBoard[pair]?.let { lastClickedPiece.centerOn(it) }
                                lastClickedPiece.moved = true
                                whiteTurn = true
                            }
                        }

                        "Bishop" -> {
                            var canMove = true
                            val dx = newX - oldX
                            val dy = newY - oldY
                            if (abs(dx) == abs(dy)) {
                                var row = oldY
                                var col = oldX
                                while (row != newY && col != newX) {
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

                            if (canMove && !whiteTurn) {
                                lastClickedPiece.setOldPositionPair(newX to newY)
                                rectsBoard[pair]?.let { lastClickedPiece.centerOn(it) }
                                lastClickedPiece.moved = true
                                whiteTurn = true
                            }
                        }

                        "Queen" -> {
                            var canMove = true
                            val dx = newX - oldX
                            val dy = newY - oldY/* Rook like movement */
                            if (newY == oldY && newX != oldX) {
                                var row = oldY
                                while (row >= newY) {
                                    var col = oldX
                                    while (col >= newX) {
                                        val square = rectsBoard[col to row]
                                        square!!.onCollision {
                                            if (it.name != lastClickedPiece.name) {
                                                if (it is Piece) {
                                                    canMove = false
                                                }

                                            }
                                        }
                                        col--
                                    }
                                    row--
                                }
                            } else if (newX == oldX && newY != oldY) {
                                var row = oldY
                                while (row >= newY) {
                                    var col = oldX
                                    while (col >= newX) {
                                        val square = rectsBoard[col to row]
                                        square!!.onCollision {
                                            if (it.name != lastClickedPiece.name) {
                                                if (it is Piece) {
                                                    canMove = false
                                                }

                                            }
                                        }
                                        col--
                                    }
                                    row--
                                }

                            }/* Bishop like movement */
                            else if (newX != oldX && newY != oldY) {
                                if (abs(dx) == abs(dy)) {
                                    var row = oldY
                                    var col = oldX
                                    while (row != newY && col != newX) {
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
                                }
                            } else {
                                canMove = false
                            }
                            if (canMove && !whiteTurn) {
                                lastClickedPiece.setOldPositionPair(newX to newY)
                                rectsBoard[pair]?.let { lastClickedPiece.centerOn(it) }
                                lastClickedPiece.moved = true
                                whiteTurn = true
                            }
                        }
                        "King" -> {
                            if ((newX == oldX + 1 || newX == oldX - 1) && (newY == oldY + 1 || newY == oldY - 1) && !whiteTurn) {
                                lastClickedPiece.setOldPositionPair(newX to newY)
                                rectsBoard[pair]?.let { lastClickedPiece.centerOn(it) }
                                lastClickedPiece.moved = true
                                whiteTurn = true
                            } else if ((newX == oldX + 1 || newX == oldX - 1) && newY == oldY && !whiteTurn) {
                                lastClickedPiece.setOldPositionPair(newX to newY)
                                rectsBoard[pair]?.let { lastClickedPiece.centerOn(it) }
                                lastClickedPiece.moved = true
                                whiteTurn = true
                            } else if ((newY == oldY + 1 || newY == oldY - 1) && newX == oldX && !whiteTurn) {
                                lastClickedPiece.setOldPositionPair(newX to newY)
                                rectsBoard[pair]?.let { lastClickedPiece.centerOn(it) }
                                lastClickedPiece.moved = true
                                whiteTurn = true
                            }
                        }

                    }
                }
            }
            blackKingInCheck(blackKing)


        }
    }


}

fun blackKingInCheck(blackKing: Piece) {
    val blackKingX = blackKing.getOldPositionPair()!!.first
    val blackKingY = blackKing.getOldPositionPair()!!.second
    println(whiteKingCheck)
    var somethingfound = false
    for (bp in whitePieces!!) {
        val bpx = bp.getOldPositionPair()!!.first
        val bpy = bp.getOldPositionPair()!!.second

        when (bp.type) {

            "Pawn" -> {
                if (blackKingX == bpx + 1 && blackKingY == bpy + 1) {
                    whiteKingCheck = true
                    somethingfound = true
                } else if (blackKingX == bpx - 1 && blackKingY == bpy + 1) {
                    whiteKingCheck = true
                    somethingfound = true
                }
            }

            "Knight" -> {
                if (blackKingX == bpx + 1 && blackKingY == bpy + 2) {
                    whiteKingCheck = true
                    somethingfound = true
                } else if (blackKingX == bpx - 1 && blackKingY == bpy + 2) {
                    whiteKingCheck = true
                    somethingfound = true
                } else if (blackKingX == bpx + 2 && blackKingY == bpy + 1) {
                    whiteKingCheck = true
                    somethingfound = true
                } else if (blackKingX == bpx - 2 && blackKingY == bpy + 1) {
                    whiteKingCheck = true
                    somethingfound = true
                }
            }
        }
    }
    if (!somethingfound) {
        whiteKingCheck = false
    }
//    if (((whiteKingX == newX + 1) || (whiteKingX == newX - 1)) && whiteKingY == newY + 1) {
//        println("White King is in check")
//        whiteKingCheck = true
//    }

}
