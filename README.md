# Recetario

# Comandos

```
docker compose up
docker exec -it recetario-db-1 mysql -p recetario
./mvnw spring-boot:run
./mvnw test
```

# Desarrollo

## Problemas

### Ingredientes

Los ingredientes son un problema, porque son muchos y todos los usuarios deberían poder verlos.
Con una sola tabla, tendría que añadirlos todos con un script o a mano con usuario admin. Eso es MUCHO trabajo.
Puedo añadir una subclase `IngredienteUsuaraio`, de forma que cada usuario pueda añadir sus propios ingredientes.
A la hora de crear una receta, se debe poder elegir entre todos los ingredientes globas y del usuario que la crea.
El problema de esto es que un usuario podría colapsar la BD creando ingredientes indefinidamente, por eso se deben
limitar el número que puede crear cada usuario.

Se creará una tarea automática que, periodicamente, analice los ingredientes de todos los usuarios y, si existen
unos cuantos repetidos entre varios usuarios, crea un ingrediente global a partir de éstos y eliminia los de usuario,
liberando espacio tanto para la DB como para los usuarios.

También se debe añadir una pestaña para Admins que permita crear ingredientes globales directamente.