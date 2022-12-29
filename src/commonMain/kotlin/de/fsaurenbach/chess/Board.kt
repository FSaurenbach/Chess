package de.fsaurenbach.chess

import clicked
import com.soywiz.korge.input.*
import com.soywiz.korge.view.*
import com.soywiz.korim.color.*
import fieldSize
import lastClicked
import rectsBoard
import whiteTurn
import kotlin.math.*

class Board : Container() {

    private val white = Colors["#f0d9b5"]
    private val black = Colors["#b58863"]
    private var rect: SolidRect? = null

    init {
        for (i in 0..7)
            for (j in 0..7) {
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
            text("${8 - i}", textSize = 25.0, color = if (i % 2 != 0) black else white)
                .xy(8 * fieldSize - 15.0, 1 + i * fieldSize)
                .addTo(this)
        }
        val letters = "abcdefgh"
        for (i in 0..7) {
            text(letters[i] + "", textSize = 25.0, color = if (i % 2 != 0) black else white)

                .xy(3 + i * fieldSize, 8 * fieldSize - 30.0)
                .addTo(this)
        }

    }

    private fun move(pair: Pair<Int, Int>) {
        if (clicked) {
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
                            } else if (newX == oldX && newY == oldY + 2 && lastClickedPiece.pawnFirstMove && whiteTurn) {
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

                            if (canMove) {
                                lastClickedPiece.setOldPositionPair(newX to newY)
                                rectsBoard[pair]?.let { lastClickedPiece.centerOn(it) }
                                lastClickedPiece.moved = true
                                whiteTurn = !whiteTurn
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
                                // The bishop is not moving diagonally
                                canMove = false
                            }

                            if (canMove) {
                                lastClickedPiece.setOldPositionPair(newX to newY)
                                rectsBoard[pair]?.let { lastClickedPiece.centerOn(it) }
                                lastClickedPiece.moved = true
                                whiteTurn = !whiteTurn
                            }
                        }
                    }
                }
            }


        }
    }


}
