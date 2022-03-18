# Challenge reactor-core

Dentro del código se encutra un ejemplo para aplicar reactividad con Spring Boot, se cuenta con una herramienta utilitaria para leer archivos CSV, lo que se buscar es aplicar los operadores reactivas para leer este archivo, y se reta para hacer este procedimiento en mongodb.

Leer las pruebas unitarias.


# Step by Step

- Transformar un CSV una lista de Stream de Java (Usar operadores basicos)
- En vez de usar Java Stream aplicamos reactividad (Reactor Core) con trasnformaciones (Usar operadores basicos)
- Optimizar las consultas y aplicar un servicio web para hacer estos filtros
- Hacer un proceso de migración en donde tomemos los datos del archivo y lo llevemos a una base datos (MangoDB)
- Trabajo directamente los desde una base de datos reactiva y objserva la rendimiento
- Prueba de concurencia con JMeter, donde vamos a colocar 100 hilos de ejeucución para observar los comportamientos de cada servicio

# Capturas de funcionamiento
- Funcionamiento mayor a 34
![Funcionamiento mayor 34](https://github.com/mdyagual/challenge-reactor-core/blob/main/ss/TestMayorA34.JPG)
  
- Funcionamiento Nacionalidades
  ![Funcionamiento mayor 34](https://github.com/mdyagual/challenge-reactor-core/blob/main/ss/TestNacionalidad.JPG)

Hecho con mucho entusiasmo para Sofka U(◕‿◕✿)