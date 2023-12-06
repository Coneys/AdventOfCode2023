plugins {
    kotlin("jvm") version "1.9.21"
    id("org.jmailen.kotlinter") version "4.1.0"

}

sourceSets {
    main {
        kotlin.srcDir("src")
    }
}

tasks {
    wrapper {
        gradleVersion = "8.2"
    }
}

dependencies{
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.0-RC")
}