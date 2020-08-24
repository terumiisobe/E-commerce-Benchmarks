# E-commerce-Benchmarks
This repository contains th two e-commerce benchmarks that will be used for the SOAP and REST performance comparison analysis.

## Setting up
You will need to install the following software:
- Eclipse (2019-09)
- SoapUI (5.6.0)
- DBeaver (7.1.15)
- Postman

### Server
Download WildFly 18.0.0 at https://www.wildfly.org/downloads/. Configure the datasource and driver in standalone/configuration/standalone.xml or copy it from this repository.

### Database
I use MySQL 8 as the database management system for this project.
Download the jar file at https://mvnrepository.com/artifact/mysql/mysql-connector-java/8.0.21 and add it into the server's directory path modules/system/layers/base/com/mysql/main.
In the main folder add the file module.xml.

Clone this repository and add one of the versions of **bookstore** (Soap or Rest) to your workspace.
