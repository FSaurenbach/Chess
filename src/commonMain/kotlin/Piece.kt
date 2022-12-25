import com.soywiz.korge.input.*
import com.soywiz.korge.view.*

class Piece(var type: String, var color: String, pieceX: Int, pieceY: Int, image: Image) : Container() {
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
                val newX = this.getOldPositionPair()?.first
                val newY = this.getOldPositionPair()?.second
                val oldX = lastClickedPiece.getOldPositionPair()!!.first
                val oldY = lastClickedPiece.getOldPositionPair()!!.second
                if (clicked && (lastClicked != this.name!!) && (lastClickedPiece.color != this.color)) {
                    when (lastClickedPiece.color) {
                        "White" -> {
                            when (lastClickedPiece.type) {
                                "Pawn" -> {
                                    if ((newX == oldX + 1 || newX == oldX - 1) && newY == oldY + 1 && whiteTurn) {
                                        this.removeFromParent()
                                        rectsBoard[newX to newY]?.let { lastClickedPiece.centerOn(it) }
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
                                    var canMove = true
                                    // Check if there is a piece in the way but not the last one
                                    if (newX == oldX) {
                                        if (newY!! > oldY) {
                                            for (i in oldY + 1 until newY) {
                                                if (rectsBoard[newX to i]?.name != null) {
                                                    canMove = false
                                                }
                                            }
                                        } else {
                                            for (i in newY + 1 until oldY) {
                                                if (rectsBoard[newX to i]?.name != null) {
                                                    canMove = false
                                                }
                                            }
                                        }
                                    } else if (newY == oldY) {
                                        if (newX!! > oldX) {
                                            for (i in oldX + 1 until newX!!) {
                                                if (rectsBoard[i to newY]?.name != null) {
                                                    canMove = false
                                                }
                                            }
                                        } else {
                                            for (i in newX + 1 until oldX) {
                                                if (rectsBoard[i to newY]?.name != null) {
                                                    canMove = false
                                                }
                                            }
                                        }
                                    } else {
                                        canMove = false
                                    }

                                    if (newY == oldY && newX != oldX && whiteTurn && canMove) {
                                        this.removeFromParent()
                                        lastClickedPiece.setOldPositionPair(newX!! to newY)
                                        rectsBoard[newX to newY]?.let { lastClickedPiece.centerOn(it) }
                                        lastClickedPiece.moved = true
                                        whiteTurn = false
                                    } else if (newX == oldX && newY != oldY && whiteTurn && canMove) {
                                        this.removeFromParent()
                                        lastClickedPiece.setOldPositionPair(newX to newY!!)
                                        rectsBoard[newX to newY]?.let { lastClickedPiece.centerOn(it) }
                                        lastClickedPiece.moved = true
                                        whiteTurn = false
                                    }
                                }

                                "Knight" -> {
                                    if ((newX == oldX + 1 || newX == oldX - 1) && (newY == oldY + 2 || newY == oldY - 2) && whiteTurn) {
                                        this.removeFromParent()
                                        lastClickedPiece.setOldPositionPair(newX!! to newY)
                                        rectsBoard[newX to newY]?.let { lastClickedPiece.centerOn(it) }
                                        lastClickedPiece.moved = true
                                        whiteTurn = false
                                    } else if ((newX == oldX + 2 || newX == oldX - 2) && (newY == oldY + 1 || newY == oldY - 1) && whiteTurn) {
                                        this.removeFromParent()
                                        lastClickedPiece.setOldPositionPair(newX to newY!!)
                                        rectsBoard[newX to newY]?.let { lastClickedPiece.centerOn(it) }
                                        lastClickedPiece.moved = true
                                        whiteTurn = false
                                    }
                                }

                            }

                        }

                        "Black" -> {
                            when (lastClickedPiece.type) {
                                "Pawn" -> {
                                    if ((newX == oldX + 1 || newX == oldX - 1) && newY == oldY - 1 && !whiteTurn) {
                                        this.removeFromParent()
                                        rectsBoard[newX to newY]?.let { lastClickedPiece.centerOn(it) }
                                        lastClickedPiece.setOldPositionPair(newX to newY)
                                        lastClickedPiece.moved = false
                                        clicked = false
                                        whiteTurn = true
                                    }
                                }

                                "Rook" -> {
                                    var canMove = true
                                    // Check if there is a piece in the way but not the last one
                                    if (newX == oldX) {
                                        if (newY!! > oldY) {
                                            for (i in oldY + 1 until newY) {
                                                if (rectsBoard[newX to i]?.name != null) {
                                                    canMove = false
                                                }
                                            }
                                        } else {
                                            for (i in newY + 1 until oldY) {
                                                if (rectsBoard[newX to i]?.name != null) {
                                                    canMove = false
                                                }
                                            }
                                        }
                                    } else if (newY == oldY) {
                                        if (newX!! > oldX) {
                                            for (i in oldX + 1 until newX!!) {
                                                if (rectsBoard[i to newY]?.name != null) {
                                                    canMove = false
                                                }
                                            }
                                        } else {
                                            for (i in newX + 1 until oldX) {
                                                if (rectsBoard[i to newY]?.name != null) {
                                                    canMove = false
                                                }
                                            }
                                        }
                                    } else {
                                        canMove = false
                                    }
                                    if (newY == oldY && newX != oldX && !whiteTurn && canMove) {
                                        this.removeFromParent()
                                        lastClickedPiece.setOldPositionPair(newX!! to newY)
                                        rectsBoard[newX to newY]?.let { lastClickedPiece.centerOn(it) }
                                        lastClickedPiece.moved = true
                                        whiteTurn = true
                                    } else if (newX == oldX && newY != oldY && !whiteTurn && canMove) {
                                        this.removeFromParent()
                                        lastClickedPiece.setOldPositionPair(newX to newY!!)
                                        rectsBoard[newX to newY]?.let { lastClickedPiece.centerOn(it) }
                                        lastClickedPiece.moved = true
                                        whiteTurn = true
                                    }
                                }

                                "Knight" -> {
                                    if ((newX == oldX + 1 || newX == oldX - 1) && (newY == oldY + 2 || newY == oldY - 2) && !whiteTurn) {
                                        this.removeFromParent()
                                        lastClickedPiece.setOldPositionPair(newX!! to newY)
                                        rectsBoard[newX to newY]?.let { lastClickedPiece.centerOn(it) }
                                        lastClickedPiece.moved = true
                                        whiteTurn = true
                                    } else if ((newX == oldX + 2 || newX == oldX - 2) && (newY == oldY + 1 || newY == oldY - 1) && !whiteTurn) {
                                        this.removeFromParent()
                                        lastClickedPiece.setOldPositionPair(newX to newY!!)
                                        rectsBoard[newX to newY]?.let { lastClickedPiece.centerOn(it) }
                                        lastClickedPiece.moved = true
                                        whiteTurn = true
                                    }
                                }

                            }

                        }
                    }
                    println("About to take piece: ${this.name} from $lastClicked")
                    clicked = false
                    lastClicked = ""
                }
            }

        }


    }

}

fun Board.move(pair: Pair<Int, Int>) {
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

                }
            }
        }


    }
}

