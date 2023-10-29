// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(Build.androidBuildTools)
        classpath(Build.kotlinGradlePlugin)
        classpath(Build.hiltAndroidGradlePlugin)
        classpath("com.google.gms:google-services:4.4.0")

    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}