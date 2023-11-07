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
include(":user")
include(":user:user_data")
include(":user:user_domain")
include(":user_presentation")
include(":photo")
include(":photo:photo_domain")
include(":photo:photo_data")
include(":photo_presentation")
include(":book")
include(":book:book_data")
include(":book:book_domain")
include(":book:book_presentation")
include(":notes")
include(":notes:notes_data")
include(":notes:notes_domain")
include(":notes_presentation")
