import com.soywiz.korge.*
import com.soywiz.korge.input.*
import com.soywiz.korge.scene.*
import com.soywiz.korge.view.*
import com.soywiz.korim.color.*

const val fieldSize: Double = 512.0 / 8.0
suspend fun main() = Korge(width = 512, height = 512, bgcolor = Colors["#2b2b2b"]) {
	val sceneContainer = sceneContainer()

	sceneContainer.changeTo({ MyScene(sceneContainer) })
}

class MyScene(private var scontainer: Any) : Scene() {
	override suspend fun SContainer.sceneMain() {
        addChild(Board())
        //addChild(Piece("Pawn","White", 0, 1, scontainer))
        var rpiece = Piece("Pawn","White", 1, 3, scontainer).addTo(this)
        rpiece.changePosition(3, 3)
	}
}
class Piece(val type:String, var Color:String, var pieceX:Int, var pieceY:Int, var scontainer: Any) :Container() {

    init {
        val piece = solidRect(fieldSize / 2, fieldSize / 2, Colors.RED)
        piece.xy(pieceX*64, pieceY*64)
        addChild(piece)
        centerPiece(piece)
        move(piece)
    }
    fun changePosition(x:Int, y:Int) {
        piece.xy(x*64, y*64)
    }
    private fun centerPiece(piece: SolidRect) {
        piece.onCollision() {
            if (it.name == "Board") {
                piece.centerOn(it)
            }

        }
    }

    private fun move(piece: SolidRect) {

        var clicked = true
        piece.onClick {
            println(clicked)
            if (!clicked) {
                clicked = true
                println("clicked")
            }


        }



        this.onUpAnywhere {
            if (clicked) {
                piece.pos = this.mouse.currentPosLocal
                centerPiece(piece)
                println("up")
                clicked = false
            }


        }
    }

}
class Board() :Container() {

    private val white = Colors["#f0d9b5"]
    private val black = Colors["#b58863"]
    private val rectsBoard = mutableMapOf<Pair<Int, Int>, SolidRect>()

    init {

        for (i in 0..7)
            for (j in 0..7) {
                val rect = SolidRect(fieldSize, fieldSize, if ((j + i) % 2 != 0) black else white)
                rect.xy(i * fieldSize, j * fieldSize)
                rect.name = "Board"
                addChild(rect)
                rectsBoard[i to j] = rect
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
