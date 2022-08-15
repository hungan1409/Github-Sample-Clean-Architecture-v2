plugins {
    id(GradlePlugins.android)
    kotlin(GradlePlugins.kotlinAndroid)
    kotlin(GradlePlugins.kotlinApt)
    kotlin(GradlePlugins.kotlinExt)
    id(GradlePlugins.hilt)
}

apply {
    plugin(GradlePlugins.navigationSafeKotlin)
    from("../ktlint.gradle")
    from("jacoco.gradle")
    from("auto-dimensions.gradle")
}

android {
    compileSdkVersion(Android.targetSdk)
    flavorDimensions("default")

    defaultConfig {
        applicationId = Android.applicationId
        minSdkVersion(Android.minSdk)
        targetSdkVersion(Android.targetSdk)
        versionCode = Android.versionCode
        versionName = Android.versionName

        testInstrumentationRunner = AndroidJUnit.runner
    }

    buildTypes {
        getByName(BuildType.release) {
            isMinifyEnabled = BuildType.minifyRelease
            proguardFiles(BuildType.proguardRelease)
        }

        getByName(BuildType.debug) {
            isMinifyEnabled = BuildType.minifyDebug
            proguardFiles(BuildType.proguardDebug)
            isTestCoverageEnabled = true
        }
    }

    productFlavors {
        create("develop") {
            matchingFallbacks += listOf("debug", "qa")
        }

        create("production") {
            matchingFallbacks += listOf("release")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    dataBinding {
        isEnabled = true
    }

    androidExtensions {
        isExperimental = true
    }
}

dependencies {
    // ktx core
    implementation(Libs.ktx)
    implementation(Libs.stdLib)

    // support
    implementation(Libs.supportAppCompat)
    implementation(Libs.supportAnnotations)
    implementation(Libs.supportCardview)
    implementation(Libs.supportDesign)
    implementation(Libs.supportRecyclerview)
    implementation(Libs.supportRecyclerviewSelection)
    implementation(Libs.supportLegacyV4)

    // lifecycle
    implementation(Libs.lifecycleExtensions)
    implementation(Libs.lifecycleLiveDataKtx)
    implementation(Libs.lifecycleViewModel)
    implementation(Libs.lifecycleSavedState)
    implementation(Libs.fragmentKtx)

    // Constraint Layout
    implementation(Libs.constraintlayout)

    // Glide
    implementation(Libs.glideRuntime)

    // coroutines
    implementation(Libs.coroutines)
    implementation(Libs.coroutinesAndroid)

    // Binding
    kapt(Libs.metadataJvm)

    // Hilt
    implementation(Libs.hilt)
    implementation(Libs.hiltLifeCycle)
    implementation(Libs.hiltNavigation)
    kapt(Libs.hiltCompiler)
    kapt(Libs.hiltCompilerAndroidX)

    implementation(Libs.retrofitGson)
    implementation(Libs.retrofitRuntime)

    // Permission
    implementation(Libs.permission)

    // module
    implementation(project(Modules.domain)) {
        exclude(group = "com.example.github", module = "domain")
    }
    implementation(project(Modules.data)) {
        exclude(group = "com.example.github", module = "data")
    }

    // Navigation
    implementation(Libs.navigationUiKtx)
    implementation(Libs.navigationFragmentKtx)

    // logging
    implementation(Libs.timber)

    // Dependencies for local unit tests
    testImplementation(Libs.junit)
    testImplementation(Libs.mockitoAll)
    testImplementation(Libs.hamcrest)
    testImplementation(Libs.archTesting)
    testImplementation(Libs.stdLib)
    testImplementation(Libs.kotlinTest)
    testImplementation(Libs.mockitoWebServer)
    testImplementation(Libs.robolectric)

    // CircleImageView
    implementation(Libs.circleImageView)

    // LeakCanary
    debugImplementation(Libs.leakCanary)

    // Shimmer
    implementation(Libs.shimmer)
}
