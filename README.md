# Backend Setup (Spring Boot)

This folder contains the backend of the project, built with **Spring Boot**.

## Requirements

- **Java 17+**
  - Make sure you have JDK 11 or higher installed.
- **Maven 3.x+**
  - The project uses Maven for dependency management and building the application.
- **H2 Database**
  - This project uses an embedded H2 database for development and testing.

## Setup

## Setup

To run the backend, follow the steps below:

1. Install Java 17.x and Maven if you haven't already.
2. Clone the repository and navigate to the `backend` directory:
   
   ```bash
   cd backend
   ```

3. Configure the Database.

    The application uses H2 as the database. By default, the H2 database will be used in the src/main/resources/application.properties file. You can configure it if needed, but H2 is embedded, and no additional configuration is required for development.

4. Install dependencies:

   ```bash
    mvn clean install
   ```
5. Run the Spring Boot application:

   ```bash
    mvn spring-boot:run
   ```
The backend will now be running at http://localhost:8080.

## API Endpoints

    - GET /api/notes: Get all notes
    - GET /api/notes/{id}: Get a note by ID
    - POST /api/notes: Create a new note
    - PUT /api/notes/{id}: Update a note
    - DELETE /api/notes/{id}: Delete a note
    - GET /api/notes/active: Get all active notes
    - GET /api/notes/archived: Get all archived notes
    - PUT /api/notes/{id}/archive: Archive a note
    - PUT /api/notes/{id}/unarchive: Unarchive a note

## Testing the Backend
To ensure everything works as expected, you can run unit tests provided with the project

   ```bash
    mvn test
   ```

## Troubleshooting

If the backend doesn't start, ensure that the port 8080 is not in use by another process.
If you're facing issues related to database connections, make sure the H2 console is accessible and configured correctly.