import com.soywiz.korge.scene.*
import com.soywiz.korge.view.*
import com.soywiz.korim.format.*
import com.soywiz.korio.file.std.*

class GameScene : Scene() {
    override suspend fun SContainer.sceneMain() {
        addChild(Board())
        var pieces = listOf(
            Piece("Pawn","White", 1, 2, image(resourcesVfs["pawn.png"].readBitmap())).name("WhitePawn1"),
            Piece("Pawn", "White", 2, 2, image(resourcesVfs["pawn.png"].readBitmap())).name("WhitePawn2"),
            Piece("Pawn", "White", 3, 2, image(resourcesVfs["pawn.png"].readBitmap())).name("WhitePawn3"),
            Piece("Pawn", "White", 4, 2, image(resourcesVfs["pawn.png"].readBitmap())).name("WhitePawn4"),
            Piece("Pawn", "White", 5, 2, image(resourcesVfs["pawn.png"].readBitmap())).name("WhitePawn5"),
            Piece("Pawn", "White", 6, 2, image(resourcesVfs["pawn.png"].readBitmap())).name("WhitePawn6"),
            Piece("Pawn", "White", 7, 2, image(resourcesVfs["pawn.png"].readBitmap())).name("WhitePawn7"),
            Piece("Pawn", "White", 8, 2, image(resourcesVfs["pawn.png"].readBitmap())).name("WhitePawn8"),


            )
        addChildren(pieces)
    }
}
