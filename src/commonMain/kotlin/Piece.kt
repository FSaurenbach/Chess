import com.soywiz.korge.input.onUp
import com.soywiz.korge.view.*
import kotlin.math.*

class Piece(var type: String, var color: String, pieceX: Int, pieceY: Int, image: Image) : Container() {
    private var piece = image
    private var oldPositionPair: Pair<Int, Int>? = null
    var moved = true

    var pawnFirstMove = false

    init {
        if (type == "Pawn") {
            pawnFirstMove = true
        }
        piece.scaledWidth = fieldSize / 1.000001
        piece.scaledHeight = fieldSize / 1.000001
        piece.xy(pieceX * globalWith / 8, pieceY * globalHeight / 8)
        addChild(piece)
        centerPiece(piece)
        move(piece)
    }

    fun setOldPositionPair(pair: Pair<Int, Int>) {
        oldPositionPair = pair
    }

    fun getOldPositionPair(): Pair<Int, Int>? {
        return oldPositionPair
    }


    private fun centerPiece(piece: Image) {
        piece.onCollision {
            if (it.name == "Board") {
                piece.centerOn(it)
            }

        }
    }

    private fun move(piece: Image) {

        piece.onUp {
            val lastClickedPiece: Piece? = (stage?.findViewByName(lastClicked) as Piece?)
            if (!clicked) {
                lastClicked = this.name!!
                clicked = true

            }
            if (lastClickedPiece != null) {
                val newX = this.getOldPositionPair()?.first
                val newY = this.getOldPositionPair()?.second
                val oldX = lastClickedPiece.getOldPositionPair()!!.first
                val oldY = lastClickedPiece.getOldPositionPair()!!.second
                if (clicked && (lastClicked != this.name!!) && (lastClickedPiece.color != this.color) ) {
                    when (lastClickedPiece.color){
                        "White" -> {
                            when (lastClickedPiece.type){
                                "Pawn"-> {
                                    if ((newX == oldX +1 || newX == oldX -1) && newY == oldY + 1 && whiteTurn){
                                        this.removeFromParent()
                                        rectsBoard[newX to newY]?.let { lastClickedPiece.centerOn(it) }
                                        lastClickedPiece.setOldPositionPair(newX to newY)
                                        lastClickedPiece.moved = false
                                        clicked = false
                                        whiteTurn = false
                                    }
                                    else{
                                        clicked = false
                                        lastClicked = ""
                                    }
                                }

                                "Rook" -> {
                                    var canMove = true
                                    if (newX == oldX) {
                                        if (newY!! > oldY) {
                                            for (i in oldY + 1 until newY) {
                                                if (rectsBoard[newX to i]?.name != null) {
                                                    canMove = false
                                                }
                                            }
                                        } else {
                                            for (i in newY + 1 until oldY) {
                                                if (rectsBoard[newX to i]?.name != null) {
                                                    canMove = false
                                                }
                                            }
                                        }
                                    } else if (newY == oldY) {
                                        if (newX!! > oldX) {
                                            for (i in oldX + 1 until newX) {
                                                if (rectsBoard[i to newY]?.name != null) {
                                                    canMove = false
                                                }
                                            }
                                        } else {
                                            for (i in newX + 1 until oldX) {
                                                if (rectsBoard[i to newY]?.name != null) {
                                                    canMove = false
                                                }
                                            }
                                        }
                                    } else {
                                        canMove = false
                                    }

                                    if (newY == oldY && newX != oldX && whiteTurn && canMove) {
                                        this.removeFromParent()
                                        lastClickedPiece.setOldPositionPair(newX!! to newY)
                                        rectsBoard[newX to newY]?.let { lastClickedPiece.centerOn(it) }
                                        lastClickedPiece.moved = true
                                        whiteTurn = false
                                    }
                                    else if (newX == oldX && newY != oldY && whiteTurn){
                                        this.removeFromParent()
                                        lastClickedPiece.setOldPositionPair(newX to newY!!)
                                        rectsBoard[newX to newY]?.let { lastClickedPiece.centerOn(it) }
                                        lastClickedPiece.moved = true
                                        whiteTurn = false
                                    }
                                }
                                "Knight"-> {
                                    if ((newX == oldX + 1 || newX == oldX - 1) && (newY == oldY + 2 || newY == oldY - 2) && whiteTurn){
                                        this.removeFromParent()
                                        lastClickedPiece.setOldPositionPair(newX to newY)
                                        rectsBoard[newX to newY]?.let { lastClickedPiece.centerOn(it) }
                                        lastClickedPiece.moved = true
                                        whiteTurn = false
                                    }
                                    else if ((newX == oldX + 2 || newX == oldX - 2) && (newY == oldY + 1 || newY == oldY - 1) && whiteTurn){
                                        this.removeFromParent()
                                        lastClickedPiece.setOldPositionPair(newX to newY)
                                        rectsBoard[newX to newY]?.let { lastClickedPiece.centerOn(it) }
                                        lastClickedPiece.moved = true
                                        whiteTurn = false
                                    }
                                }

                                "Bishop" -> {
                                    var canMove = true
                                    if (abs(newX!! - oldX) == abs(newY!! - oldY)) {
                                        // Bishop is moving in one of the four diagonal directions
                                        if (newX > oldX && newY > oldY) {
                                            // Bishop is moving in the top-right direction
                                            for (i in 2 until abs(newX - oldX) + 1) {
                                                if (rectsBoard[oldX + i to oldY + i]?.name != null) {
                                                    canMove = false
                                                }
                                            }
                                        } else if (newX > oldX && newY < oldY) {
                                            // Bishop is moving in the bottom-right direction
                                            for (i in 2 until abs(newX - oldX) + 1) {
                                                if (rectsBoard[oldX + i to oldY - i]?.name != null) {
                                                    canMove = false
                                                }
                                            }
                                        } else if (newX < oldX && newY > oldY) {
                                            // Bishop is moving in the top-left direction
                                            for (i in 2 until abs(newX - oldX) + 1) {
                                                if (rectsBoard[oldX - i to oldY + i]?.name != null) {
                                                    canMove = false
                                                }
                                            }
                                        } else if (newX < oldX && newY < oldY) {
                                            // Bishop is moving in the bottom-left direction
                                            for (i in 2 until abs(newX - oldX) + 1) {
                                                if (rectsBoard[oldX - i to oldY - i]?.name != null) {
                                                    canMove = false
                                                }
                                            }
                                        }
                                    } else {
                                        // Bishop is not moving diagonally
                                        canMove = false
                                    }

                                    if (abs(newX - oldX) == abs(newY - oldY) && whiteTurn && canMove) {
                                        this.removeFromParent()
                                        lastClickedPiece.setOldPositionPair(newX to newY)
                                        rectsBoard[newX to newY]?.let { lastClickedPiece.centerOn(it) }
                                        lastClickedPiece.moved = true
                                        whiteTurn = false
                                    }


                                }

                            }
                        }
                        "Black" -> {
                            when (lastClickedPiece.type){
                                "Pawn"-> {
                                    if ((newX == oldX +1 || newX == oldX -1) && newY == oldY - 1 && !whiteTurn){
                                        this.removeFromParent()
                                        rectsBoard[newX to newY]?.let { lastClickedPiece.centerOn(it) }
                                        lastClickedPiece.setOldPositionPair(newX to newY)
                                        lastClickedPiece.moved = false
                                        clicked = false
                                        whiteTurn = true
                                    }
                                }

                                "Rook" -> {
                                    var canMove = true
                                    if (newX == oldX) {
                                        if (newY!! > oldY) {
                                            for (i in oldY + 1 until newY) {
                                                if (rectsBoard[newX to i]?.name != null) {
                                                    canMove = false
                                                }
                                            }
                                        } else {
                                            for (i in newY + 1 until oldY) {
                                                if (rectsBoard[newX to i]?.name != null) {
                                                    canMove = false
                                                }
                                            }
                                        }
                                    } else if (newY == oldY) {
                                        if (newX!! > oldX) {
                                            for (i in oldX + 1 until newX) {
                                                if (rectsBoard[i to newY]?.name != null) {
                                                    canMove = false
                                                }
                                            }
                                        } else {
                                            for (i in newX + 1 until oldX) {
                                                if (rectsBoard[i to newY]?.name != null) {
                                                    canMove = false
                                                }
                                            }
                                        }
                                    } else {
                                        canMove = false
                                    }
                                    if (newY == oldY && newX != oldX && !whiteTurn && canMove) {

                                        this.removeFromParent()
                                        lastClickedPiece.setOldPositionPair(newX!! to newY)
                                        rectsBoard[newX to newY]?.let { lastClickedPiece.centerOn(it) }
                                        lastClickedPiece.moved = true
                                        whiteTurn = true
                                    }
                                    else if (newX == oldX && newY != oldY && !whiteTurn){
                                        this.removeFromParent()
                                        lastClickedPiece.setOldPositionPair(newX to newY!!)
                                        rectsBoard[newX to newY]?.let { lastClickedPiece.centerOn(it) }
                                        lastClickedPiece.moved = true
                                        whiteTurn = true
                                    }
                                }
                                "Knight"-> {
                                    if ((newX == oldX + 1 || newX == oldX - 1) && (newY == oldY + 2 || newY == oldY - 2) && !whiteTurn){
                                        this.removeFromParent()
                                        lastClickedPiece.setOldPositionPair(newX to newY)
                                        rectsBoard[newX to newY]?.let { lastClickedPiece.centerOn(it) }
                                        lastClickedPiece.moved = true
                                        whiteTurn = true
                                    }
                                    else if ((newX == oldX + 2 || newX == oldX - 2) && (newY == oldY + 1 || newY == oldY - 1) && !whiteTurn){
                                        this.removeFromParent()
                                        lastClickedPiece.setOldPositionPair(newX to newY)
                                        rectsBoard[newX to newY]?.let { lastClickedPiece.centerOn(it) }
                                        lastClickedPiece.moved = true
                                        whiteTurn = true
                                    }
                                }

                                "Bishop" -> {
                                    var canMove = true
                                    if (abs(newX!! - oldX) == abs(newY!! - oldY)) {
                                        // Bishop is moving diagonally
                                        val dx = newX - oldX
                                        val dy = newY - oldY
                                        var row = oldY
                                        var col = oldX
                                        while (row != newY && col != newX) {
                                            row += if (dy > 0) 1 else -1
                                            col += if (dx > 0) 1 else -1
                                            val square = rectsBoard[col to row]
                                            square!!.onCollision {
                                                if (it.name != lastClickedPiece.name) {
                                                    if (it is Piece) {
                                                        canMove = false
                                                    }
                                                }
                                            }
                                        }
                                    } else {
                                        canMove = false
                                    }

                                    if (canMove) {
                                        this.removeFromParent()
                                        lastClickedPiece.setOldPositionPair(newX to newY)
                                        rectsBoard[newX to newY]?.let { lastClickedPiece.centerOn(it) }
                                        lastClickedPiece.moved = true
                                        whiteTurn = !whiteTurn
                                    }
                                }

                            }

                        }
                    }

                    println("About to take piece: ${this.name} from $lastClicked")
                    clicked = false
                    lastClicked = ""
                }
            }
            
        }


    }

}

