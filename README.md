## Build & Deploy App:
From the command line within the project directory run the following commands:
1. `mvn install`
2. `docker-compose -f retail-discounts-services.yaml up`

## Testing
From the command line within the project directory run `mvn test jacoco:report`.
Coverage report will be generated in a file called index.html and can be found within `\target\site\jacoco` directory.

## Endpoints
To call the endpoints, use Postman and connect to `http://localhost:8080`.
You can import the provided collection that contains requests for all the endpoints.

Note: To call the get payable discount, first will need to make a call to post signup user and post item to save the user and items info.
