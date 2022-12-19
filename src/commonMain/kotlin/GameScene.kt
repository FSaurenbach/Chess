import com.soywiz.korge.scene.Scene
import com.soywiz.korge.view.SContainer
import com.soywiz.korge.view.name

class GameScene : Scene() {
    override suspend fun SContainer.sceneMain() {
        var board = addChild(Board())
        var pieces = listOf(
            Piece("White", 1, 2).name("WhitePawn1"),
            Piece("White", 2, 2).name("WhitePawn2"),
            Piece("White", 3, 2).name("WhitePawn3"),
            Piece("White", 4, 2).name("WhitePawn4"),
            Piece("White", 5, 2).name("WhitePawn5"),
            Piece("White", 6, 2).name("WhitePawn6"),
            Piece("White", 7, 2).name("WhitePawn7"),
            Piece("White", 8, 2).name("WhitePawn8"),




        )
        addChildren(pieces)
    }
}
