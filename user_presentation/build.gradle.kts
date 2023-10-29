plugins {
    `android-library`
    `kotlin-android`
}

apply(from = "$rootDir/compose-module.gradle")

android {
    namespace = "com.eltescode.auth_presentation"
}

dependencies {
    implementation(project(Modules.core_ui))
    implementation(project(Modules.user_domain))
    implementation(project(Modules.core_domain))
}