import com.android.build.gradle.AppExtension
import org.codehaus.groovy.ant.GenerateStubsTask
import org.jetbrains.kotlin.config.AnalysisFlag.Flags.experimental
import org.jetbrains.kotlin.config.KotlinCompilerVersion
import org.jetbrains.kotlin.gradle.dsl.Coroutines
import org.jetbrains.kotlin.gradle.plugin.KaptExtension
import org.jetbrains.kotlin.js.translate.context.Namer.kotlin
import org.jetbrains.kotlin.kapt3.base.Kapt
import org.jetbrains.kotlin.kapt3.base.Kapt.kapt

apply(plugin = "com.android.application")
apply(plugin = "kotlin-android")
apply(plugin = "kotlin-android-extensions")
apply(plugin = "kotlin-kapt")
//apply(plugin = "com.google.gms.google-services")

configure<AppExtension> {
    compileSdkVersion(28)
    defaultConfig {
        applicationId = "com.hotsx.market"
        minSdkVersion(22)
        targetSdkVersion(28)
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }
}

dependencies {
    val implementation = "implementation"
    val testImplementation = "testImplementation"
    val androidTestImplementation = "androidTestImplementation"
    val kapt = "kapt"

    implementation(fileTree(mapOf("dir" to "libs", "include" to "*.jar")))
    implementation(kotlin("stdlib-jdk7", KotlinCompilerVersion.VERSION))
    implementation("androidx.appcompat:appcompat:1.0.0-rc02")
    implementation("androidx.constraintlayout:constraintlayout:1.1.2")
    implementation("com.google.android.material:material:1.0.0-rc01")
    testImplementation("junit:junit:4.12")
    androidTestImplementation("androidx.test:runner:1.1.0-alpha4")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.1.0-alpha4")
    implementation("com.journeyapps:zxing-android-embedded:3.6.0")
    implementation("androidx.cardview:cardview:1.0.0-rc02")
    //coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:0.23.3")
    //Architecture components
    implementation("androidx.lifecycle:lifecycle-runtime:2.0.0-rc01")
    implementation("androidx.lifecycle:lifecycle-extensions:2.0.0-rc01")
    kapt("androidx.lifecycle:lifecycle-compiler:2.0.0-rc01")
    implementation("androidx.room:room-runtime:2.0.0-rc01")
    kapt("androidx.room:room-compiler:2.0.0-rc01")
    //firebase-messaging
//    implementation("com.google.firebase:firebase-messaging:17.3.0")
}

configure<KaptExtension> {
    generateStubs = true
}

configure<org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension> {
    experimental.coroutines = Coroutines.ENABLE
}
