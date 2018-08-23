import java.net.URI

buildscript {
    val kotlinVersion = "1.2.61"
    repositories {
        google()
        jcenter()

    }
    dependencies {
        classpath("com.android.tools.build:gradle:3.3.0-alpha06")
        classpath(kotlin("gradle-plugin", version = kotlinVersion))
        classpath("com.google.gms:google-services:4.0.1")
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        maven(url = "https://maven.google.com")
    }
}

task<Delete>("clean") {
    delete(rootProject.buildDir)
}
