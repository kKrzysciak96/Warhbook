plugins {
    `android-library`
    `kotlin-android`
}

apply(from = "$rootDir/compose-module.gradle")

android {
    namespace = "com.eltescode.book_presentation"
}

dependencies {
    implementation(project(Modules.core_ui))
    implementation(project(Modules.book_domain))
    implementation(project(Modules.core_domain))

}