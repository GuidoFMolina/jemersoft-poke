
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
   http://localhost:8080/swagger-ui/index.html
   ```

---

## API Usage

### Store Pokémon by Name

**Request:**

```
POST /pokemon/{name}
```

### Explanation for POST `/pokemon/{name}` Endpoint

The `POST /pokemon/{name}` endpoint fetches detailed Pokémon information from the external **PokeAPI** based on the Pokémon name provided in the URL path. Once retrieved, this data is stored in the local database.

When you call this endpoint with a Pokémon name (e.g., `/pokemon/pikachu`), the application performs the following steps:

1. **Fetch data from PokeAPI:**
   The service sends a request to the public PokeAPI (`https://pokeapi.co/api/v2/pokemon/{name}`) to get detailed information about the requested Pokémon.

2. **Map and save data locally:**
   The response from PokeAPI is mapped into the local entity model, including properties like name, image URL, weight, types, and abilities.

3. **Save to local database:**
   The mapped Pokémon entity is saved into the local PostgreSQL database.

4. **Return saved Pokémon data:**
   Finally, the saved Pokémon data is returned as a JSON response with all the relevant details.

This approach ensures that the application always stores consistent and up-to-date Pokémon data by relying on the official external API as the source of truth.

---

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
