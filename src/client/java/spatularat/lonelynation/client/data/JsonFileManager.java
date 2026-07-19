package spatularat.lonelynation.client.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class JsonFileManager {

	private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

	public static void createFile(Path PATH, Object TYPE){

		if (!Files.exists(PATH)) {
			try {
				Files.createDirectories(PATH.getParent());

				try (BufferedWriter writer = Files.newBufferedWriter(PATH)){
					GSON.toJson(TYPE, writer);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static <T> T loadFile(Path PATH, Class<T> TYPE) {

		try (BufferedReader reader = Files.newBufferedReader(PATH)) {
			return GSON.fromJson(reader,TYPE);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void saveFile(Path PATH, Object TYPE) {

		try {
			Files.createDirectories(PATH.getParent());

			try (BufferedWriter writer = Files.newBufferedWriter(PATH)) {
				GSON.toJson(TYPE,writer);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}