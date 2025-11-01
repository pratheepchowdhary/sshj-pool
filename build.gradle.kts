plugins {
    kotlin("jvm") version "2.2.10"
    id("com.vanniktech.maven.publish") version "0.34.0"
}

group = "com.connecthid.sshjpool"
version = "1.0.0"

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

mavenPublishing {
    publishToMavenCentral()

    signAllPublications()
}
//https://vanniktech.github.io/gradle-maven-publish-plugin/central/
//gpg --keyring secring.gpg --export-secret-keys > ~/.gnupg/secring.gpg
mavenPublishing {
    coordinates(project.group.toString(), "SshJPool", project.version.toString())

    pom {
        name.set("SSH Connection Pool")
        description.set("Reusable SSH Connection Pool for Kotlin using SSHJ + Apache Commons Pool2")
        inceptionYear.set("2025")
        url.set("https://github.com/pratheepchowdhary/sshj-pool")
        licenses {
            license {
                name.set("The Apache License, Version 2.0")
                url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                distribution.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
            }
        }
        developers {
            developer {
                id.set("pratheepkanati <pratheepkanati@gmail.com>")
                name.set("Pratheep Kanati")
                url.set("https://github.com/pratheepchowdhary/")
            }
        }
        scm {
            url.set("https://github.com/pratheepchowdhary")
            connection.set("scm:git:git://github.com/pratheepchowdhary/sshj-pool.git")
            developerConnection.set("scm:git:ssh://git@github.com/pratheepchowdhary/sshj-pool.git")
        }
    }
}
