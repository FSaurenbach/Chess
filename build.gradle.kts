import com.soywiz.korge.gradle.*
import com.soywiz.korge.gradle.*


plugins {
    alias(libs.plugins.korge)
    java
}
korge {
	id = "de.fsrb.chess"


	targetJvm()
    targetJs()

	serializationJson()
}


dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.1")
    add("commonMainApi", project(":deps"))
}

