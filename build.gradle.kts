// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.1.1" apply false
    id("org.jetbrains.kotlin.android") version "1.8.10" apply false
    id("com.google.dagger.hilt.android") version "2.44" apply false
    id("com.google.devtools.ksp") version "1.8.10-1.0.9" apply false
}
buildscript {
    extra.apply{
        set("retrofit_version", "2.9.0")
        set("okhttp_logging_version", "4.11.0")
        set("moshi_version", "1.12.0")
        set("compose_bom_version", "2023.03.00")
        set("hilt_version", "2.44")
        set("room_version", "2.5.2")
        set("navigation_version", "2.7.3")
        set("paging_version", "3.2.1")
    }
}