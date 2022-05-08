plugins {
    id(GradlePlugins.javaLib)
    id(GradlePlugins.kotlin)
}

apply {
    from("../ktlint.gradle")
}

dependencies {
    // kotlin core
    implementation(Libs.stdLib)
    implementation(Libs.ktx)

    // rx
    implementation(Libs.rxJava)
    implementation(Libs.rxAndroid)

    // Hilt
    implementation(Libs.hiltCore)

    // test
    testImplementation(Libs.junit)
    testImplementation(Libs.mockitoCore)
    testImplementation(Libs.hamcrest)
    testImplementation(Libs.stdLib)
    testImplementation(Libs.kotlinTest)
}
