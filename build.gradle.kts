// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
}

buildscript {
    // Ensure Gradle plugin classpath uses a compatible JavaPoet
    configurations.classpath {
        resolutionStrategy {
            force("com.squareup:javapoet:1.13.0")
        }
    }
    dependencies {
        // Inject newer JavaPoet onto the buildscript classpath to satisfy Hilt plugin tasks
        classpath("com.squareup:javapoet:1.13.0")
    }
}