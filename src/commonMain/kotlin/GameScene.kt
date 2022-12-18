import com.soywiz.korge.scene.Scene
import com.soywiz.korge.view.SContainer
import com.soywiz.korge.view.addTo
import com.soywiz.korge.view.name

class GameScene : Scene() {
    override suspend fun SContainer.sceneMain() {
        var board = addChild(Board())

        Piece("White", 0, 1).name("Pawn2").addTo(this)

        val rpiece = Piece("Black", 0, 0).addTo(this)
        rpiece.name("Pawn")

        rpiece.p(5.0, 3.0)
    }
}
