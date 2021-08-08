package Interface;

import java.io.IOException;
import java.util.List;

public  interface  IFile<T>{
    List<T> GetCsvDataToList(Class<T> pClass) throws IOException, InstantiationException, IllegalAccessException, NoSuchFieldException;
}