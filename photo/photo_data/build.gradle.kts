plugins {
    `android-library`
    `kotlin-android`

}

apply(from = "$rootDir/base-module.gradle")

android {
    namespace = "com.eltescode.photo_data"
}

dependencies {

    implementation(project(Modules.core_data))
    implementation(project(Modules.core_domain))
    implementation(project(Modules.photo_domain))
    implementation(Retrofit.retrofit)
//    implementation(Moshi.moshi)
//    "kapt"(Moshi.moshiCodegen)
    implementation(Retrofit.moshiConverter)
    implementation(Retrofit.okHttp)
    implementation(Retrofit.okHttpLoggingInterceptor)
    implementation(Paging.paging)

}