# Proyecto: Evaluación Java

Este proyecto es un entregable de un ejercicio Práctico java

El proyecto incluye un archivo **_import.sql_** para llenar con data inicial, el cual consta de un registro de usuario, para el login el cual es:   admin@eval.cl / admin.
El proyecto utiliza una BD H2 e inicia en el puerto por defecto 8080.
Contiene un archivo  **_Eval-Java -DDQS.postman_collection.json**_ el cual es una colección de Postman con las apis a consumir segun indcaciones de la prueba.

## Servicio 1: Crear Usuario
Este servicio permite crear un usuario con una lista de telefonos asociados

### Ruta de Consumo
POST:  localhost:8080/v1/user

Respuesta esperada:
```json
{
    "mensaje": "Operación Exitosa",
    "data": {
        "idUser": "ca8ac93c-1f5b-4760-9168-c76c36aa8f52",
        "created": "2024-08-19",
        "modified": "2024-08-19",
        "lastLogin": "2024-08-19",
        "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJqdWFuQHJvZHJpZ3VlejMuY2wiLCJpYXQiOjE3MjQxMDEwMjYsImV4cCI6MTcyNDExOTAyNn0.u7cmBPSXjN4QA2wfbaCas755IEUBo-Pvm4jBSFNswZ_XUbkn_rYx1xyucxny-rsddDaqURko_kkLRHnsB1d02A",
        "active": true
    }
}
```



## Ejecución del Proyecto

1. Clona el repositorio o descarga los archivos.

2. Abre una terminal en el directorio raíz del proyecto.

3. Ejecuta el siguiente comando para compilar y ejecutar la aplicación:

mvn spring-boot:run
