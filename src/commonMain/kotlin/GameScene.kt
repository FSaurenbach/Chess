import com.soywiz.korge.scene.*
import com.soywiz.korge.view.*
import com.soywiz.korim.format.*
import com.soywiz.korio.file.std.*

class GameScene : Scene() {
    override suspend fun SContainer.sceneMain() {
        addChild(Board())
        val pieces = listOf(
            Piece("Pawn", "White", 1, 2, image(resourcesVfs["whitePawn.png"].readBitmap())).name("WhitePawn1"),
            Piece("Pawn", "White", 2, 2, image(resourcesVfs["whitePawn.png"].readBitmap())).name("WhitePawn2"),
            Piece("Pawn", "White", 3, 2, image(resourcesVfs["whitePawn.png"].readBitmap())).name("WhitePawn3"),
            Piece("Pawn", "White", 4, 2, image(resourcesVfs["whitePawn.png"].readBitmap())).name("WhitePawn4"),
            Piece("Pawn", "White", 5, 2, image(resourcesVfs["whitePawn.png"].readBitmap())).name("WhitePawn5"),
            Piece("Pawn", "White", 6, 2, image(resourcesVfs["whitePawn.png"].readBitmap())).name("WhitePawn6"),
            Piece("Pawn", "White", 7, 2, image(resourcesVfs["whitePawn.png"].readBitmap())).name("WhitePawn7"),
            Piece("Pawn", "White", 8, 2, image(resourcesVfs["whitePawn.png"].readBitmap())).name("WhitePawn8"),
            Piece("Pawn", "Black", 1, 7, image(resourcesVfs["whitePawn.png"].readBitmap())).name("BlackPawn1"),
            Piece("Pawn", "Black", 2, 7, image(resourcesVfs["whitePawn.png"].readBitmap())).name("BlackPawn2"),
            Piece("Pawn", "Black", 3, 7, image(resourcesVfs["whitePawn.png"].readBitmap())).name("BlackPawn3"),
            Piece("Pawn", "Black", 4, 7, image(resourcesVfs["whitePawn.png"].readBitmap())).name("BlackPawn4"),
            Piece("Pawn", "Black", 5, 7, image(resourcesVfs["whitePawn.png"].readBitmap())).name("BlackPawn5"),
            Piece("Pawn", "Black", 6, 7, image(resourcesVfs["whitePawn.png"].readBitmap())).name("BlackPawn6"),
            Piece("Pawn", "Black", 7, 7, image(resourcesVfs["whitePawn.png"].readBitmap())).name("BlackPawn7"),
            Piece("Pawn", "Black", 8, 7, image(resourcesVfs["whitePawn.png"].readBitmap())).name("BlackPawn8"),
            Piece("Rook", "White", 1, 1, image(resourcesVfs["apple.png"].readBitmap())).name("WhiteRook1"),
            Piece("Rook", "White", 8, 1, image(resourcesVfs["apple.png"].readBitmap())).name("WhiteRook2"),
            Piece("Rook", "Black", 1, 8, image(resourcesVfs["apple.png"].readBitmap())).name("BlackRook1"),
            Piece("Rook", "Black", 8, 8, image(resourcesVfs["apple.png"].readBitmap())).name("BlackRook2"),
            Piece("Knight", "White", 2, 1, image(resourcesVfs["apple.png"].readBitmap())).name("WhiteKnight1"),
            Piece("Knight", "White", 7, 1, image(resourcesVfs["apple.png"].readBitmap())).name("WhiteKnight2"),
            Piece("Knight", "Black", 2, 8, image(resourcesVfs["apple.png"].readBitmap())).name("BlackKnight1"),
            Piece("Knight", "Black", 7, 8, image(resourcesVfs["apple.png"].readBitmap())).name("BlackKnight2"),
            Piece("Bishop", "White", 3, 1, image(resourcesVfs["apple.png"].readBitmap())).name("WhiteBishop1"),

            )
        addChildren(pieces)
    }
}
