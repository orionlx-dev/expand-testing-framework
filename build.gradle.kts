plugins {
    id("java")
}

group = "org.fojin.tech"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // Source: https://mvnrepository.com/artifact/org.aeonbits.owner/owner
    implementation("org.aeonbits.owner:owner:1.0.12")
    // Source: https://mvnrepository.com/artifact/net.datafaker/datafaker
    implementation("net.datafaker:datafaker:2.7.0")
    // Source: https://mvnrepository.com/artifact/tools.jackson.core/jackson-databind
    implementation("tools.jackson.core:jackson-databind:3.2.2")
    // Source: https://mvnrepository.com/artifact/org.mapstruct/mapstruct
    implementation("org.mapstruct:mapstruct:1.6.3")
    // Source: https://mvnrepository.com/artifact/org.projectlombok/lombok-mapstruct-binding
    implementation("org.projectlombok:lombok-mapstruct-binding:0.2.0")

    testCompileOnly("org.projectlombok:lombok:1.18.46")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.46")
    testAnnotationProcessor("org.mapstruct:mapstruct-processor:1.6.3")
    testImplementation(platform("org.junit:junit-bom:6.0.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.junit.jupiter:junit-jupiter-params")
    // Source: https://mvnrepository.com/artifact/io.qameta.allure/allure-jupiter
    testImplementation("io.qameta.allure:allure-jupiter:2.35.4")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    // Source: https://mvnrepository.com/artifact/io.rest-assured/rest-assured
    testImplementation("io.rest-assured:rest-assured:6.0.1")
}

tasks.test {
    useJUnitPlatform()
}