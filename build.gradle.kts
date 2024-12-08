import org.gradle.internal.impldep.org.junit.experimental.categories.Categories.CategoryFilter.exclude

plugins {
    // this is necessary to avoid the plugins to be loaded multiple times
    // in each subproject's classloader
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.composeMultiplatform) apply false
    alias(libs.plugins.composeCompiler) apply false
    alias(libs.plugins.kotlinMultiplatform) apply false
}

val detekt by configurations.creating


dependencies{
    detekt(libs.detekt.cli)
    detekt(libs.detektFormatting)
}


val detektTask = tasks.register<JavaExec>("detekt") {
    mainClass.set("io.gitlab.arturbosch.detekt.cli.Main")
    classpath = detekt
    val input = projectDir
    val config = "config/detekt/detekt.yml"
    val exclude = "**/build/**,**/resources/**"
    val params = listOf("-i", input, "-c", config, "-ex", exclude)

    args(params)
}
