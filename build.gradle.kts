import org.gradle.internal.impldep.org.junit.experimental.categories.Categories.CategoryFilter.exclude

plugins {
    // this is necessary to avoid the plugins to be loaded multiple times
    // in each subproject's classloader
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.composeMultiplatform) apply false
    alias(libs.plugins.composeCompiler) apply false
    alias(libs.plugins.kotlinMultiplatform) apply false
    alias(libs.plugins.kover) apply false
}

val detekt by configurations.creating

val detektTask = tasks.register<JavaExec>("detekt") {
    mainClass.set("io.gitlab.arturbosch.detekt.cli.Main")
    classpath = detekt
    val input = projectDir
    val config = "config/detekt/detekt.yml"
    val exclude = "**/build/**,**/resources/**"
    val report = "sarif:build/reports/detekt/detekt.sarif" // used to report on github

    val autoFormatEnabled = project.hasProperty("auto-correct")

    val params = listOf(
        "-i",
        input,
        "-c",
        config,
        "-ex",
        exclude,
        "-r",
        report,
        "--build-upon-default-config"
    )

    args(params)

    if (autoFormatEnabled) {
        println("Running Detekt with auto-correct enabled...")
        args("-ac")
    } else {
        println("Running Detekt without auto-correct.")
    }
}

dependencies {
    detekt(libs.detekt.cli)
    detekt(libs.detektFormatting)
}
