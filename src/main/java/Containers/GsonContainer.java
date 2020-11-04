package Containers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonContainer {
	public static Gson Create() {
		GsonBuilder builder = new GsonBuilder();
		return builder.create();
	}
}
