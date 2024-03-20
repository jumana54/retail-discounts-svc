## App
### Build App:
From the command line within the project directory run `mvn install`.
### Deploy App:
1. Run `docker pull mongo:latest` to pull mongodb library in docker if you do not have it already.
2. Run `docker run -d -p 27017:27017 --name retail-discounts-mongodb mongo:latest` to run mongo image in retail-discounts-mongodb container. 
3. Run `docker build -t retail-discounts-svc:1.0 .` to build retail-discounts-svc image.
4. Run `docker run -p 8080:8080 --name retail-discounts-svc --link retail-discounts-mongodb:mongo -d retail-discounts-svc:1.0`
to run retail-discounts-svc image in retail-discounts-mongodb container.

## Testing
From the command line within the project directory run `mvn test jacoco:report`.
Coverage report will be generated in a file called index.html and can be found within `\target\site\jacoco` directory.

## Endpoints
To call the endpoints, use Postman and connect to `http://localhost:8080`.
You can import the provided collection that contains requests for all the endpoints.

Note: To call the get payable discount, first will need to make a call to post signup user and post item to save the user and items info.
