plugins {
    //id("com.github.ben-manes.versions") version "0.52.0"
    id("application")
    id("org.sonarqube") version "7.0.1.6134"
    id("checkstyle")
    jacoco
}

group = "hexlet.code"
version = "1.0-SNAPSHOT"


repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    implementation("tools.jackson.core:jackson-databind:3.0.3")
    implementation("tools.jackson.dataformat:jackson-dataformat-yaml:3.0.3")
}

tasks.test {
    useJUnitPlatform()
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)

    reports {
        xml.required.set(true)
        html.required.set(true)
    }
}

application {
    mainClass = "hexlet.code.App"
}

sonar {
    properties {
        property("sonar.projectKey", "podolyak-tatyana_qa-auto-engineer-java-project-78")
        property("sonar.organization", "podolyak-tatyana")
        property(
            "sonar.coverage.jacoco.xmlReportPaths",
            "build/reports/jacoco/test/jacocoTestReport.xml"
        )
    }
}


