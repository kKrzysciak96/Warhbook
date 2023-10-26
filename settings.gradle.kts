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
    }
}

rootProject.name = "Warhbook"
include(":app")
include(":auth")
include(":auth:auth_domain")
include(":auth:auth_data")
include(":auth_presentation")
include(":core_domain")
include(":core_ui")
include(":core_data")
