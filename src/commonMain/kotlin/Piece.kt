import com.soywiz.korge.input.onUp
import com.soywiz.korge.view.*
import com.soywiz.korim.color.Colors

class Piece(color: String, pieceX: Int, pieceY: Int) : Container() {
    private val piece = solidRect(fieldSize / 2, fieldSize / 2, Colors.RED)

    init {

        if (color == "White") {
            piece.color = Colors.WHITE
        }
        if (color == "Black") {
            piece.color = Colors.BLACK
        }
        piece.xy(pieceX * 64, pieceY * 64)
        addChild(piece)
        centerPiece(piece)
        move(piece)
    }

    fun p(px: Double, py: Double) {
        piece.xy(px * 64, py * 64)
    }
    fun px(px: Double) {
        piece.x = px * 64
    }
    fun py(py: Double) {
        piece.y = py * 64
    }



    private fun centerPiece(piece: SolidRect) {
        piece.onCollision {
            if (it.name == "Board") {
                piece.centerOn(it)
            }

        }
    }

    private fun move(piece: SolidRect) {

        piece.onUp {
            if (!pawn1clicked) {
                lastClicked = this.name!!
                pawn1clicked = true

            }
        }


    }

}
