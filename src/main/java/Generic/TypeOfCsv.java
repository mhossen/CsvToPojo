package Generic;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

public class TypeOfCsv<T> {
	private static final String USER_DIRECTORY = System.getProperty("user.dir");
	private static final String RESOURCE_FOLDER = "\\src\\main\\resources\\";
	private String _extension;
	private String _path;
	private Object _delimiter;

	public TypeOfCsv<T> WithFileExtension(String fileExtension) {
		this._extension = String.format(".%s", fileExtension);
		return this;
	}

	public TypeOfCsv<T> WithFileName(String fileName) {
		this._path = USER_DIRECTORY + RESOURCE_FOLDER + fileName + _extension;
		return this;
	}

	private List<String> ReadFileContentAsLines() throws IOException {
		return Files.readAllLines(Paths.get(_path));
	}

	public TypeOfCsv<T> WithDelimiter(char delimiter) {
		this._delimiter = delimiter;
		return this;
	}

	public TypeOfCsv<T> WithDelimiter(String delimiter) {
		this._delimiter = delimiter;
		return this;
	}

	public List<T> GetCsvDataList(Class<T> pClass) throws Exception {
		List<String> rawData = ReadFileContentAsLines();
		String[] headers = rawData.stream().findFirst().get().split(String.valueOf(_delimiter));
		int columnCount = headers.length;
		List<String> dataRows = rawData.stream().skip(1).collect(Collectors.toList());
		List<T> genericTList = new ArrayList<>();

		for (String row : dataRows) {
			String[] columnValues = row.split(String.valueOf(_delimiter));
			T t1 = pClass.newInstance();

			for (int i = 0; i < columnCount; i++) {
				Class<?> tClass = t1.getClass();
				Field field = tClass.getField(headers[i]);
				field.set(t1, SetFieldValueType(field, columnValues[i]));
			}
			genericTList.add(t1);
		}
		return genericTList;
	}

	private Object SetFieldValueType(Field field, Object data) {
		if (field.getType().isPrimitive() && (field.getType().getName().contains("double") || field.getType().equals("Double"))) {
			return Double.valueOf(data.toString());
		} else if (field.getType().isPrimitive() && (field.getType().getName().contains("int") || field.getType().equals("Integer"))) {
			return Integer.valueOf(data.toString());
		} else if (field.getType().isPrimitive() && (field.getType().getName().contains("boolean") || field.getType().equals("Boolean"))) {
			return Integer.valueOf(data.toString());
		}
		return data.toString();
	}
}
