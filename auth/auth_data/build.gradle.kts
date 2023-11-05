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

    implementation(platform(Firebase.firebaseBom))
    implementation(Firebase.firebaseAuth)
    implementation(Firebase.firebaseFirestore)
    implementation(Firebase.firebaseStorage)
}