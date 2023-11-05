plugins {
    `android-library`
    `kotlin-android`
}

apply(from = "$rootDir/compose-module.gradle")

android {
    namespace = "com.eltescode.notes_presentation"
}

dependencies {
    implementation(project(Modules.core_ui))
    implementation(project(Modules.notes_domain))

}