import com.soywiz.korge.*
import com.soywiz.korge.scene.*
import com.soywiz.korge.view.*
import com.soywiz.korim.color.*

val rectsBoard = mutableMapOf<Pair<Int, Int>, SolidRect>()
var globalWith = 1024
var globalHeight = 1024
val fieldSize: Double = globalWith / 8.0
var clicked = false
var lastClicked: String = ""

suspend fun main() = Korge(width = globalWith, height = globalHeight, bgcolor = Colors["#2b2b2b"]) {
    val sceneContainer = sceneContainer()
    sceneContainer.changeTo({ GameScene() })
}
