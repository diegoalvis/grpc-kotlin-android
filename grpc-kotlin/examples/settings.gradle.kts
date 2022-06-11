rootProject.name = "grpc-kotlin-examples"

// when running the assemble task, ignore the android & graalvm related subprojects
if (startParameter.taskRequests.find { it.args.contains("assemble") } == null) {
    include("protos", "stub", "server", "android")
} else {
    include("protos", "stub", "server")
}

pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
    }
}
