// Tệp build cấp độ cao nhất nơi bạn có thể thêm cấu hình chung cho tất cả các mô-đun/dự án con.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    id("com.google.gms.google-services") version "4.4.2" apply false
}

buildscript {
    repositories {
        google() // Kho Maven của Google
        mavenCentral() // Kho Maven Central
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.4.2") // Thay thế bằng phiên bản Gradle plugin Android hiện tại của bạn
        classpath("com.google.gms:google-services:4.4.2") // Thêm plugin Google Services
    }
}

// Tạo task để xóa các thư mục build
tasks.register<Delete>("clean") {
    delete(layout.buildDirectory) // Sử dụng layout.buildDirectory
}
