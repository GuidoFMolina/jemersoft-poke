
````markdown
# Pokémon API

A Spring Boot REST API to fetch Pokémon data from the official [PokeAPI](https://pokeapi.co/), store it locally in a PostgreSQL database, and retrieve stored Pokémon.

---

## Technologies Used

- **Java 17**
- **Spring Boot 3.x**
- **Spring Data JPA** (Hibernate ORM)
- **PostgreSQL** (Database)
- **RestTemplate** (For external API consumption)
- **Swagger / OpenAPI** (API documentation)
- **JUnit 5 & Mockito** (Unit testing)
- **Lombok** (For reducing boilerplate code)
- **SLF4J / Logback** (Logging)

---

## Setup Instructions

1. **Database Configuration**

   Update `src/main/resources/application.properties` with your PostgreSQL connection details:

   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/pokemon_db
   spring.datasource.username=your_db_username
   spring.datasource.password=your_db_password
   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.show-sql=true
   spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
````

2. **Build and Run**

   Build the project with Maven:

   ```bash
   mvn clean install
   ```

   Run the Spring Boot application:

   ```bash
   mvn spring-boot:run
   ```

3. **Swagger UI**

   Access API documentation and test endpoints at:

   ```
   http://localhost:8080/swagger-ui.html
   ```

---

## API Usage

### Store Pokémon by Name

**Request:**

```
POST /pokemon/{name}
```

* `name` (path variable): The Pokémon name (case-insensitive)

**Response:**

```json
{
  "message": "Pokémon saved successfully",
  "data": {
    "id": 1,
    "name": "pikachu",
    "imageUrl": "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/25.png",
    "weight": 60,
    "types": ["electric"],
    "abilities": ["static", "lightning-rod"]
  }
}
```

**Possible Errors:**

* 400 Bad Request — Validation error or invalid Pokémon name
* 404 Not Found — Pokémon not found in PokeAPI
* 409 Conflict — Pokémon already exists in the database

---

### Get All Stored Pokémon

**Request:**

```
GET /pokemon
```

**Response:**

```json
[
  {
    "id": 1,
    "name": "pikachu",
    "imageUrl": "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/25.png",
    "weight": 60,
    "types": ["electric"],
    "abilities": ["static", "lightning-rod"]
  },
  {
    "id": 2,
    "name": "charmander",
    "imageUrl": "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/4.png",
    "weight": 85,
    "types": ["fire"],
    "abilities": ["blaze", "solar-power"]
  }
]
```

---

## Testing

* Unit tests are implemented using **JUnit 5** and **Mockito**.
* Run tests with:

```bash
mvn test
```

---

## Notes

* The API fetches Pokémon data live from [PokeAPI](https://pokeapi.co/) and caches it locally.
* Input names are case-insensitive and validated for proper format.
* Swagger UI provides interactive documentation and API testing capability.

---

## License

MIT License

---

Feel free to contribute or open issues for improvements and bug fixes.

```
```
