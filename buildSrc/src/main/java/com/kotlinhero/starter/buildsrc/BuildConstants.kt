package com.kotlinhero.starter.buildsrc

import org.gradle.api.JavaVersion

object BuildConstants {
    const val APPLICATION_ID = "com.kotlinhero.starter"
    const val COMPILE_SDK_VERSION = 35
    const val MIN_SDK_VERSION = 24
    const val TARGET_SDK_VERSION = 35
    const val VERSION_CODE = 1
    const val VERSION_NAME = "1.0"
    val JAVA_VERSION = JavaVersion.VERSION_1_8
    const val JVM_TARGET = "1.8"
}