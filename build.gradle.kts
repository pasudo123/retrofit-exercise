import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.5.6"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    kotlin("jvm") version "1.5.31"
    kotlin("plugin.spring") version "1.5.31"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    // https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-configuration-processor
    // springboot configuration processor
    implementation("org.springframework.boot:spring-boot-configuration-processor")


    // springboot validation
    implementation("org.springframework.boot:spring-boot-starter-validation")

    // logging
    implementation("io.github.microutils:kotlin-logging-jvm:2.0.10")

    // retrofit2 : for api communicate
    // https://square.github.io/retrofit/
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-jackson:2.9.0")
    implementation("com.squareup.retrofit2:adapter-rxjava2:2.9.0")

    // okhttp3 logging
    implementation("com.squareup.okhttp3:logging-interceptor")

    // test
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    // MockWebServer external api test
    // https://github.com/square/okhttp/blob/master/mockwebserver/README.md
    // https://mvnrepository.com/artifact/com.squareup.okhttp3/mockwebserver
    // retrofit2 의 okhttp 버전과 맞춰주어야 한다.
    testImplementation("com.squareup.okhttp3:mockwebserver:3.14.9") {
        // junit4 를 제외해주면,  org.junit.rules.ExternalResource 에 대한 의존성이 없어 컴파일 에러가 난다.
        // this.exclude(group = "junit", module = "junit")
    }

    // mock & kotest
    testImplementation("io.mockk:mockk:1.10.6")
    testImplementation("io.kotest:kotest-assertions-core:4.4.3")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
