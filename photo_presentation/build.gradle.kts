plugins {
    `android-library`
    `kotlin-android`
}

apply(from = "$rootDir/compose-module.gradle")

android {
    namespace = "com.eltescode.photo_presentation"
}

dependencies {
    implementation(project(Modules.core_ui))
    implementation(project(Modules.photo_domain))
    implementation(project(Modules.core_domain))

}