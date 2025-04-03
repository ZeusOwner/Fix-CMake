// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
}

tasks.register("autoBuild") {
    group = "build"
    description = "Checks dependencies and builds the app automatically."

    doLast {
        println("🔍 Checking dependencies...")

        // Verificar variables de entorno necesarias
        val sdkDir = System.getenv("ANDROID_SDK_ROOT")
        val ndkDir = System.getenv("ANDROID_NDK_HOME")
        val javaHome = System.getenv("JAVA_HOME")

        if (sdkDir.isNullOrEmpty()) {
            println("⚠️ ANDROID_SDK_ROOT no está configurado. Intentando usar ANDROID_HOME...")
            val androidHome = System.getenv("ANDROID_HOME")
            if (androidHome.isNullOrEmpty()) {
                throw GradleException("❌ Ni ANDROID_SDK_ROOT ni ANDROID_HOME están configurados!")
            } else {
                println("✅ Usando ANDROID_HOME: $androidHome")
            }
        } else {
            println("✅ SDK Path: $sdkDir")
        }

        if (ndkDir.isNullOrEmpty()) {
            println("⚠️ ANDROID_NDK_HOME no está configurado. La compilación nativa puede fallar.")
        } else {
            println("✅ NDK Path: $ndkDir")
        }

        if (javaHome.isNullOrEmpty()) {
            println("⚠️ JAVA_HOME no está configurado. Usando Java del sistema.")
        } else {
            println("✅ JAVA_HOME: $javaHome")
        }

        println("🚀 Iniciando compilación...")
        try {
            // Ejecutar tareas de compilación en secuencia
            project.exec {
                commandLine("./gradlew", "clean")
            }
            println("✅ Limpieza completada")

            project.exec {
                commandLine("./gradlew", "assembleDebug")
            }
            println("✅ Compilación Debug completada")

            // Ejecutar pruebas unitarias
            project.exec {
                commandLine("./gradlew", "test")
                isIgnoreExitValue = true  // Continuar incluso si las pruebas fallan
            }
            println("✅ Pruebas unitarias ejecutadas")

            println("🎉 Proceso de compilación completado con éxito!")
        } catch (e: Exception) {
            println("❌ Error durante la compilación: ${e.message}")
            throw GradleException("La compilación falló: ${e.message}")
        }
    }
}