# CorpalivApp - Alba Lab
Maximiliano Córdova Ibáñez 
Aplicación móvil de gestión de colaboraciones y donantes desarrollada para la corporación Alba Lab. Construida bajo la arquitectura MVVM, utilizando persistencia de datos local y componentes modernos de interfaz de usuario.

## Características Principales (Rúbrica)
* **Formulario de Donaciones:** Captura de datos de los colaboradores validando tipos específicos (String, Int, Date/String, Checkbox y Switch).
* **Acceso de Administradores:** Sistema de login independiente con cifrado local de contraseñas mediante funciones Hash.
* **Recursos Nativos:**
  * **Cámara:** Integración de `TakePicturePreview` para adjuntar comprobantes fotográficos de manera directa.
  * **Marcador Telefónico:** Acceso a la línea de contacto corporativa mediante `ACTION_DIAL`.
* **Bonus Track:** Implementación de Notificaciones Locales (canales nativos de Android) para alertar al usuario tras un registro exitoso.

## Arquitectura y Tecnologías
* **Lenguaje:** Kotlin
* **UI:** Jetpack Compose
* **Patrón de Diseño:** MVVM (Model-View-ViewModel) con manejo de estados reactivos a través de `StateFlow`.
* **Base de Datos:** Room Database (DAO, Entidades `AdminUser` / `Collaboration`, y Patrón Repository).
