dependencies {
    implementation(project(":features:orders"))
    implementation(project(":features:employees"))

    runtimeOnly("com.h2database:h2")
}

tasks.getByName("bootJar") {
    enabled = true
}

tasks.getByName("jar") {
    enabled = false
}