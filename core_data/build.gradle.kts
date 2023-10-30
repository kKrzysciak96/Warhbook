plugins {
    `android-library`
    `kotlin-android`
}

apply(from = "$rootDir/base-module.gradle")

android {
    namespace = "com.eltescode.core_data"
}

dependencies {
    api(platform(Firebase.firebaseBom))
    api(Firebase.firebaseAuth)
    api(Firebase.firebaseFirestore)
    api(Firebase.firebaseStorage)
}