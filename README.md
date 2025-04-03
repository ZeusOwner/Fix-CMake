# BearMod Android App

## Automatización de Compilación y Despliegue

Este proyecto utiliza GitHub Actions para automatizar el proceso de compilación, pruebas y despliegue. La automatización ha sido implementada como una prioridad máxima para garantizar la calidad del código y facilitar el desarrollo continuo.

### Flujos de Trabajo Implementados

#### 1. Compilación Continua (CI)
- **Archivo**: `.github/workflows/android-ci.yml`
- **Propósito**: Compilar la aplicación automáticamente en cada push o pull request.
- **Características**:
  - Configuración automática del entorno de desarrollo (JDK, Android SDK, NDK)
  - Caché de dependencias para acelerar las compilaciones
  - Ejecución de la tarea personalizada `autoBuild`
  - Publicación de artefactos de compilación

#### 2. Pruebas Automatizadas
- **Archivo**: `.github/workflows/android-test.yml`
- **Propósito**: Ejecutar pruebas unitarias e instrumentadas.
- **Características**:
  - Ejecución de pruebas unitarias con JUnit
  - Ejecución de pruebas instrumentadas en un emulador Android
  - Publicación de resultados de pruebas

#### 3. Despliegue Automático
- **Archivo**: `.github/workflows/android-release.yml`
- **Propósito**: Generar y publicar versiones de la aplicación.
- **Características**:
  - Se activa al crear un tag con formato `v*` (ej: v1.0.0)
  - Firma automática del APK
  - Creación de una nueva release en GitHub con el APK firmado

### Tarea Personalizada `autoBuild`

Se ha implementado una tarea personalizada de Gradle llamada `autoBuild` que:

1. Verifica la configuración del entorno de desarrollo
2. Limpia el proyecto
3. Compila la versión de depuración
4. Ejecuta pruebas unitarias
5. Maneja errores de forma robusta

### Cómo Utilizar la Automatización

#### Para Desarrolladores
1. Realiza tus cambios en una rama separada
2. Crea un Pull Request hacia `main`
3. La automatización ejecutará la compilación y las pruebas
4. Una vez aprobado y fusionado, los cambios pasarán a `main`

#### Para Crear una Nueva Versión
1. Asegúrate de que todos los cambios estén en la rama `main`
2. Crea un nuevo tag con el formato `v1.x.x`:
   ```bash
   git tag v1.0.0
   git push origin v1.0.0
   ```
3. El flujo de trabajo de release se activará automáticamente
4. Una nueva release será creada en GitHub con el APK firmado

### Requisitos para la Firma de APK

Para que el proceso de firma automática funcione, debes configurar los siguientes secretos en tu repositorio de GitHub:

- `SIGNING_KEY`: La clave de firma en formato Base64
- `KEY_ALIAS`: El alias de la clave
- `KEY_STORE_PASSWORD`: La contraseña del keystore
- `KEY_PASSWORD`: La contraseña de la clave

### Solución de Problemas

Si encuentras problemas con la automatización:

1. Verifica los logs de ejecución en la pestaña "Actions" de GitHub
2. Asegúrate de que todas las variables de entorno necesarias estén configuradas
3. Verifica que los secretos para la firma del APK estén correctamente configurados

---

Con esta implementación, el proceso de compilación, pruebas y despliegue está completamente automatizado, permitiendo un desarrollo más rápido y confiable.
