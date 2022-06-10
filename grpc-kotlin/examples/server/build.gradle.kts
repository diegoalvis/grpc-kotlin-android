plugins {
    application
    kotlin("jvm")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    implementation(project(":stub"))
    runtimeOnly("io.grpc:grpc-netty:${rootProject.ext["grpcVersion"]}")

    testImplementation(kotlin("test-junit"))
    testImplementation("io.grpc:grpc-testing:${rootProject.ext["grpcVersion"]}")
}

tasks.register<JavaExec>("IceCreamServer") {
    dependsOn("classes")
    classpath = sourceSets["main"].runtimeClasspath
    mainClass.set("io.grpc.examples.icecream.IceCreamServerKt")
}
val iceCreamServerStartScripts = tasks.register<CreateStartScripts>("iceCreamServerStartScripts") {
    mainClass.set("io.grpc.examples.icecream.IceCreamServerKt")
    applicationName = "ice-cream-server"
    outputDir = tasks.named<CreateStartScripts>("startScripts").get().outputDir
    classpath = tasks.named<CreateStartScripts>("startScripts").get().classpath
}
tasks.named("startScripts") {
    dependsOn(iceCreamServerStartScripts)
}