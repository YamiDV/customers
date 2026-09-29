# Customers API

API REST desarrollada con Spring Boot para la gestión de clientes y consulta de indicadores relacionados con sus fechas de nacimiento.

## Tecnologías

- Java 21
- Spring Boot
- Maven
- Spring Web
- Jakarta Validation
- MapStruct
- Lombok
- OpenAPI / Swagger
- JUnit 5
- Mockito

## Arquitectura

El proyecto utiliza una arquitectura basada en separación de responsabilidades y principios de arquitectura hexagonal.

Estructura principal:

application
├── dto
├── mapper
└── service

domain
├── exception
├── model
├── port
└── usecase

infrastructure
├── config
├── in
│   └── rest
└── out

Flujo principal:

REST Controller
↓
Application Service
↓
Domain Use Case
↓
Persistence Port
↓
Adapter
↓
Repository

Actualmente el proyecto utiliza una implementación en memoria para almacenar clientes durante las pruebas y desarrollo local.

## Funcionalidades

La API permite:

- Registrar clientes.
- Consultar todos los clientes.
- Buscar clientes por DNI.
- Buscar clientes por email.
- Buscar clientes mediante un término general por DNI o email.
- Consultar cantidad de clientes nacidos por mes/año.
- Consultar el mes/año con mayor cantidad de nacimientos.
- Consultar el mes/año con menor cantidad de nacimientos.
- Consultar la tasa de natalidad por mes.
- Documentar y probar los endpoints mediante Swagger / OpenAPI.

## Modelo de cliente

Un cliente contiene los siguientes campos:

{
"id": 1,
"firstName": "Juan",
"lastName": "Perez",
"email": "juan@gmail.com",
"dni": "12345678",
"createdAt": "2026-09-29T10:00:00",
"birthDate": "1998-05-15"
}

## Requisitos

Antes de ejecutar el proyecto es necesario tener instalado:

- Java 21
- Maven 3.9+ o Maven Wrapper
- Git

Verificar Java:

java -version

Verificar Maven:

mvn -version

## Instalación

Clonar el repositorio:

git clone https://github.com/YamiDV/customers.git

Entrar al proyecto:

cd customers

Compilar el proyecto:

./mvnw clean install

También puede utilizarse Maven instalado localmente:

mvn clean install

## Ejecución

Ejecutar con Maven Wrapper:

./mvnw spring-boot:run

O:

mvn spring-boot:run

La aplicación estará disponible por defecto en:

http://localhost:8080

## Endpoints

### Registrar cliente

POST /api/customers/register

Ejemplo de request:

{
"firstName": "Juan",
"lastName": "Perez",
"email": "juan@gmail.com",
"dni": "12345678",
"birthDate": "1998-05-15"
}

Respuesta esperada:

201 Created

Ejemplo de respuesta:

{
"id": 1,
"firstName": "Juan",
"lastName": "Perez",
"email": "juan@gmail.com",
"dni": "12345678",
"createdAt": "2026-09-29T10:00:00",
"birthDate": "1998-05-15"
}

### Consultar clientes

GET /api/customers/find

Consultar todos los clientes:

GET /api/customers/find

Buscar por DNI:

GET /api/customers/find?filterType=DNI&searchTerm=12345678

Buscar por email:

GET /api/customers/find?filterType=EMAIL&searchTerm=juan@gmail.com

Búsqueda general por DNI o email:

GET /api/customers/find?filterType=ALL&searchTerm=juan

Valores permitidos para filterType:

- ALL
- DNI
- EMAIL

Comportamiento:

- ALL sin searchTerm: devuelve todos los clientes.
- ALL con searchTerm: busca coincidencias por DNI o email.
- DNI: filtra por DNI.
- EMAIL: filtra por email.

### Consultar indicadores

GET /api/customers/indicators

La respuesta contiene:

- Cantidad de clientes nacidos por mes/año.
- Periodo con mayor cantidad de nacimientos.
- Periodo con menor cantidad de nacimientos.
- Tasa de natalidad agrupada por mes.

Ejemplo de respuesta:

{
"birthsByPeriod": [
{
"month": 5,
"year": 1998,
"totalBirths": 4
}
],
"highestBirthPeriod": {
"month": 5,
"year": 1998,
"totalBirths": 4
},
"lowestBirthPeriod": {
"month": 7,
"year": 1999,
"totalBirths": 1
},
"monthlyBirthRates": [
{
"month": 5,
"totalBirths": 5,
"birthRate": 31.25
}
]
}

## Cálculo de indicadores

### Cantidad de nacimientos por mes/año

Los clientes se agrupan utilizando el mes y año de birthDate.

Ejemplo:

Mayo 1998 -> 4 clientes
Agosto 2000 -> 3 clientes

### Mes/Año con mayor cantidad de nacimientos

