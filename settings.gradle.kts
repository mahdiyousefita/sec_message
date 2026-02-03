pluginManagement {
    repositories {
        google {
            content {
                maven { url = uri("https://maven.myket.ir") }

                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        maven { url = uri("https://maven.myket.ir") }
        google()
        mavenCentral()
    }
}

rootProject.name = "SecMessage"
include(":app")
 