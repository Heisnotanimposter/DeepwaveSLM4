buildscript {
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://chaquo.com/maven") }
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.0.4")
        classpath("com.chaquo.python:gradle:9.1.0")
        classpath(kotlin("gradle-plugin", version = "1.5.31"))
    }
}
plugins {
    id("com.android.application") version "7.0.4" apply false
    id("org.jetbrains.kotlin.android") version "1.6.10" apply false
    id("com.chaquo.python") version "10.0.0" apply false

}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://chaquo.com/maven") }
    }
}