Se obtiene el periodo con el valor más alto de totalBirths.

### Mes/Año con menor cantidad de nacimientos

Se obtiene el periodo con el valor más bajo de totalBirths.

### Tasa de natalidad por mes

Para este proyecto, la tasa mensual se calcula de la siguiente forma:

Clientes nacidos en el mes
-------------------------- x 100
Total de clientes

La agrupación se realiza únicamente por mes, independientemente del año de nacimiento.

Ejemplo:

Si existen 16 clientes y 4 nacieron en mayo:

4 / 16 x 100 = 25%

## Validaciones

Durante el registro se validan, entre otros:

- Nombre obligatorio.
- Apellido obligatorio.
- Formato válido de email.
- DNI obligatorio.
- DNI de 8 dígitos.
- Fecha de nacimiento obligatoria.
- Fecha de nacimiento en el pasado.
- DNI no duplicado.
- Email no duplicado.

La validación de formato se realiza mediante Jakarta Validation.

Las reglas de negocio relacionadas con clientes duplicados se validan en la capa de dominio / caso de uso.

## Manejo de duplicados

Antes de crear un cliente se valida si existe otro cliente con:

- El mismo DNI.
- El mismo email.

En caso de existir, se lanza una excepción de negocio CustomerAlreadyExistsException.

La API puede responder con:

409 Conflict

## Swagger / OpenAPI

La API se encuentra documentada mediante OpenAPI.

Con el backend ejecutándose, Swagger UI está disponible en:

http://localhost:8080/swagger-ui/index.html

El documento OpenAPI en formato JSON está disponible en:

http://localhost:8080/v3/api-docs

Desde Swagger UI es posible:

- Visualizar todos los endpoints.
- Consultar parámetros.
- Revisar modelos de request y response.
- Ejecutar peticiones directamente desde el navegador.

## CORS

Durante el desarrollo local, la API permite peticiones provenientes del frontend Angular ejecutándose en:

http://localhost:4200

Esto permite la comunicación:

Angular :4200
↓
Spring Boot :8080

## Persistencia

Durante la fase actual de desarrollo, el proyecto utiliza un repositorio en memoria con datos de prueba.

Esto permite ejecutar y probar la aplicación sin requerir una base de datos externa.

La arquitectura utiliza un Persistence Port, por lo que la implementación en memoria puede sustituirse posteriormente por JPA/PostgreSQL sin modificar la lógica principal del dominio.

Flujo:

CustomerUseCase
↓
ICustomerPersistencePort
↓
CustomerJpaAdapter
↓
ICustomerRepository
↓
Repositorio en memoria

## Pruebas unitarias

El proyecto incluye al menos una prueba unitaria para una operación del servicio / caso de uso.

Tecnologías utilizadas:

- JUnit 5
- Mockito

Ejemplo de operación probada:

CustomerUseCase.createCustomer()

La prueba verifica:

- Que se consulte si el DNI ya existe.
- Que se consulte si el email ya existe.
- Que se invoque la operación de persistencia.
- Que se devuelva correctamente el cliente creado.

Ejecutar pruebas con Maven Wrapper:

./mvnw test

O:

mvn test

Resultado esperado:

Tests run: 1
Failures: 0
Errors: 0

BUILD SUCCESS

## Postman

Los endpoints pueden probarse también mediante Postman.

Endpoints principales:

POST http://localhost:8080/api/customers/register

GET http://localhost:8080/api/customers/find

GET http://localhost:8080/api/customers/indicators

La colección de Postman puede incluir los casos de:

- Registro de cliente.
- Consulta sin filtros.
- Consulta por DNI.
- Consulta por email.
- Consulta de indicadores.

## Integración con Frontend

El backend está preparado para ser consumido por la aplicación web Customers desarrollada en Angular.

Backend:

http://localhost:8080

Frontend:

http://localhost:4200

La aplicación Angular consume los endpoints REST mediante HttpClient.

## Ejemplo de flujo completo

Registro:

Angular
↓
POST /api/customers/register
↓
CustomerRestController
↓
CustomerService
↓
CustomerUseCase
↓
ICustomerPersistencePort
↓
CustomerJpaAdapter
↓
Repository

Consulta:

Angular
↓
GET /api/customers/find
↓
CustomerRestController
↓
CustomerService
↓
CustomerUseCase
↓
Persistence Port
↓
Repository

Indicadores:

Angular
↓
GET /api/customers/indicators
↓
CustomerRestController
↓
CustomerService
↓
Agrupación y cálculo de estadísticas
↓
CustomerIndicatorsResponse

## Código fuente

Repositorio:

https://github.com/YamiDV/customers.git

## Autor

Proyecto desarrollado como prueba técnica para la gestión de clientes utilizando Java 21, Spring Boot y arquitectura hexagonal.
