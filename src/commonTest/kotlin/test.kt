import com.soywiz.korge.input.*
import com.soywiz.korge.tests.*
import com.soywiz.korge.view.*
import com.soywiz.korim.color.*
import com.soywiz.korim.format.*
import com.soywiz.korio.file.std.*
import kotlin.test.*

class Tests : ViewsForTesting() {
    @Test
    fun imageTest() = viewsTest {
        val log = arrayListOf<String>()
        val rect = solidRect(100, 100, Colors.RED)
        rect.onClick {
            log += "clicked"
        }
        image(resourcesVfs["whitePawn.png"].readBitmap())
        image(resourcesVfs["apple.png"].readBitmap())

    }
}
