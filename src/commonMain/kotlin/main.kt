import com.soywiz.klock.*
import com.soywiz.klogger.*
import com.soywiz.korev.*
import com.soywiz.korev.DummyEventDispatcher.addEventListener
import com.soywiz.korge.*
import com.soywiz.korge.input.*
import com.soywiz.korge.render.*
import com.soywiz.korge.scene.*
import com.soywiz.korge.tween.*
import com.soywiz.korge.ui.*
import com.soywiz.korge.view.*
import com.soywiz.korim.color.*
import com.soywiz.korim.format.*
import com.soywiz.korio.file.std.*
import com.soywiz.korma.geom.*
import com.soywiz.korma.interpolation.*
import kotlin.math.*
val sizeItem: Double = min(512.0, 512.0) / 8.0
suspend fun main() = Korge(width = 512, height = 512, bgcolor = Colors["#2b2b2b"]) {
	val sceneContainer = sceneContainer()

	sceneContainer.changeTo({ MyScene() })
}

class MyScene : Scene() {
	override suspend fun SContainer.sceneMain() {
        val board = addChild(Board())
        addChild(Piece("Pawn","White", 0, 1, board))


	}
}
class Piece(val type:String, var Color:String, var PieceX:Int, var PieceY:Int, var board: Unit) :Container() {

    init {
        val piece = solidRect(sizeItem/2, sizeItem/2, Colors.RED)
        piece.xy(x*50, y*50)
        addChild(piece)
        centerPiece(piece)
        move(piece)
    }
    private fun centerPiece(piece:SolidRect) {
        piece.onCollision() {
            if (it.name == "Board"){
                var u =5
                //piece.centerOn(it)
            }

        }
    }
    private fun move(piece:SolidRect) {
        var clicked = false
        piece.onClick {
            println(clicked)
            if (!clicked) {
                clicked = true
                println("clicked")
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
                val rect = SolidRect(sizeItem, sizeItem, if ((j + i) % 2 != 0) black else white)
                rect.xy(i * sizeItem, j * sizeItem)
                rect.name = "Board"
                addChild(rect)
                rectsBoard[i to j] = rect
            }
        for (i in 0..7) {
            text("${8 - i}", textSize = 25.0, color = if (i % 2 != 0) black else white)
                .xy(8 * sizeItem - 15.0, 1 + i * sizeItem)
                .addTo(this)
        }
        val letters = "abcdefgh"
        for (i in 0..7) {
            text(letters[i] + "", textSize = 25.0, color = if (i % 2 != 0) black else white)

                .xy(3 + i * sizeItem, 8 * sizeItem - 30.0)
                .addTo(this)
        }

    }
}
