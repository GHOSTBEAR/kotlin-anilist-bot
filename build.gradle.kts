import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("application")
	kotlin("kapt") version "1.3.50"
	kotlin("jvm") version "1.3.50"
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

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "1.8"
	}
}

tasks.withType<Jar>() {
	configurations["compileClasspath"].forEach { file: File ->
		from(zipTree(file.absoluteFile))
	}
}
