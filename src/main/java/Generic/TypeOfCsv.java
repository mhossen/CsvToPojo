package Generic;

import Interface.ICsvType;
import Interface.IDelimiter;
import Interface.IFile;
import org.apache.commons.math3.exception.NullArgumentException;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TypeOfCsv<T> implements ICsvType, IDelimiter, IFile<T> {
  private static final String USER_DIRECTORY = System.getProperty("user.dir");
  private static final String RESOURCE_FOLDER = "\\src\\main\\resources\\";
  private static final String CSV_EXTENSION = ".csv";
  private String _path;
  private Object _delimiter;

  /**
   * Provide name of CSV file
   *
   * @param fileName - String value of file name
   * @return this
   */
  public IFile<T> WithFileName(String fileName) {
    this._path = USER_DIRECTORY + RESOURCE_FOLDER + fileName + CSV_EXTENSION;
    return this;
  }

  /**
   * Set character that separates the column value from csv file
   *
   * @param delimiter - char
   * @return this
   */
  public IDelimiter WithDelimiter(char delimiter) {
    this._delimiter = delimiter;
    return this;
  }

  /**
   * Set character(s) that separates the column value from csv file
   *
   * @param delimiter - character string
   * @return this
   */
  public IDelimiter WithDelimiter(String delimiter) {
    this._delimiter = delimiter;
    return this;
  }

  /**
   * This is where the reflection magic happens. As long as the CSV header matches the T class fields it will set the value
   *
   * @param pClass - Pass in a Plain Old Java Object class
   * @return List<T></T>
   * @throws IOException - input output exception
   */
  public List<T> GetCsvDataToList(Class<T> pClass) throws IOException, InstantiationException, IllegalAccessException, NoSuchFieldException {
    // Gets each line from CSV file as string
    List<String> rawData = ReadFileContentAsLines();

    // Getting the first row as header and then splitting each column with the delimiter
    String[] headers = rawData.stream().findFirst().isPresent()
            ? rawData.stream().findFirst().get().split(String.valueOf(_delimiter))
            : null;

    // Setting a total count for the header as total number of column
    if (headers == null) {
      throw new NullArgumentException();
    }

    int columnCount = headers.length;

    // Selecting rest of the data minus first row starting with row 2 - end of the csv file
    List<String> dataRows = rawData.stream().skip(1).collect(Collectors.toList());

    // placeholder of generic List of T representing the POJO class that will be passed in.
    List<T> genericTList = new ArrayList<>();

    // Looping through each data rows after starting with row 2
    for (String row : dataRows) {
      // Splitting each row with the delimiter
      String[] columnValues = row.split(String.valueOf(_delimiter));
      // Since JAVA doesn't allow you to new up T like new T(), so using master Class<T> to create an instance of the class passed in at run time, like Person.class
      T t1 = pClass.newInstance();

      // Looping throw number of column based on the first header row of csv file
      for (int i = 0; i < columnCount; i++) {
        // Getting the class that I am passing into the method signature
        Class<?> tClass = t1.getClass();
        // Getting a filed based csv header match
        // Field is case-sensitive so need to ensure that header is same exact casing
        Field field = tClass.getField(headers[i]);
        // This is where the value is being set, t1 representing POJO class and SetFieldValueType returns the primitive data type for the filed like: String, double, int, etc.
        field.set(t1, SetFieldValueType(field, columnValues[i]));
      }
      // Adding my class to the generic list for each row of data
      genericTList.add(t1);
    }
    // Returning list of my POJO class with values
    return genericTList;
  }

  /**
   * This checks for field data type for the POJO class, if the value is primitive data type like: String, double, int
   * Other implementation can be added but for my example I am only using three data types
   *
   * @param field - single field of class
   * @param value - this value is coming from the csv file
   * @return object so if the
   */
  private Object SetFieldValueType(Field field, Object value) {
    // data type checking here
    if (field.getType().isPrimitive() && (field.getType() == double.class || field.getType() == Double.class)) {
      return Double.valueOf(value.toString());
    }

    if (field.getType().isPrimitive() && (field.getType() == int.class || field.getType() == Integer.class)) {
      return Integer.valueOf(value.toString());
    }

    if (field.getType().isPrimitive() && (field.getType() == boolean.class || field.getType() == Boolean.class)) {
      return Boolean.valueOf(value.toString());
    }

    if (field.getType().isPrimitive() && (field.getType() == float.class || field.getType() == Float.class)) {
      return Float.valueOf(value.toString());
    }

    if (field.getType().isPrimitive() && (field.getType() == String.class)) {
      return String.valueOf(value.toString());
    }

    return value.toString();
  }

  /**
   * POI - implementation of reading file
   *
   * @return each line as a string array from csv file
   * @throws IOException - If file doesn't exist or unable to read file throws Input/Output exception.
   */
  private List<String> ReadFileContentAsLines() throws IOException {
    return Files.readAllLines(Paths.get(_path));
  }
}
