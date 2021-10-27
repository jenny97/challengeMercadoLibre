# Desafío Mutantes

Este proyecto desarrollado en Java contiene un servicio post que permite analizar la secuencia de ADN de un humano, verificando si este es mutante o no.
Recibe un arreglo de strings , donde cada uno representa cada fila de una tabla NxN. . Las letras de los Strings solo pueden ser: (A,T,C,G), las
cuales representa cada base nitrogenada del ADN.
Los datos analizados son almacenados en una base de datos mongo, por lo que se tiene un servicio get para la consulta de las estadísticas de las verificaciones de ADN realizadas.
Ver la aplicación desplegada:  https://mutant-ml-app.herokuapp.com/

Ver documentación swagger de los servicios: https://mutant-ml-app.herokuapp.com/swagger-ui.html
Desde la cual puedes probar los servicios disponibles en la aplcación, o puedes acceder directamente a ellos mediante los siguientes endpoints:

** Post : https://mutant-ml-app.herokuapp.com/mutant
** Get: https://mutant-ml-app.herokuapp.com/stats


## Comenzando 🚀

Para obtener una copia del proyecto, se debe clonar la rama principal.

### Pre-requisitos 📋

Se debe tener instalado en la máquina el jdk de java versión 8.
Debe contar con un IDE para java, puede ser eclipse.
El IDE debe tener integrada la librería lombok, que se usa para facilitar el desarrollo del código evitando escribir ciertos métodos repetitivos como los get y los set.
Ver documentación para su instalación : https://projectlombok.org/


### Instalación 🔧

En el IDE de su preferencia, importe el proyecto clonado como un Proyecto existente de Maven.
Para ejecutar la aplicación, corra la clase principal "ChallengeApplication" como una aplicación Java.
La aplicación está lista para usarse, por defecto corre en la ruta http://localhost:8080/, si tiene problemas con el uso de este puerto, puede especificar el de su preferencia 
en el archivo application.properties de la siguiente manera : server.port=8082

Prueba el análisis de ADN  para saber si un humano es o no mutante:
 Por medio de postman, consume un servicio tipo Post con la ruta http://localhost:8080/mutant/
 Agrega un cuerpo tipo json a la petición con el arreglo de string que representa la cadena de adn, tal como se indica en la documentación de swagger de la aplicacíón.
 Ejemplo: 
 {
  "dna": [
    "ATGCGT",
    "TAGTAC",
    "TTGTGG",
    "AGTAGG",
    "CCCCTA",
    "TCACTG"
  ]
}

Si la información contiene una letra diferente a las permitidas (A,T,C,G) o no representa una matriz de NxN, recibirá como respuesta una excepción de tipo InvalidDataException.
Si la información es correcta, y el ADN corresponde a un humano mutante (Si la matriz, contiene mas de una secuencia de 4 letras iguales, ya sea en filas, columnas o en sus diagonales),
obtendrá como respuesta un  un HTTP 200-OK, de lo contrario un 403-Forbidden.
https://mutant-ml-app.herokuapp.com/swagger-ui.html#/adn-validator-service/mutantUsingPOST

Consulta las estadísiticas de las verificaciones de ADN que se han realizado hasta el momento:
Cada que se hace un análisis de ADN (método anterior), la información es almacenada en una base de datos de mongo.
Consuma el servicio tipo GET con la ruta http://localhost:8080/stats/ para obtener las estadísticas de los datos guardados.
Como respuesta obtendrá un json con la cantidad de humanos analizados, cantidad de mutantes encontrados, y porcentaje de mutantes con respecto al total de ADNs verificados.
https://mutant-ml-app.herokuapp.com/swagger-ui.html#/adn-validator-service/getStatsUsingGET

Las pruebas unitarias están implementadas con Junit. Se pueden ver en la carpeta test del proyecto y ser ejecutadas para su comprobación.

## Construido con 🛠️

* [SpringBoot](https://spring.io/projects/spring-boot/) - Framework web usado
* [Spring Data MongoDB](https://spring.io/projects/spring-data-mongodb/) - Para la integración con la base de datos MongoDB.
* [Maven](https://maven.apache.org/) - Manejador de dependencias
* [Lombok](https://projectlombok.org/) - Librería Java para eliminar código repetitvo
* [Swagger](https://swagger.io/) - Framework para generar documentación de APIs REST
* [Junit](https://junit.org/) - Para pruebas unitarias.


## Autores ✒️

* **Jenny Alejandra Castaño López** - *Desarrollo y documentación.* - [JennyCastaño]


