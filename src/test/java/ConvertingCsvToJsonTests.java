import Containers.GsonContainer;
import Generic.TypeOfCsv;
import Model.Person;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.List;

public class ConvertingCsvToJsonTests {
	private List<Person> data;

	@BeforeTest
	public void TestSetup() throws Exception {
		data = new TypeOfCsv<Person>()
						.WithDelimiter(",")
						.WithFileName("PersonData")
						.GetCsvDataToList(Person.class);
	}

	@Test
	public void TestOutputAsJson() {
		String json = GsonContainer.Create().toJson(data);
		System.out.println(json);
	}
}
