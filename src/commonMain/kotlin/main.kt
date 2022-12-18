import com.soywiz.korge.*
import com.soywiz.korge.scene.*
import com.soywiz.korge.view.*
import com.soywiz.korim.color.*

val rectsBoard = mutableMapOf<Pair<Int, Int>, SolidRect>()
const val fieldSize: Double = 512.0 / 8.0
var pawn1clicked = false
var lastClicked: String = ""
suspend fun main() = Korge(width = 512, height = 512, bgcolor = Colors["#2b2b2b"]) {
    val sceneContainer = sceneContainer()

    sceneContainer.changeTo({ GameScene() })
}

