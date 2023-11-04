plugins {
    `android-library`
    `kotlin-android`

}

apply(from = "$rootDir/base-module.gradle")

android {
    namespace = "com.eltescode.book_data"
}

dependencies {
    implementation(project(Modules.core_data))
    implementation(project(Modules.book_domain))
}