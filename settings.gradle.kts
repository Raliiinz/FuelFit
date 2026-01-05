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
include(":core:utils")
include(":core:designsystem")
include(":core:database")
include(":core:network")
include(":feature:auth:impl")
include(":feature:auth:api")
include(":feature:exercise:api")
include(":feature:exercise:impl")
include(":feature:routine:impl")
include(":feature:routine:api")
include(":core:model")
include(":feature:profile:impl")
include(":feature:profile:api")
