### Enchanged Console CRUD application v 3.0

#### Used technologies:

##### JDK version 15
##### Flyway
##### JUnit
##### Mockito
##### Hibernate

##### Database used:
PostgreSQL

##### To launch the application, type in the command prompt:
mvn flyway:migrate

javac -sourcepath ./src -d bin src/main/java/com/taorusb/consolecrunduseshibernate/Main.java

java -classpath ./bin com.taorusb.consolecrunduseshibernate.Main

Used design patterns:
Chain of responsability, Facade, Singleton

##### Object data is stored in tables:

writers, posts, regions

##### The query command has a similar sql structure (commands are processed regardless of the case of letters).
##### Request structure:
[operation type] [model type] [arguments]

##### Where operation type:
select all, update [model type] where, insert into, delete from [model type] where

##### Where model type:
writers, posts, regions

##### Where arguments:
##### writers:
select - none, update - id= firstname= lastname= regionId= , insert - firstname= lastname= regionId= , delete - id=

##### posts:
select - writerid= , update - id= content= , insert - writerid= content= , delete - id=

##### regions:
select - none , update - id= name= , insert - name= , delete - id=