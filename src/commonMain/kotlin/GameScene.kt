import com.soywiz.korge.scene.*
import com.soywiz.korge.view.*
import com.soywiz.korim.format.*
import com.soywiz.korio.file.std.*

class GameScene : Scene() {
    override suspend fun SContainer.sceneMain() {
        addChild(Board())
        val pieces = listOf(
            Piece("Pawn", "White", 1, 2, image(resourcesVfs["pawn.png"].readBitmap())).name("WhitePawn1"),
            Piece("Pawn", "White", 2, 2, image(resourcesVfs["pawn.png"].readBitmap())).name("WhitePawn2"),
            Piece("Pawn", "White", 3, 2, image(resourcesVfs["pawn.png"].readBitmap())).name("WhitePawn3"),
            Piece("Pawn", "White", 4, 2, image(resourcesVfs["pawn.png"].readBitmap())).name("WhitePawn4"),
            Piece("Pawn", "White", 5, 2, image(resourcesVfs["pawn.png"].readBitmap())).name("WhitePawn5"),
            Piece("Pawn", "White", 6, 2, image(resourcesVfs["pawn.png"].readBitmap())).name("WhitePawn6"),
            Piece("Pawn", "White", 7, 2, image(resourcesVfs["pawn.png"].readBitmap())).name("WhitePawn7"),
            Piece("Pawn", "White", 8, 2, image(resourcesVfs["pawn.png"].readBitmap())).name("WhitePawn8"),
            Piece("Pawn", "Black", 1, 7, image(resourcesVfs["pawn.png"].readBitmap())).name("BlackPawn1"),
            Piece("Pawn", "Black", 2, 7, image(resourcesVfs["pawn.png"].readBitmap())).name("BlackPawn2"),
            Piece("Pawn", "Black", 3, 7, image(resourcesVfs["pawn.png"].readBitmap())).name("BlackPawn3"),
            Piece("Pawn", "Black", 4, 7, image(resourcesVfs["pawn.png"].readBitmap())).name("BlackPawn4"),
            Piece("Pawn", "Black", 5, 7, image(resourcesVfs["pawn.png"].readBitmap())).name("BlackPawn5"),
            Piece("Pawn", "Black", 6, 7, image(resourcesVfs["pawn.png"].readBitmap())).name("BlackPawn6"),
            Piece("Pawn", "Black", 7, 7, image(resourcesVfs["pawn.png"].readBitmap())).name("BlackPawn7"),
            Piece("Pawn", "Black", 8, 7, image(resourcesVfs["pawn.png"].readBitmap())).name("BlackPawn8"),

            )
        addChildren(pieces)
    }
}
