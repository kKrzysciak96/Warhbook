plugins {
    `android-library`
    `kotlin-android`

}

apply(from = "$rootDir/base-module.gradle")

android {
    namespace = "com.eltescode.auth_data"
}

dependencies {

    implementation(project(Modules.core_data))
    implementation(project(Modules.core_domain))
    implementation(project(Modules.auth_domain))
}