# M06-UF2-A03

## Funcionalidades

El programa ofrece diferentes funcionalidades para interaccionar con una bdd:

### 1. Ayuda (`help` o `1`)
Muestra un mensaje de ayuda con la lista de opciones disponibles en el programa.

```
> 1
Opciones disponibles:
    1 - ayuda     -> muestra este mensaje de error
    2 - crear     -> inserta un nuevo elemento en la bdd
    3 - mostrar   -> muestra por pantalla elementos de la bdd
    4 - modificar -> modifica un elemento de la bdd
    5 - borrar    -> elimina un elemento de la bdd
    6 - salir     -> termina la ejecución actual
```

### 2. Insertar Datos (`crear` o `2`)
Permite añadir nuevos Aeropuertos, Aviones, Pilotos o VUelos.
Para poder crearlos el usuario debe proporcionar los siguientes datos:

**Aeropuerto**:
- Ciudad

**Avion**:
- Modelo
- Capacidad

**Piloto**:
- Nombre
- Pasaporte
- Teléfono

**Vuelo**:
- Origen
- Destino
- Avion
- Piloto


Ejemplo **creacion** Aeropuerto:

```
> 2
¿Qué elemento quieres crear? (Aeropuerto, Avion, Piloto, Vuelo)
Si quieres terminar de crear escribe 'salir'
> Aeropuerto

Rellena la siguiente información para poder crear el aeropuerto
¿En que ciudad se encuentra el aeropuerto?
Londres

¡Aeropuerto creado con éxito!
Aeropuerto [
    ID: 3
    Ciudad: Londres
]
```

### 3. Mostrar Datos (`mostrar` o `3`)
Proporciona diferentes métodos para consultar datos de la bdd:

- **Búsqueda por ID:** Muestra los datos de una de las entidades según su ID.
- **Búsqueda por Atributos:** Muestra una lista de los datos de una entidad segun algun de sus atributos
- **Mostrar Todos:** Muestra una lista de todas las filas existentes de una entidad

Ejemplo **mostrar** Avion:

```
> 3
¿Qué elemento quieres mostrar? (Aeropuerto, Avion, Piloto, Vuelo)
Si quieres terminar de mostrar escribe 'salir'
> Avion
    Específica método de búsqueda:
        1 - Búsqueda por ID
        2 - Búsqueda por modelo
        3 - Búsqueda por capacidad
        4 - Muestra todos los aviones
        5 - Muestra la capacidad de todos los aviones juntos
> 4

Avion/es encontrado!
Avion [
    ID: 1
    Modelo: RX-18
    Capacidad: 150
]
```

### 4. Modificar Datos (`modificar` o `4`)
Permite modificar los atributos de una de las entidades.

Ejemplo **modificar** Piloto:

```
> 4

¿Qué elemento quieres modificar? (Aeropuerto, Avion, Piloto, Vuelo)
Si quieres terminar de modificar escribe 'salir'
> Piloto

Rellena la siguiente información para poder modificar el piloto
¿Cual es el id del piloto que quieres modificar?
1

Datos actuales - Piloto [
    ID: 1
    Nombre: Manolo
    Pasaporte: A1234567A
    Telefono: 612345678
]

¿Cual es el nuevo nombre del piloto?
Manuel
¿Cual es el nuevo pasaporte del piloto?
A7654321A
¿Cual es el nuevo telefono del piloto?
687654321

¡Piloto modificado con éxito!
Piloto [
    ID: 1
    Nombre: Manuel
    Pasaporte: A7654321A
    Telefono: 687654321
]
```

### 5. Borrar Datos (`borrar` o `5`)
Elimina una fila de alguna de las entidades. El usuario debe proporcionar el ID de la fila que desea eliminar.

```
> 5

¿Qué elemento quieres borrar? (Aeropuerto, Avion, Piloto, Vuelo)
Si quieres terminar de borrar escribe 'salir'
> Vuelo

Rellena la siguiente información para poder eliminar el vuelo
¿Cual es el id del vuelo que quieres eliminar?
2

¡Vuelo eliminado con éxito!
```

### 6. Salir (`salir` o `6`)
Termina la ejecución del programa.