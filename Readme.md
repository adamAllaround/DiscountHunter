# Discount Hunter Application
This application's goal is to examine web pages searching for changes in product prices. 
It consists of few main parts: 
* A REST API allowing users to add new Web Pages for tracking and reading the prices which are displayed on the web page
* A Batch job to run periodically, download page contents, extract prices and assert if any changes to prices might have happened
* An Email service notifying users of changing prices to their tracked pages. 

## Here are the important Features/Functionalities/Frameworks which were exercised in this application
* Securing REST Api [with Spring Security](https://allaroundjava.com/securing-rest-api-with-spring-security/)
* [Integration Testing](https://github.com/adamAllaround/DiscountHunter/tree/master/AcceptanceTest/src/test/java/com/allaroundjava/rest) Secure REST Api
* [Buildinig a batch job](https://github.com/adamAllaround/DiscountHunter/tree/master/Application/src/main/java/com/allaroundjava/batch) with Spring Batch
* Integrating applications with Active MQ (to be done)

## Running the application
After you clone the source code from Github, go into the main application folder called DoctorBooking and run maven install
to generate an executable jar. 
```
mvn clean install
```
Run an in-memory h2 database from your .m2 folder. You should find a jar file in .m2\repository\com\h2database\h2\1.4.197\
```
java -jar h2-1.4.197.jar
```
Start the database as a Generic H2 Server

Run the application jar file with 
```
$ java -jar DiscountHunter/Application/target/Application-0.1-SNAPSHOT-exec.jar
```