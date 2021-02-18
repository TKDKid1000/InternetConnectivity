package io.github.tkdkid1000;

import java.util.Timer;
import java.util.TimerTask;

public class Main {

	public static void main(String[] args) {
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
