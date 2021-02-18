package io.github.tkdkid1000;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

public class Commands {

	public void getInternetConnectivity() {
		Yaml yaml = new Yaml();
		InputStream inputStream = this.getClass()
				  .getClassLoader()
				  .getResourceAsStream("config.yml");
		Map<String, Object> config = yaml.load(inputStream);
		List<String[]> csv = new CSV().load((String) config.get("datafile"));
		if (csv.size()>(int) config.get("deleteafter")) {
			csv.remove((int) config.get("deleteafter"));
			new CSV().save(csv, (String) config.get("datafile"));
		}
		long start = System.currentTimeMillis();
		String wifi = "Disconnected";
		try {
			InetAddress add = InetAddress.getByName((String) config.get("router"));
			try {
				if (add.isReachable(10)) {
					wifi = "Connected";
					System.out.println("Internet Connected");
				} else {
					System.out.println("Internet Disconnected");
				}
			} catch (IOException e) {
				System.out.println("Internet Connected");
			}
		} catch (UnknownHostException e) {
			System.out.println("Internet Connected");
		}
		try {
			URL url = new URL("https://google.com");
			URLConnection connection = url.openConnection();
			connection.connect();
			System.out.println("Connected");
			long end = System.currentTimeMillis()-start;
			System.out.println("Response in " + end + " milliseconds.");
			new CSV().insert(new String[] {"Connected", end + "", wifi+"", Utils.getDateTime()}, (String) config.get("datafile"), 1);
		} catch (MalformedURLException e) {
			System.out.println("Not Connected");
			new CSV().insert(new String[] {"Disconnected","-1", wifi+"", Utils.getDateTime()}, (String) config.get("datafile"), 1);
		} catch (IOException e) {
			System.out.println("Not Connected");
			new CSV().insert(new String[] {"Disconnected","-1", wifi+"", Utils.getDateTime()}, (String) config.get("datafile"), 1);
		}
	}
	
	public void sendInfoText() {
		Yaml yaml = new Yaml();
		InputStream inputStream = this.getClass()
				  .getClassLoader()
				  .getResourceAsStream("config.yml");
		Map<String, Object> config = yaml.load(inputStream);
		List<String[]> csv = new CSV().load((String) config.get("datafile"));
		int badping = 0;
		int disconnected = 0;
		for (String[] line : csv) {
			try {
				long ping = Long.parseLong(line[1]);
				if (ping>(int) config.get("badping")) {
					badping++;
				}
			} catch (NumberFormatException e) {
			}
			if (line[0].equalsIgnoreCase("Disconnected")) {
				disconnected++;
			}
		}
		try {
			Gmail.sendEmail((String) config.get("sendto"), "Internet", "There were " + badping + "/" + (csv.size()-2) + " 200+ pings\nThere were " + disconnected + "/" + (csv.size()-2) + " disconnected tests.");
		} catch (FileNotFoundException e) {
			System.out.println("The configuration file does not exist!");
		}
	}
}
