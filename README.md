# myHome_Server_Side
Server-side repo para el TPO de Aplicaciones distribuidas (2do cuatrimestre, 2023)

## Descripción
MyHome es una aplicación de Android que permite a los usuarios administrar propiedades inmobiliarias. 
Funciones disponibles en esta versión:
- Registro de inmobiliaria
- Reset de contraseña
- Ingreso como inmobiliaria
- Creacion de propiedades
- Visualización de propiedades
- Visualización Perfil de inmobiliaria

## IDE
IntelliJ IDEA 2023.1.2

### SDK Versions
Amazon Coretto version 17.0.8

### Dependencias
org.springframework.boot:spring-boot-starter-data-jpa

org.springframework.boot:spring-boot-starter-web

org.springframework.boot:spring-boot-devtools

org.springframework.boot:spring-boot-test

org.springframework.boot:spring-boot-starter-test

org.springframework.boot:spring-boot-starter-validation

org.projectlombok:lombok

org.postgresql:postgresql

### Clases
Las clases fueron agrupadas por paquetes. Si bien el proyecto en sí es un monolito, se organizaron siguiendo las practicas de microservicios. Esto es:
* **Controllers**: encargados de recibin y manejar las llamadas a la aplicacion;
* **Services**: encargados de hacer de intermediarios entre los controladores y la capa de persistencia, asi como tambien de implementar la lógica de negocio.
* **Repositories**: encargados de obtener y persistir los datos de la base de datos.
* **Model**: separados en Entity y DTO, se encargan de manejar el modelo de datos, y la comunicacion de datos entre el server y la app.

## Instalación
Para instalar la aplicación, siga estos pasos:
1. Descargue el proyecto de GitHub  
2. Abra el proyecto en IntelliJ o su IDE de preferencia.
3. Asegurese de tener el SDK correspondiente.
4. Compile y ejecute la aplicación.

## Uso
El server-side puede correrse localmente sin problemas. Sin embargo, de no querer hacer esto, tambien se encuentra desplegado en Azure.

Se debe tener en cuenta que, ante la falta de uso luego de un periodo de tiempo dado, Azure pone a la aplicacion a dormir. Esto implica que, en algunas ocasiones, el servidor puede tardar unos minutos en responder.

Luego de que el servidor a despertado a la aplicación, esta responde rapidamente a todos los requests.

## Autores Equipo 10
Falasco Germán

Godoy Andres

Guzmán Nydia

Leto Marcelo

Salvioli Santiago
