pluginManagement {
    repositories {
        google {
            content {
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
        google()
        mavenCentral()
    }
}

rootProject.name = "FuelFit"
include(":app")
include(":core:model")
include(":core:utils")
include(":core:designsystem")
include(":core:navigation")
include(":core:database")
include(":core:network")
include(":core:ui")
include(":feature:auth:impl")
include(":feature:auth:api")
include(":feature:workoutsession:impl")
include(":feature:workoutsession:api")
include(":feature:exercise:api")
include(":feature:exercise:impl")
