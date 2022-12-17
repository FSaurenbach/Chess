import com.soywiz.korge.*
import com.soywiz.korge.input.*
import com.soywiz.korge.scene.*
import com.soywiz.korge.view.*
import com.soywiz.korim.color.*

val rectsBoard = mutableMapOf<Pair<Int, Int>, SolidRect>()
const val fieldSize: Double = 512.0 / 8.0
var pawn1clicked = false
var lastClicked: String = ""
suspend fun main() = Korge(width = 512, height = 512, bgcolor = Colors["#2b2b2b"]) {
    val sceneContainer = sceneContainer()

    sceneContainer.changeTo({ MyScene(sceneContainer) })
}

class MyScene(private var scontainer: Any) : Scene() {
    override suspend fun SContainer.sceneMain() {
        var board = addChild(Board())

        Piece("Pawn","White", 0, 1, scontainer).name("Pawn2").addTo(this)

        val rpiece = Piece("Pawn", "Black", 0, 0, scontainer).addTo(this)
        rpiece.name("Pawn")
        rpiece.x = 3 * fieldSize
        rpiece.y = 3 * fieldSize
        println("name:" + rpiece.name)
    }
}

class Piece(type: String, var Color: String, var pieceX: Int, var pieceY: Int, var scontainer: Any) : Container() {

    init {
        val piece = solidRect(fieldSize / 2, fieldSize / 2, Colors.RED)
        if (Color == "White") {
            piece.color = Colors.WHITE
        }
        if (Color == "Black") {
            piece.color = Colors.BLACK
        }
        piece.xy(pieceX * 64, pieceY * 64)
        addChild(piece)
        centerPiece(piece)
        move(piece)
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
            println(pawn1clicked)
            if (!pawn1clicked) {
                println("debug" + this.name)
                lastClicked = this.name!!
                pawn1clicked = true
                println(pawn1clicked)

            }
        }


    }

}

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
                    clickListener(i to j)
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

    private fun clickListener(pair: Pair<Int, Int>) {
        if (pawn1clicked) {
            println("clicked on Board")
            println("last clicked $lastClicked")
            rectsBoard[pair]?.let { stage!!.findViewByName(lastClicked)!!.centerOn(it) }
            println(stage!!.findViewByName(lastClicked))
            lastClicked = ""
            pawn1clicked = false
        }

    }
}
