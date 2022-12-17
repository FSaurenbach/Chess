import com.soywiz.korge.gradle.*

plugins {
	alias(libs.plugins.korge)
}

korge {
	id = "de.fsrb.chess"


	targetAll()
	
	targetJvm()
	targetJs()

	serializationJson()
}


dependencies {
    add("commonMainApi", project(":deps"))
    //add("commonMainApi", project(":korge-dragonbones"))
}

