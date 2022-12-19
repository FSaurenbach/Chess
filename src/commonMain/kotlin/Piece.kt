import com.soywiz.korge.input.*
import com.soywiz.korge.view.*
import com.soywiz.korim.color.*

class Piece(color: String, pieceX: Int, pieceY: Int) : Container() {
    private val piece = solidRect(fieldSize / 2, fieldSize / 2, Colors.RED)
    private var oldPositionPair: Pair<Int, Int>? = null

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
    fun setOldPositionPair(pair: Pair<Int, Int>) {
        oldPositionPair = pair
    }
    fun getOldPositionPair(): Pair<Int, Int>? {
        return oldPositionPair
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
            if (!clicked) {
                lastClicked = this.name!!
                clicked = true

            }
        }


    }

}

fun Board.checkMove(pair: Pair<Int, Int>) {
    if (clicked) {
        var lastClickedPiece:View = stage!!.findViewByName(lastClicked)!!
        var newx = pair.first
        var newy = pair.second
        var oldx = (lastClickedPiece.x / 64).toInt()
        var oldy = (lastClickedPiece.y / 64).toInt()
        println("oldx: $oldx oldy: $oldy newx: $newx newy: $newy")
//        if (newy - oldy == 2 && newx - oldx == 0 ) {
//            rectsBoard[pair]?.let { lclicked.centerOn(it) }
//            lastClicked = ""
//            clicked = false
//        }
        if (newy in 0..7 && newy > oldy && newx - oldx == 0 && lastClickedPiece.name!!.contains("Pawn", true)) {
            rectsBoard[pair]?.let { lastClickedPiece.centerOn(it) }
            lastClicked = ""
            clicked = false
        }
    }
}
