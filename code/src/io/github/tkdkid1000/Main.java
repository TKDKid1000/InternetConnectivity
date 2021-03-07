package io.github.tkdkid1000;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Timer;
import java.util.TimerTask;

public class Main {

	public static void main(String[] args) {
		try {
			System.setErr(new PrintStream(new FileOutputStream("error.log")));
		} catch (FileNotFoundException e) {
			try {
				FileWriter fw = new FileWriter("error.log");
				fw.write("create");
				fw.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		Timer timer = new Timer();
		Commands commands = new Commands();
		timer.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {
				commands.getInternetConnectivity();
			}
			
		}, 5000, 5000);
		timer.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {
				commands.sendInfoText();
			}
		}, 15000, 86400000);
	}
}
