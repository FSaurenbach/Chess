import com.soywiz.korge.gradle.*

plugins {
    java
    id("com.soywiz.korge") version "3.4.0"
}
repositories {
    mavenCentral()
}
korge {
	id = "de.fsrb.chess"


    targetAll()

	serializationJson()
}
