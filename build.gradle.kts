plugins {
    java
    id("org.springframework.boot")
    id("io.spring.dependency-management")

}

tasks.withType<org.springframework.boot.gradle.tasks.run.BootRun> {
    mainClass.set("org.kwan.javaspringstarter.YourApplication")
}

allprojects {
    group = "org.kwan.java-spring-starter"
    version = "1.0.0"

    repositories {
        mavenLocal()
        mavenCentral()
        maven("https://repo.spring.io/release")
        maven("https://repo.spring.io/milestone/")
        maven("https://jitpack.io")
    }
}

subprojects {
    val springBootVersion: String by project
    val lombokVersion: String by project
    val slf4jVersion: String by project
    val logbackVersion: String by project
    val logbackContribVersion: String by project

    apply(plugin = "java")
    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")

    tasks.test {
        useJUnitPlatform()
    }

    dependencies {
        implementation(platform("org.springframework.boot:spring-boot-dependencies:$springBootVersion"))
        implementation("org.springframework.boot:spring-boot-starter-web")
        implementation("org.springframework.boot:spring-boot-starter-test")

        implementation("org.slf4j:slf4j-api:$slf4jVersion")
        implementation("ch.qos.logback:logback-core:$logbackVersion")
        implementation("ch.qos.logback:logback-classic:$logbackVersion")
        implementation("ch.qos.logback.contrib:logback-jackson:$logbackContribVersion")
        implementation("ch.qos.logback.contrib:logback-json-classic:$logbackContribVersion")

        implementation("com.fasterxml.jackson.core:jackson-databind")

        implementation("org.projectlombok:lombok:$lombokVersion")
        annotationProcessor("org.projectlombok:lombok:$lombokVersion")

        testImplementation(platform("org.junit:junit-bom:5.10.0"))
        testImplementation("org.junit.jupiter:junit-jupiter-api")
        testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
    }
}
