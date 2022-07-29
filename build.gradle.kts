import java.net.URI

buildscript {
    repositories {
        google()
        mavenCentral()
        maven {
            url = uri(Url.fabric)
        }
    }

    dependencies {
        classpath(kotlin(module = "gradle-plugin", version = Versions.kotlin))
        classpath(BuildPlugins.androidPlugin)
        classpath(BuildPlugins.navigationSafe)
        classpath(BuildPlugins.googleService)
        classpath(BuildPlugins.fabric)
        classpath(BuildPlugins.hilt)
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven {
            url = URI.create(Url.jitpack)
        }
        maven {
            url = uri(Url.fabric)
        }
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}