# Proyecto: Evaluación Java

Este proyecto es un entregable de un ejercicio Práctico java

El proyecto incluye un archivo **_import.sql_** para llenar con data inicial, el cual consta de un registro de usuario, para el login el cual es:   admin@eval.cl / admin , el token recibido de este login permitira interacturar con el resto de apis.

Los path libres de token son las de documentación swagger, tanto la interfaz como la de formato JSON:
- http://localhost:8080/api-docs
- http://localhost:8080/swagger-ui/index.html
- http://localhost:8080/login

Utiliza una BD H2 e inicia en el puerto por defecto 8080.

Contiene un archivo  **_Eval-Java -DDQS.postman_collection.json**_ el cual es una colección de Postman con las apis a consumir segun indicaciones de la prueba, y con data dummy necesaria, en la documentación swagger se podra tener el detalle de lo que se requiere para cada servicios, así como la respuesta del mismo.


Orden para consumir los servicios principales
## Servicio 1: Login
Este servicio permite obtener un token válido para consumir las otras apis, es el único libre de autenticación

### Ruta de Consumo
POST:  localhost:8080/login

Payload  (Reregistro inicial cargado a traves de **_import.sql_** al monento de levantar el proyecto):
```json
{
    "email": "admin@eval.cl",
    "password": "admin"
}
```

Respuesta esperada:
```json
{
    "mensaje": "Operación Exitosa",
    "data": {
        "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbkBldmFsLmNsIiwiaWF0IjoxNzI0MTQxMDEyLCJleHAiOjE3MjQxNTkwMTJ9.QSxB6NBmUjlI93k5tycJfJBZKyKQnDARotxRkoBvAc16sNFek66w7daS2qiHdADmNDWcEYSdIUCtySlGGr5LdA"
    }
}
```

## Servicio 2: Crear Usuario
Este servicio permite crear un usuario con una lista de telefonos asociados

### Ruta de Consumo
Requiere token 

POST:  localhost:8080/v1/user

Payload:
```json
{
    "name": "Juan Rodriguez",
    "email": "juan@rodriguez41.cl",
    "password": "Hhunter2",
    "phones": [
        {
            "number": "1234567",
            "cityCode": "1",
            "countryCode": "57"
        }
    ]
}
```

Respuesta esperada:
```json
{
    "mensaje": "Operación Exitosa",
    "data": {
        "idUser": "8ad819a6-2594-4fb1-97c2-a92eed29cc7e",
        "created": "2024-08-20T03:05:27.4896887",
        "modified": "2024-08-20T03:05:27.4906892",
        "lastLogin": "2024-08-20T03:05:27.5056886",
        "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJqdWFuQHJvZHJpZ3VlejQxLmNsIiwiaWF0IjoxNzI0MTQxMTI3LCJleHAiOjE3MjQxNTkxMjd9.Ta_Yc6AttxsU4B-B7TQrrd7-E9NBbtVHNuV4MaC6yvREXmjXlB_rL_eeoiyFQyYXOip0galjIY90RhGmoabL7A",
        "active": true
    }
}
```
## Servicios complementarios: List, ObtenerPorUUI, Actualizar y Eliminar
Estos servicios complementan el controller de User, todos basados en UUID en cuanto a busquedas individuales, y el eliminar basado en el campo isActive hará un borrado lógico

### Rutas de Consumo cion ejemplo
Requieren token 

GET:  
- localhost:8080/v1/user

GET: 
- localhost:8080/v1/user/49f81ff0-f5a4-4388-863c-b94f6add5437
  
PATCH:
- localhost:8080/v1/user/49f81ff0-f5a4-4388-863c-b94f6add5437
  
DELETE: 
- localhost:8080/v1/user/49f81ff0-f5a4-4388-863c-b94f6add5437

## Ejecución del Proyecto

1. Clona el repositorio o descarga los archivos.

2. Abre una terminal en el directorio raíz del proyecto.

3. Ejecuta el siguiente comando para compilar y ejecutar la aplicación:

4. mvn spring-boot:run
