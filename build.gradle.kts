
plugins {
    id(GradlePlugins.android) version Versions.application apply false
    id(GradlePlugins.androidLib) version Versions.application apply false
    id(GradlePlugins.jetbrainsKotlinAndroid) version Versions.kotlin apply false
    id(GradlePlugins.jetbrainsKotlinJvm) version Versions.kotlin apply false
    id(GradlePlugins.navigationSafe) version Versions.navigationSafe apply false
    id(GradlePlugins.hilt) version Versions.hilt apply false
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}