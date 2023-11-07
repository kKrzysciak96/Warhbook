plugins {
    `android-library`
    `kotlin-android`

}

apply(from = "$rootDir/base-module.gradle")

android {
    namespace = "com.eltescode.notes_data"
}

dependencies {

    implementation(project(Modules.core_data))
    implementation(project(Modules.notes_domain))

    implementation(Room.roomRuntime)
    implementation(Room.roomKtx)
    "kapt"(Room.roomCompiler)

    implementation(platform(Firebase.firebaseBom))
    implementation(Firebase.firebaseAuth)
    implementation(Firebase.firebaseFirestore)
    implementation(Firebase.firebaseStorage)

    implementation(DataStore.dataStore)
}