fun Board.move(pair: Pair<Int, Int>) {
    if (clicked) {
        val lastClickedPiece: Piece = (stage!!.findViewByName(lastClicked) as Piece?)!!
        val newX = pair.first
        val newY = pair.second
        val oldX = lastClickedPiece.getOldPositionPair()!!.first
        val oldY = lastClickedPiece.getOldPositionPair()!!.second
        println("oldX: $oldX, oldY: $oldY, newX: $newX, newY: $newY")
        clicked = false
        when (lastClickedPiece.color) {
            "White" -> {
                when (lastClickedPiece.type) {
                    "Pawn" -> {
                        if (newX == oldX && newY == oldY + 1 && whiteTurn) {
                            lastClickedPiece.pawnFirstMove = false
                            lastClickedPiece.setOldPositionPair(newX to newY)
                            rectsBoard[pair]?.let { lastClickedPiece.centerOn(it) }
                            lastClickedPiece.moved = true
                            whiteTurn = false
                        } else if (newX == oldX && newY == oldY + 2 && lastClickedPiece.pawnFirstMove && whiteTurn) {
                            lastClickedPiece.pawnFirstMove = false

                            lastClickedPiece.setOldPositionPair(newX to newY)
                            rectsBoard[pair]?.let { lastClickedPiece.centerOn(it) }
                            lastClickedPiece.moved = true
                            whiteTurn = false

                        }
                    }
                    "Rook"->{
                        if (newY == oldY && newX != oldX && whiteTurn){
                            lastClickedPiece.setOldPositionPair(newX to newY)
                            rectsBoard[pair]?.let { lastClickedPiece.centerOn(it) }
                            lastClickedPiece.moved = true
                            whiteTurn = false
                        }
                        else if (newX == oldX && newY != oldY && whiteTurn){
                            lastClickedPiece.setOldPositionPair(newX to newY)
                            rectsBoard[pair]?.let { lastClickedPiece.centerOn(it) }
                            lastClickedPiece.moved = true
                            whiteTurn = false
                        }
                    }
                    "Knight"->{
                        if ((newX == oldX + 1 || newX == oldX - 1) && (newY == oldY + 2 || newY == oldY - 2) && whiteTurn){
                            lastClickedPiece.setOldPositionPair(newX to newY)
                            rectsBoard[pair]?.let { lastClickedPiece.centerOn(it) }
                            lastClickedPiece.moved = true
                            whiteTurn = false
                        }
                        else if ((newX == oldX + 2 || newX == oldX - 2) && (newY == oldY + 1 || newY == oldY - 1) && whiteTurn){
                            lastClickedPiece.setOldPositionPair(newX to newY)
                            rectsBoard[pair]?.let { lastClickedPiece.centerOn(it) }
                            lastClickedPiece.moved = true
                            whiteTurn = false
                        }
                    }
                    "Bishop"->{
                        if (newX - oldX == newY - oldY && whiteTurn){
                            lastClickedPiece.setOldPositionPair(newX to newY)
                            rectsBoard[pair]?.let { lastClickedPiece.centerOn(it) }
                            lastClickedPiece.moved = true
                            whiteTurn = false
                        }
                        else if (newX - oldX == oldY - newY && whiteTurn){
                            lastClickedPiece.setOldPositionPair(newX to newY)
                            rectsBoard[pair]?.let { lastClickedPiece.centerOn(it) }
                            lastClickedPiece.moved = true
                            whiteTurn = false
                        }
                    }

                    "Bishop" -> {
                        var canMove = true
                        val dx = newX - oldX
                        val dy = newY - oldY
                        if (abs(dx) == abs(dy)) {
                            var row = oldY
                            var col = oldX
                            while (row != newY && col != newX) {
                                row += if (dy > 0) 1 else -1
                                col += if (dx > 0) 1 else -1
                                val square = rectsBoard[col to row]
                                square!!.onCollision {
                                    if (it.name != lastClickedPiece.name) {
                                        if (it is Piece) {
                                            canMove = false
                                        }
                                    }
                                }
                            }
                        } else {
                            // The bishop is not moving diagonally
                            canMove = false
                        }

                        if (canMove) {
                            lastClickedPiece.setOldPositionPair(newX to newY)
                            rectsBoard[pair]?.let { lastClickedPiece.centerOn(it) }
                            lastClickedPiece.moved = true
                            whiteTurn = !whiteTurn
                        }
                    }


                }
            }
            "Black" -> {
                when (lastClickedPiece.type) {
                    "Pawn" -> {
                        if (newX == oldX && newY == oldY - 1 && !whiteTurn) {
                            lastClickedPiece.pawnFirstMove = false
                            lastClickedPiece.setOldPositionPair(newX to newY)
                            rectsBoard[pair]?.let { lastClickedPiece.centerOn(it) }
                            lastClickedPiece.moved = true
                            whiteTurn = true
                        } else if (newX == oldX && newY == oldY - 2 && lastClickedPiece.pawnFirstMove && !whiteTurn) {
                            lastClickedPiece.pawnFirstMove = false

                            lastClickedPiece.setOldPositionPair(newX to newY)
                            rectsBoard[pair]?.let { lastClickedPiece.centerOn(it) }
                            lastClickedPiece.moved = true
                            whiteTurn = true

                        }
                    }
                    "Rook"->{
                        if (newY == oldY && newX != oldX && !whiteTurn){
                            lastClickedPiece.setOldPositionPair(newX to newY)
                            rectsBoard[pair]?.let { lastClickedPiece.centerOn(it) }
                            lastClickedPiece.moved = true
                            whiteTurn = true
                        }
                        else if (newX == oldX && newY != oldY && !whiteTurn){
                            lastClickedPiece.setOldPositionPair(newX to newY)
                            rectsBoard[pair]?.let { lastClickedPiece.centerOn(it) }
                            lastClickedPiece.moved = true
                            whiteTurn = true
                        }
                    }
                    "Knight"->{
                        if ((newX == oldX + 1 || newX == oldX - 1) && (newY == oldY + 2 || newY == oldY - 2) && !whiteTurn){
                            lastClickedPiece.setOldPositionPair(newX to newY)
                            rectsBoard[pair]?.let { lastClickedPiece.centerOn(it) }
                            lastClickedPiece.moved = true
                            whiteTurn = true
                        }
                        else if ((newX == oldX + 2 || newX == oldX - 2) && (newY == oldY + 1 || newY == oldY - 1) && !whiteTurn){
                            lastClickedPiece.setOldPositionPair(newX to newY)
                            rectsBoard[pair]?.let { lastClickedPiece.centerOn(it) }
                            lastClickedPiece.moved = true
                            whiteTurn = true
                        }
                    }
                    "Bishop"->{
                        if (newX - oldX == newY - oldY && !whiteTurn){
                            lastClickedPiece.setOldPositionPair(newX to newY)
                            rectsBoard[pair]?.let { lastClickedPiece.centerOn(it) }
                            lastClickedPiece.moved = true
                            whiteTurn = true
                        }
                        else if (newX - oldX == oldY - newY && !whiteTurn){
                            lastClickedPiece.setOldPositionPair(newX to newY)
                            rectsBoard[pair]?.let { lastClickedPiece.centerOn(it) }
                            lastClickedPiece.moved = true
                            whiteTurn = true
                        }
                    }

                    "Bishop" -> {
                        var canMove = true
                        val dx = newX - oldX
                        val dy = newY - oldY
                        if (abs(dx) == abs(dy)) {
                            var row = oldY
                            var col = oldX
                            while (row != newY && col != newX) {
                                row += if (dy > 0) 1 else -1
                                col += if (dx > 0) 1 else -1
                                val square = rectsBoard[col to row]
                                square!!.onCollision {
                                    if (it.name != lastClickedPiece.name) {
                                        if (it is Piece) {
                                            canMove = false
                                        }
                                    }
                                }
                            }
                        } else {
                            // The bishop is not moving diagonally
                            canMove = false
                        }

                        if (canMove) {
                            lastClickedPiece.setOldPositionPair(newX to newY)
                            rectsBoard[pair]?.let { lastClickedPiece.centerOn(it) }
                            lastClickedPiece.moved = true
                            whiteTurn = !whiteTurn
                        }
                    }
                }
            }
        }


    }
}

