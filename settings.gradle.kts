
pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven("https://maven.fabric.io/public")
        maven("https://jitpack.io")
    }
}

rootProject.name = "Github-Sample-Clean-Architecture-v2"

include(":data")
include(":domain")
include(":presentation")

