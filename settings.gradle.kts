pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        // For PDF
        gradlePluginPortal()
        maven { url = uri("https://jitpack.io") }

    }
}

rootProject.name = "JBSB4"
include(":app")
