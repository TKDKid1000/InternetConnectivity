package io.github.tkdkid1000;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CSV {

	public void append(String[] content, String filename) {
		List<String[]> data = load(filename);
		data.add(content);
		save(data, filename);
	}
	
	public void insert(String[] content, String filename, int line) {
		List<String[]> data = load(filename);
		data.add(line, content);
		save(data, filename);
	}
	
	public List<String[]> load(String filename) {
		List<String[]> data = new ArrayList<String[]>();
		File f = new File(filename);
		String content = "";
		try {
			Scanner scan = new Scanner(f);
			while (scan.hasNextLine()) {
				content = content + scan.nextLine() + "\n";
			}
			for (String line : content.split("\n")) {
				data.add(line.split(","));
			}
			scan.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return data;
	}
	
	public void save(List<String[]> content, String filename) {
		try {
			FileWriter fw = new FileWriter(filename);
			String csv = "";
			for (String[] line : content) {
				String csvline = "";
				for (String s : line) {
					csvline = csvline + s + ",";
				}
				csvline = csvline.substring(0, csvline.length()-1);
				csv = csv + csvline + "\n";
			}
			fw.write(csv);
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
