#Build the project
1) Open the Vessels folder in the command prompt
2) Run the command `mvn -U clean install`

#Run the application
`mvn spring-boot:run`

#Test the endpoint
http://localhost:8090/generateReport

#Endpoint Data
Report will contain avgSpeed and totalDistanceTravelled along with the test data to populate in graph