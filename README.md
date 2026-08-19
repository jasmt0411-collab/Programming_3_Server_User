# Sistema de Consulta del Padrón Electoral

BIS06 — Programación III · Universidad Latina de Costa Rica
Prof. Bryan Vega Rondón · III Cuatrimestre 2026

**Estudiantes:** Jean Carlo Andrade Rojas · Jason Gabriel Flores Reyes

---

## Descripción

Sistema distribuido compuesto por **dos programas Java independientes**
que se comunican exclusivamente por red:

- **Servidor** (`Semana13_patronEle`) — lee `PADRON.txt` y `distelec.txt`
  (archivos planos, sin base de datos) y expone la consulta por cédula
  mediante TCP y HTTP.
- **Cliente** (`ClientePadron`) — interfaz gráfica Swing que consulta al
  servidor por cualquiera de los dos protocolos y muestra el resultado.

Toda la comunicación entre ambos ocurre en formato **JSON**. Ninguno de
los dos programas puede ejecutarse dentro del otro — son procesos
separados que solo se conocen a través del socket o la petición HTTP.

```
Usuario → VentanaPrincipal (Swing) → ServicioConsulta
              → ClienteTCP / ClienteHTTP → red (JSON) →
Servidor (TCPServer / HttpServerPadron) → PadronService
              → PadronRepository / DistritoRepository
              → PADRON.txt / distelec.txt
```

---

## Estructura del repositorio

```
/
├── README.md                     (este archivo)
├── .gitignore
├── Semana13_patronEle/           Servidor
│   ├── src/
│   │   ├── config/                Configuracion (puertos, rutas)
│   │   ├── excepciones/           ConfiguracionException
│   │   ├── entidades/             Persona, DistritoElectoral
│   │   ├── dto/                   PersonaDTO, ErrorDTO
│   │   ├── datos/                 RepositorioPadron/Distritos + implementaciones
│   │   ├── logica/                PadronService
│   │   ├── presentacion/          Main, servidores TCP y HTTP
│   │   ├── util/                  JsonUtil, Validador
│   │   └── pruebas/               ClienteTCPPrueba (cliente de prueba manual)
│   ├── config.properties.example
│   └── data/                      (local, no se sube — ver Configuración)
└── ClientePadron/                Cliente
    ├── src/
    │   ├── config/                 Configuracion (host y puertos del servidor)
    │   ├── excepciones/            ConfiguracionException, ComunicacionException
    │   ├── dto/                    PersonaDTO, ErrorDTO
    │   ├── comunicacion/           ClienteComunicacion + ClienteTCP/ClienteHTTP
    │   ├── logica/                 ServicioConsulta
    │   ├── presentacion/           Main, VentanaPrincipal
    │   └── util/                   JsonUtil, Validador
    └── config.properties.example
```

---

## Requisitos previos

