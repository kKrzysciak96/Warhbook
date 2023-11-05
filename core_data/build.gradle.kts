plugins {
    `android-library`
    `kotlin-android`
}

apply(from = "$rootDir/base-module.gradle")

android {
    namespace = "com.eltescode.core_data"
}

dependencies {
    implementation(platform(Firebase.firebaseBom))
    implementation(Firebase.firebaseAuth)
    implementation(Firebase.firebaseFirestore)
    implementation(Firebase.firebaseStorage)
}