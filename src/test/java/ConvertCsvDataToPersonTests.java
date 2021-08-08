import Generic.TypeOfCsv;
import Model.Person;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;

public class ConvertCsvDataToPersonTests {
	private List<Person> data;

	@BeforeTest
	public void TestSetup() throws Exception {
		data = new TypeOfCsv<Person>()
						.WithDelimiter(",")
						.WithFileName("PersonData")
						.GetCsvDataToList(Person.class);
	}

	@DataProvider
	private Object[][] TestData() {
		return new Object[][]{{69, 113521.56},
						{70, 95561.39},
						{71, 102948.51},
						{72, 82095.66},
						{73, 105765.63},
						{74, 80026.66},
						{75, 108280.86},
						{76, 112632.04},
						{77, 145927.91},
						{78, 75904.1},
						{79, 85559.85},
						{80, 98954.2},
						{81, 76000.77},
						{82, 59784.9},
						{83, 78406.03},
						{84, 35667.71},
						{85, 65297.49},
						{86, 139053.27},
						{87, 133961.09},
						{88, 125491.09},
						{89, 60027.97},
						{90, 140842.93},
						{91, 123542.12},
						{92, 106589.77}
		};
	}

	@Test(dataProvider = "TestData")
	public void MatchPersonIdWithSalary_IsValid(int id, double salary) throws Exception {
		Assert.assertEquals(
						data.stream().filter(p -> p.Id == id).findFirst().get().Salary, salary);
	}
}
