import com.soywiz.korge.gradle.*

plugins {
	alias(libs.plugins.korge)
}

korge {
	id = "de.fsrb.chess"


	targetAll()

	serializationJson()
}


dependencies {
    add("commonMainApi", project(":deps"))
}

