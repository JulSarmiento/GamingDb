import java.io.FileInputStream
import java.util.Properties

plugins {
  alias(libs.plugins.android.application)
  alias(libs.plugins.kotlin.compose)
  alias(libs.plugins.hilt)
  alias(libs.plugins.ksp)

}

val localProperties = Properties()
val localPropertiesFile: File = rootProject.file("local.properties")
if (localPropertiesFile.exists()) {
  localProperties.load(FileInputStream(localPropertiesFile))
}

android {
  namespace = "com.julhdev.gamingdb"
  compileSdk {
    version = release(36) {
      minorApiLevel = 1
    }
  }

  defaultConfig {
    applicationId = "com.julhdev.gamingdb"
    minSdk = 34
    targetSdk = 36
    versionCode = 1
    versionName = "1.0"

    buildConfigField("String", "BASE_URL", localProperties.getProperty("BASE_URL"))
    buildConfigField("String", "API_KEY", localProperties.getProperty("API_KEY"))
    buildConfigField("String", "ENDPOINT_GAMES", localProperties.getProperty("ENDPOINT_GAMES"))

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }

  buildTypes {
    release {
      isMinifyEnabled = false
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
    }
  }
  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
  }
  buildFeatures {
    compose = true
    buildConfig = true
  }

}

dependencies {
  implementation(libs.androidx.core.ktx)
  implementation(libs.androidx.lifecycle.runtime.ktx)
  implementation(libs.androidx.activity.compose)
  implementation(platform(libs.androidx.compose.bom))
  implementation(libs.androidx.compose.ui)
  implementation(libs.androidx.compose.ui.graphics)
  implementation(libs.androidx.compose.ui.tooling.preview)
  implementation(libs.androidx.compose.material3)

  // Hilt
  implementation(libs.hilt)
  ksp(libs.hilt.compiler)

  implementation(libs.androidx.room.ktx)

  // Navigation
  implementation(libs.androidx.navigation.compose)

  // SharedPreferences
  implementation(libs.androidx.datastore.preferences)

  // RetroFit
  implementation(libs.retrofit)
  implementation(libs.retrofit.converter.gson)

  // Interceptor
  implementation(libs.logging.interceptor)

  // Coil
  implementation(libs.coil.compose)


  testImplementation(libs.junit)
  androidTestImplementation(libs.androidx.junit)
  androidTestImplementation(libs.androidx.espresso.core)
  androidTestImplementation(platform(libs.androidx.compose.bom))
  androidTestImplementation(libs.androidx.compose.ui.test.junit4)
  debugImplementation(libs.androidx.compose.ui.tooling)
  debugImplementation(libs.androidx.compose.ui.test.manifest)
}