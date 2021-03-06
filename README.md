# Aircraft Competition Program

![planemid](https://user-images.githubusercontent.com/31405248/118563229-7a3c7480-b76e-11eb-8bb1-a238ba7aad70.png)


## Objetivo:

El objetivo de este proyecto es manejar competiciones de aeromodelismo, pudiendo añadir competiciones, eliminarlas, registrar usuarios...




## Funciones:

 ### 1. Configurar la base de datos

```
Si pulsamos la rueda dentada de la parte superior izquierda se nos
abrirá una ventana que nos solicitará los datos de la base
```
 ### 2. Registrarse

```
Para registrarse habrá que pulsar el botón de “Registrarse” en la
ventana principal y rellenar los datos
```
Tras esto, ya estaría registrado


### 3. Iniciar sesión

```
Para iniciar sesión pulsaremos el botón de “Iniciar Sesión” de la
ventana principal e introduciremos el número de licencia y
contraseña
```
### 4. Crear y eliminar competiciones

```
Para crear y eliminar competiciones iniciaremos sesión con el
usuario administrador, cuyo número de licencia será 0.
Tras iniciar sesión como administrador, nos aparecerán los botones
de “Nueva Competición” y “Borrar Competición”.
```

### 5. Inscribirse y des inscribirse

```
Para Inscribirse y Des inscribirse seleccionaremos una competición y
pulsaremos el botón de la esquina inferior derecha, tras eso,
pulsaremos el botón superior izquierdo para actualizar la página.
```
### 6. Añadir puntuaciones

```
Para añadir puntuaciones pulsaremos el botón de “Añadir
puntuación” que nos sale en la esquina inferior izquierda cuando
tenemos seleccionada una competición en la que estamos inscritos.
Tras eso, nos aparecerá una ventana donde introduciremos la
manga a la que corresponde la puntuación y el resto de datos.
```

### 7. Ver clasificación

```
Para ver la clasificación de una competición pulsaremos el botón de
“Ver clasificación” encontrado en la mitad del borde inferior.
Tras esto, podremos ver la puntuación de todos los participantes.
```



## Posibles errores y cómo arreglarlos

### 1 - Error al iniciar sesión o aplicación.

```
En este caso, revisa la configuración de la base de datos o si esta
está disponible
```
### 2 - Error en el muestreo de datos de la tabla.

```
Ocurre a veces si se cambia la selección de la tabla cada poco
tiempo o si se han intentado cambiar datos simultáneamente desde
varios clientes.
En ese caso pulsa el botón de recargar. Si tras eso no funciona
inténtalo más tarde
```
### 3 - Error al pulsar botones (No reaccionan).

```
Se desconocen los motivos de este error, se soluciona al reiniciar la
aplicación.
```
```
Proyecto creado por: Jaime, Juan y Pablo
```



## Pasos para hacer funcionar el programa

### 1 - Descargar la carpeta del proyecto
   
### 2 - Importar el proyecto en eclipse
   
### 3 - Tener instalada la extensión de JavaFX
   
### 4 - Anadir al JavaBuildPath > Libraries > Modulepath las direcciones a la carpeta lib del proyecto, de javafx-sdk-11.0.2/lib y mysql-connector-java-8.0.23
   
### 5 - Establecer los argumentos del Main de la siguiente manera 
```--module-path Directory\AircraftCompetitionProgram\AircraftCompetition\javafx-sdk-11.0.2\lib --add-modules=javafx.controls,javafx.fxml```
   
### 6 - Tras estos ajustes el proyecto debería funcionar sin problema

(El proyecto viene con una base de datos predeterminada, si quiere puede crear la suya propia con Tables.txt)
