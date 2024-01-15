# **Mercado Libre Android App**

Esta es una aplicación de ejemplo desarrollada como parte del desafío técnico para Mercado Libre. La aplicación consta de tres pantallas principales: un buscador, una pantalla de resultados y una pantalla de detalles.

## **Características Principales**

- **Arquitectura MVVM y Clean Architecture:** La aplicación sigue una arquitectura de Modelo-Vista-VistaModelo (MVVM) y Clean Architecture para mantener un código organizado, escalable y fácil de mantener.
- **Bibliotecas Utilizadas:**
    - Coroutines: Para manejar de manera eficiente las operaciones asíncronas.
    - Retrofit: Para realizar solicitudes HTTP a la API de Mercado Libre.
    - Gson: Para el parsing de datos JSON.
    - Glide: Para cargar y mostrar imágenes de manera eficiente.
    - Navigation Components: Para la navegación entre las distintas pantallas de la aplicación.
    - Dagger Hilt: Para la inyección de dependencias.
- **Unit Testing:**
    - [MockK](https://mockk.io/): Se utiliza para la creación de objetos falsos y simulaciones durante las pruebas unitarias.
    - Core Testing: Proporciona clases y métodos de utilidad para realizar pruebas en componentes de Android.
    - Kotlin Coroutines Test: Ofrece funciones de prueba específicas para coroutines.
 
    - ## **Estructura del Proyecto**

La estructura del proyecto sigue las mejores prácticas de organización:

|-- app
    |-- src
        |-- main
            |-- java
                |-- com.example.mercadolibre
                    |-- data
										|-- di
                    |-- domain
                    |-- presentation
            |-- res
|-- test
    |-- java
        |-- com.example.mercadolibre
            |-- data
            |-- domain
            |-- presentation

```

- **`data`:** Contiene las clases responsables de acceder y gestionar los datos de la aplicación.
- **`di`:** Contiene las clases que proveen los módulos adecuados para la gestión de la inyección de dependencias.
- **`domain`:** Contiene las clases que definen la lógica de negocio de la aplicación.
- **`presentation`:** Contiene las clases encargadas de la interfaz de usuario y la presentación de datos.
