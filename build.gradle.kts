import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("application")
	kotlin("jvm") version "1.3.50"
	id("com.github.johnrengelman.shadow") version "4.0.4"
}

application {
	mainClassName = "com.github.ghostbear.kotlinanilistbot.ApplicationKt"
}

group = "com.github.ghostbear"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	mavenCentral()
	jcenter()
	maven("https://jitpack.io")
}

dependencies {
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("net.dv8tion:JDA:4.0.0_67") {
		exclude(module = "opus-java")
	}
	implementation("com.github.kittinunf.fuel:fuel:1.3.1")
	implementation("com.github.kittinunf.fuel:fuel-jackson:2.2.0")
	implementation("com.fasterxml.jackson.core:jackson-annotations:2.9.0")
	implementation("com.taskworld.kraph:kraph:0.4.1")
	implementation("org.jsoup:jsoup:1.12.1")
}

tasks {
	named<ShadowJar>("shadowJar") {
		archiveBaseName.set("shadow")
		mergeServiceFiles()
		manifest {
			attributes(mapOf("Main-Class" to application.mainClassName))
		}
	}
}

tasks {
	build {
		dependsOn(shadowJar)
	}
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "1.8"
	}
}
