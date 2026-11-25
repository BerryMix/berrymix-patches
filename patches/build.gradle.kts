group = "app.berrymix"

patches {
    about {
        name = "BerryMix Patches"
        description = "Patches for personal use"
        source = "git@github.com:BerryMix/berrymix-patches.git"
        author = "BerryMix"
        contact = "https://github.com/BerryMix/berrymix-patches/issues"
        website = "https://github.com/BerryMix/berrymix-patches"
        license = "GNU General Public License v3.0"
    }
}

// Separate configuration so gson is available at runtime for the
// generatePatchesList task but never bundled into the APK.
val patchListGeneratorClasspath = configurations.create("patchListGeneratorClasspath")

dependencies {
    compileOnly(libs.gson)
    patchListGeneratorClasspath(libs.gson)
}

tasks {
    register<JavaExec>("generatePatchesList") {
        description = "Build patch with patch list"

        dependsOn(build)

        classpath = sourceSets["main"].runtimeClasspath + patchListGeneratorClasspath
        mainClass.set("util.PatchListGeneratorKt")
    }

    // Used by gradle-semantic-release-plugin.
    publish {
        dependsOn("generatePatchesList")
    }
}
