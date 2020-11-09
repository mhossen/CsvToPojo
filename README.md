# CSV To POJO
<br/>
This demo project is designed to show how you can use Java Refection to set values to Plain Old Java Object (POJO) classes. I have placed a `PersonData.csv` in the resource folder for ease of access for anyone to access.

## How to Use Generic Class
There are two JAVA test class with in the test folder for working example.
<br/>
```JAVA
// I am passing in Parson object, then setting the separator as comma
// Then passing in the csv file name, and 
// Finally setting Person.class to create a run time instance of person class in the generic T
List<Person> people = new TypeOfCsv<Person>()
               .WithDelimiter(",")
               .WithFileName("PersonData")
               .GetCsvDataToList(Person.class);
```
## What's the benefit of this?
 Now with this generic type, another POJO class can be passed in which represents a different CSV file as long as my header exist, all the data will be collected at runtime.

Second, should there be any need to convert data to JSON it can be also done. In this example, I am using GSON container, but implementation should be similar to other JAVA library like Jackson JSON mapper.
### How to convert to JSON with GSON
```JAVA
   public static Gson Create() {
      GsonBuilder builder = new GsonBuilder();
      return builder.create();
   }

String json = Create().toJson(people);
System.out.println(json);
```
The output would serialized as JSON.

