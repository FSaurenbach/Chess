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
    add("commonMainApi", project(":deps"))
}

