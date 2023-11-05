plugins {
    `android-library`
    `kotlin-android`

}

apply(from = "$rootDir/base-module.gradle")

android {
    namespace = "com.eltescode.notes_domain"
}

dependencies {
    implementation(project(Modules.core_domain))
}