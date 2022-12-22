import com.soywiz.korge.input.*
import com.soywiz.korge.view.*
import com.soywiz.korim.color.*

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


}
