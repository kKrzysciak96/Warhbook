plugins {
    `android-library`
    `kotlin-android`

}

apply(from = "$rootDir/base-module.gradle")

android {
    namespace = "com.eltescode.user_data"
}

dependencies {

    implementation(project(Modules.core_data))
    implementation(project(Modules.core_domain))
    implementation(project(Modules.user_domain))
}