- JDK 17 o superior
- Apache NetBeans
- Librerías (agregadas manualmente como Library de NetBeans en cada
  proyecto, no hay gestor de dependencias):
  - `gson-2.13.1.jar` — servidor y cliente
  - `flatlaf-<versión>.jar` — solo el cliente (descarga directa desde
    [formdev.com/flatlaf](https://www.formdev.com/flatlaf/), no está en
    Maven Central de forma que se pueda bajar sin gestor)

---

## Configuración

Cada proyecto tiene su propio `config.properties`, **no versionado**
(cada integrante mantiene el suyo con sus propias rutas).

### Servidor — `Semana13_patronEle/config.properties`

```properties
puerto.tcp=5000
puerto.http=8080
ruta.padron=data/PADRON_COMPLETO.txt
ruta.distelec=data/distelec.txt
```

Coloque sus copias reales de `PADRON_COMPLETO.txt` y `distelec.txt`
dentro de una carpeta `data/` en la raíz de `Semana13_patronEle`
(al mismo nivel que `build.xml`). Esa carpeta está excluida por
`.gitignore` — los archivos del padrón nunca se suben al repositorio.

### Cliente — `ClientePadron/config.properties`

```properties
servidor.host=localhost
servidor.puerto.tcp=5000
servidor.puerto.http=8080
timeout.ms=5000
```

En ambos casos: copie el `.example` correspondiente, renómbrelo a
`config.properties`, y ajuste los valores. Si el archivo falta o le
falta una clave, la aplicación lo indica con un mensaje claro al
arrancar (no falla a medias).

---

## Cómo ejecutar

**El servidor siempre primero, en su propia instancia de NetBeans o
proceso separado:**

1. Abrir el proyecto `Semana13_patronEle`.
2. Confirmar `config.properties` y la carpeta `data/` con los archivos
   reales.
3. Ejecutar `presentacion.Main`. La consola debe mostrar:
   ```
   Servidor TCP iniciado en puerto 5000
   Servidor HTTP iniciado en puerto 8080
   Servidor del Padron Electoral listo (TCP y HTTP arrancados).
   ```

**Luego el cliente**, con el servidor ya corriendo:

1. Abrir el proyecto `ClientePadron`.
2. Confirmar `config.properties`.
3. Ejecutar `presentacion.Main`. Debe abrir la ventana "Consulta del
   Padrón Electoral".
4. Ingresar una cédula, elegir TCP o HTTP, y pulsar Consultar.

---

## Puertos utilizados

| Protocolo | Puerto por defecto | Configurable en |
|-----------|--------------------|--------------------|
| TCP       | 5000               | `config.properties` de ambos proyectos → `puerto.tcp` / `servidor.puerto.tcp` |
| HTTP      | 8080               | `config.properties` de ambos proyectos → `puerto.http` / `servidor.puerto.http` |

---

## Arquitectura por capas

El mismo criterio de separación se aplicó en los dos programas:

| Capa | Servidor | Cliente |
|---|---|---|
| Presentación | `TCPServer`, `TCPClienteHandler`, `HttpServerPadron` | `VentanaPrincipal` (Swing + FlatLaf) |
| Lógica | `PadronService` | `ServicioConsulta` |
| Datos / Comunicación | `RepositorioPadron`/`RepositorioDistritos` (interfaces) → `PadronRepository`/`DistritoRepository` | `ClienteComunicacion` (interfaz) → `ClienteTCP`/`ClienteHTTP` |
| DTO | `PersonaDTO`, `ErrorDTO` | `PersonaDTO`, `ErrorDTO` (mismo contrato) |
| Configuración | `Configuracion` (puertos + rutas de archivos) | `Configuracion` (host + puertos del servidor) |
| Utilidades | `JsonUtil`, `Validador` | `JsonUtil`, `Validador` |

**Regla de dependencia:** presentación solo conoce lógica; lógica solo
conoce interfaces, nunca implementaciones concretas. Ni el servidor TCP
ni el HTTP acceden a un archivo directamente, y ni `ServicioConsulta` ni
`VentanaPrincipal` conocen si una consulta viaja por socket o por HTTP.

---

## Concurrencia

Tanto `TCPServer` como `HttpServerPadron` usan
`Executors.newCachedThreadPool()`: cada conexión se delega a un hilo del
pool y el hilo principal vuelve de inmediato a aceptar la siguiente, sin
esperar a que la anterior termine. Se eligió un pool cacheado (no uno
fijo) porque las consultas son cortas y las conexiones llegan en
ráfagas, no de forma constante. Cada solicitud crea su propia instancia
de `PadronService`/repositorios — no hay estado compartido entre hilos,
por lo que no se requiere sincronización adicional.

---

## Manejo de errores

| Escenario | TCP | HTTP |
|---|---|---|
| Cédula encontrada | Respuesta JSON con los datos | `200` + JSON |
| Cédula no encontrada | `ErrorDTO` código 404 | `404` |
| Cédula vacía o con formato inválido | `ErrorDTO` código 400 | `400` |
| Comando/método no soportado | `ErrorDTO` código 400 | `405` |
| Solicitud incompleta o ruta inválida | `ErrorDTO` código 400 | `404` |
| Error inesperado (ej. archivo no accesible) | `ErrorDTO` código 500 | `500` |
| Servidor no disponible (visto desde el cliente) | `ComunicacionException` con mensaje claro | `ComunicacionException` con mensaje claro |

En todos los casos el servidor sigue atendiendo otras conexiones, y el
cliente sigue funcionando — un error nunca cierra ninguna de las dos
aplicaciones.

---

## Ejemplos de solicitud y respuesta

### TCP

Protocolo: una línea de texto, formato `COMANDO|valor`.

```
GET|115550555
```
```json
{"cedula":"115550555","nombre":"JUAN","primerApellido":"PEREZ","segundoApellido":"GOMEZ","codigoElectoral":"00101","provincia":"SAN JOSE","canton":"SAN JOSE","distrito":"CARMEN"}
```

```
GET|999999999
```
```json
{"error":true,"codigo":404,"mensaje":"No se encontró la cédula."}
```

### HTTP

```
GET /padron/115550555
```
```json
{"cedula":"115550555","nombre":"JUAN","primerApellido":"PEREZ","segundoApellido":"GOMEZ","codigoElectoral":"00101","provincia":"SAN JOSE","canton":"SAN JOSE","distrito":"CARMEN"}
```

```
POST /padron/115550555
```
```json
{"error":true,"codigo":405,"mensaje":"Método no permitido."}
```

---

## Pruebas realizadas

- [x] Consulta con cédula existente — TCP y HTTP, mismo resultado
- [x] Consulta con cédula inexistente — TCP y HTTP
- [x] Cédula vacía / con formato inválido — rechazada localmente en el
      cliente y también en el servidor
- [x] Solicitud TCP incompleta o con comando desconocido
- [x] Método HTTP distinto de GET
- [x] Múltiples clientes simultáneos, sin bloqueo del servidor
- [x] Servidor apagado durante una consulta desde el cliente — mensaje
      claro, sin cierre inesperado de la aplicación

---

## Uso de Inteligencia Artificial

*(pendiente — se completa según el formato acordado por el equipo)*

---

## Autores

- Jean Carlo Andrade Rojas
- Jason Gabriel Flores Reyes
