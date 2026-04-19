## Preguntas a responder

- [CE 7.c] 
  - ¿Que librería/clases has utilizado para realizar la práctica.? Comenta los métodos mas interesantes
    - He hecho uso de la libreria file para poder trabjar con archivos de forma que se puedan crear archivos y mostrar la informacion por pantalla a partir de esos archivos

- [CE 7.d] 
  - 2.a ¿Que formato le has dado a la información del fichero para guardar y recuperar la información? 
    - Se ha utilizado el formato CSV con separador de punto y coma
  - 2.b ¿Que estrategia has usado para trabajar con los ficheros? (Carpeta en donde se guardan los ficheros, cuando crear los archivos, ....) 
    - Los ficheros se crean en la raiz del proyecto, se crean haciendo uso del bloque init de forma que si no estan ya creados se crean con su cabecera. se escribe en los ficheros haciendo uso de appenText
  - 2.c ¿Cómo se gestionan los errores? Pon ejemplos de código (enlace permanente al código en GitHub).
    - Se gestionan mediante bloques try/catch en los metodos de acceso a ficheros.
    - https://github.com/IES-Rafael-Alberti/2526-u7-7-3-travelbookerdao-DayronTorresYegua/blob/edbd4b650ce9e3856597948388dafb57cae78589/src/main/kotlin/es/iesra/datos/DaoReservaHotel.kt#L50-L60
    - https://github.com/IES-Rafael-Alberti/2526-u7-7-3-travelbookerdao-DayronTorresYegua/blob/edbd4b650ce9e3856597948388dafb57cae78589/src/main/kotlin/es/iesra/datos/DaoReservaHotel.kt#L36-L48


- [CE 7.e] 
  - 3.a Describe la forma de acceso para leer información 
    - Se realiza con el metodo leer
    - Se leen las lineas con fichero.readLines
    - Se quita la primera que es la cabecera con el drop(1)
    - Se filtras lineas vacias con .filter { it.isNotBlank() }
    - Cada línea se convierte a objeto con mapNotNull { lineaCsvAReservaVuelo(it) } usando el método privado que hace split(";") y llama a creaInstancia.
  - 3.b Describe la forma de acceso para escribir información 
    - Se usa el metodo crear(), se comprueba que no exista una reserva con el mismo id y la conversion de objeto a CSV se usa el metodo reservaVueloALinea que une los campos con ;
    - https://github.com/IES-Rafael-Alberti/2526-u7-7-3-travelbookerdao-DayronTorresYegua/blob/edbd4b650ce9e3856597948388dafb57cae78589/src/main/kotlin/es/iesra/datos/DaoReservaHotel.kt#L33-L34
  - 3.c Describe la forma de acceso para actualizar información. Pon ejemplos de código (enlace permanente al código en GitHub).
    - Se leen las reservas de los ficheros con leer()
    - Se busca el indice de reserva a actualizar con indexOfFirst { it.id == reserva.id }
    - Si se encuentra se reemplaza en la lista mutable en memoria
    - Se llama a reSe llama a reescribirFichero(reservas) que sobreescribe el fichero completo con writeText() escribiendo primero la cabecera y después todas las reservas actualizadas
    - https://github.com/IES-Rafael-Alberti/2526-u7-7-3-travelbookerdao-DayronTorresYegua/blob/edbd4b650ce9e3856597948388dafb57cae78589/src/main/kotlin/es/iesra/datos/DaoReservaHotel.kt#L62-L77
    - https://github.com/IES-Rafael-Alberti/2526-u7-7-3-travelbookerdao-DayronTorresYegua/blob/edbd4b650ce9e3856597948388dafb57cae78589/src/main/kotlin/es/iesra/datos/DaoReservaHotel.kt#L95-L101