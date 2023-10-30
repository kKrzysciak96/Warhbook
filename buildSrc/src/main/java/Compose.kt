object Compose {
    const val composeVersion = "1.5.0"
    const val composeCompilerVersion = "1.4.5"

    const val runtime = "androidx.compose.runtime:runtime:$composeVersion"
    const val compiler = "androidx.compose.compiler:compiler:$composeCompilerVersion"

    private const val navigationVersion = "2.5.3"
    const val navigation = "androidx.navigation:navigation-compose:$navigationVersion"

    private const val hiltNavigationComposeVersion = "1.0.0-beta01"
    const val hiltNavigationCompose = "androidx.hilt:hilt-navigation-compose:$hiltNavigationComposeVersion"

    private const val activityComposeVersion = "1.8.0"
    const val activityCompose = "androidx.activity:activity-compose:$activityComposeVersion"

    private const val composeBoomVersion = "2023.03.00"
    const val composeBoom = "androidx.compose:compose-bom:$composeBoomVersion"
    const val ui = "androidx.compose.ui:ui"
    const val debugUi = "androidx.compose.ui:ui-tooling"
    const val uiGraphics = "androidx.compose.ui:ui-graphics"
    const val uiToolingPreview = "androidx.compose.ui:ui-tooling-preview"
    const val material3 = "androidx.compose.material3:material3"
    const val materialIconsExtended = "androidx.compose.material:material-icons-extended:$composeVersion"

    private const val lifecycleVersion = "2.6.0"
    const val viewModelCompose = "androidx.lifecycle:lifecycle-viewmodel-compose:$lifecycleVersion"
}
