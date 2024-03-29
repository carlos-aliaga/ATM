### Descripción del Proyecto

Cajero automático (ATM) implementado mediante microservicios en Java con Spring Boot.

Los microservicios se encargan de la gestión de clientes y gestión de transacciones monetarias. 

Se utiliza Postgress como bases de datos, AWS Event bridge para la gestión de eventos y Docker para la contenerización de la aplicación.

**Tecnologías Utilizadas** 
Microservicios desarrollados en Java 17 con Spring Boot.

- Gestión Transacciones: Microservicio responsable de las transacciones monetarias.
- Gestión Cuentas: Microservicio responsable de la gestión de las cuentas de clientes
- Gestión Tipo de cambio: Microservicio responsable de almacenar y actualizar tipos de cambios.
- Gestión de clientes: Microservicio encargado de la gestión de clientes.

**Bases de Datos Postgress**

Utilizado para almacenar datos transaccionales, financieros y de clientes. Version 16

**Event-Driven Architecture: AWS SQS**

Como sistema de mensajería para implementar una arquitectura basada en eventos.

**Contenerización: Docker**

La aplicación está contenerizada para facilitar la implementación y la escalabilidad.

#### Configuración y Despliegue Configuración de Microservicios
- Tener Java instalado.
- Configurar propiedades de conexión a base de datos en los archivos de configuración de cada microservicio.

#### Configuración de Bases de Datos
- Instale y configure Postgres según necesidades.

#### Contenerización con Docker
- Utilice los archivos de configuración proporcionados para construir imágenes Docker de los microservicios y la interfaz de usuario.

#### Despliegue
- Despliegue los contenedores Docker en el entorno deseado.

