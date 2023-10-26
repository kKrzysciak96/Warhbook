// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.google.gms.google-services") version "4.4.0" apply false
}
buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(Build.androidBuildTools)
        classpath(Build.kotlinGradlePlugin)
        classpath(Build.hiltAndroidGradlePlugin)

        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.0")

    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}