import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("application")
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
}

dependencies {
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
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
