plugins {
    `android-library`
    `kotlin-android`
    id("com.google.gms.google-services")

}

apply(from = "$rootDir/base-module.gradle")

android {
    namespace = "com.eltescode.auth_data"
}

dependencies {
    implementation(platform(Firebase.firebaseBom))
    implementation(project(Modules.core_data))
    implementation(project(Modules.auth_domain))

}