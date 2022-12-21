import com.soywiz.korge.input.onUp
import com.soywiz.korge.view.*

class Piece(var type: String, color: String, pieceX: Int, pieceY: Int, image: Image) : Container() {
    private var piece = image
    private var oldPositionPair: Pair<Int, Int>? = null
    var moved = true
    var color = color

    var pawnFirstMove = false

    init {
        if (type == "Pawn") {
            pawnFirstMove = true
        }
        piece.scaledWidth = fieldSize / 1.2
        piece.scaledHeight = fieldSize / 1.2
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
            if (!clicked) {
                lastClicked = this.name!!
                clicked = true

            }
        }


    }

}

fun Board.checkMove(pair: Pair<Int, Int>) {
    if (clicked) {
        val lastClickedPiece: Piece = (stage!!.findViewByName(lastClicked) as Piece?)!!
        val newX = pair.first
        val newY = pair.second
        val oldX = lastClickedPiece.getOldPositionPair()!!.first
        val oldY = lastClickedPiece.getOldPositionPair()!!.second
        clicked = false
        when (lastClickedPiece.color) {
            "White" -> {
                when (lastClickedPiece.type) {
                    "Pawn" -> {
                        if (newX == oldX && newY == oldY + 1) {
                            lastClickedPiece.pawnFirstMove = false
                            lastClickedPiece.setOldPositionPair(newX to newY)
                            rectsBoard[pair]?.let { lastClickedPiece.centerOn(it) }
                            lastClickedPiece.moved = true
                        } else if (newX == oldX && newY == oldY + 2 && lastClickedPiece.pawnFirstMove) {
                            lastClickedPiece.pawnFirstMove = false

                            lastClickedPiece.setOldPositionPair(newX to newY)
                            rectsBoard[pair]?.let { lastClickedPiece.centerOn(it) }
                            lastClickedPiece.moved = true

                        }
                    }

                }
            }
            "Black" -> {
                when (lastClickedPiece.type) {
                    "Pawn" -> {
                        if (newX == oldX && newY == oldY - 1) {
                            lastClickedPiece.pawnFirstMove = false
                            lastClickedPiece.setOldPositionPair(newX to newY)
                            rectsBoard[pair]?.let { lastClickedPiece.centerOn(it) }
                            lastClickedPiece.moved = true
                        } else if (newX == oldX && newY == oldY - 2 && lastClickedPiece.pawnFirstMove) {
                            lastClickedPiece.pawnFirstMove = false

                            lastClickedPiece.setOldPositionPair(newX to newY)
                            rectsBoard[pair]?.let { lastClickedPiece.centerOn(it) }
                            lastClickedPiece.moved = true

                        }
                    }

                }
            }
        }


    }
}

