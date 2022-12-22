import com.soywiz.korge.input.onUp
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
                if (clicked && (lastClicked != this.name!!) && (lastClickedPiece.color != this.color) ) {
                    when (lastClickedPiece.color){
                        "White" -> {
                            if ((newX == oldX +1 || newX == oldX -1) && newY == oldY + 1 && whiteTurn){
                                this.removeFromParent()
                                rectsBoard[newX to newY]?.let { lastClickedPiece.centerOn(it) }
                                lastClickedPiece.setOldPositionPair(newX to newY)
                                lastClickedPiece.moved = false
                                clicked = false
                                whiteTurn = false
                            }
                        }
                        "Black" -> {
                            if ((newX == oldX +1 || newX == oldX -1) && newY == oldY - 1 && !whiteTurn){
                                this.removeFromParent()
                                rectsBoard[newX to newY]?.let { lastClickedPiece.centerOn(it) }
                                lastClickedPiece.setOldPositionPair(newX to newY)
                                lastClickedPiece.moved = false
                                clicked = false
                                whiteTurn = true
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

                }
            }
        }


    }
}

