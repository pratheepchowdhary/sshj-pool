plugins {
    kotlin("jvm") version "2.2.10"
}

group = "com.connecthid.sshjpool"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("com.hierynomus:sshj:0.38.0")
    implementation("org.apache.commons:commons-pool2:2.12.1")
    implementation("org.slf4j:slf4j-api:2.0.12")
    implementation("net.sf.expectit:expectit-core:0.9.0")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}