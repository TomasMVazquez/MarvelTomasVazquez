# **Marvel App - [Tomás Vazquez](https://www.linkwdin.com/in/tomas-vazquez)**
***
## Información General

Aplicación, con listado de los personajes de Marvel, desarrollada sobre Android nativo:
- en Kotlin:
- Clean Architecture y principios SOLID.
- con POO.
- con el concepto MVVM.
- y utilizando:
  - Patrones de diseño.
  - Data Binding.
  - Constraint Layouts.
  - Navigation components.
  - Corrutinas.
  - Librería *Glide* para la carga de imagenes.
  - ROOM para la BD local.
  - Retrofit para la conexión API REST de [Marvel](https://developer.marvel.com/docs).
  - Unit Tests.
***
## Detalle de la Aplicación

Comienza con un **Splash Screen** donde valida si es la primera vez que inicializa, en caso de ser verdadero se muestra un **OnBoarding** y al finalizar se guarda en las SharedPreferences un booleano para no vovler a mostrar.

La primer pantalla es la ***"Home"*** donde a través de la API se buscarán los personajes y se mostrarán en un listado, el cuál al ir scrolleando se irán cargando cada vez más personajes.

La segunda pantalla es el ***"Buscador"*** donde se podrá buscar por nombre los personaje/s y se mostrarán un listado.

La tercer pantalla es la de ***"Favoritos"*** donde se mostrarán los personajes marcados como favoritos, los cuales son guardados en una base de datos local.

En todas estas pantallas podremos clickear en los personajes e ir a la pantalla de ***"Detalle"***, donde tendremos un botón para añadir o eliminar al personaje de mis favoritos.
