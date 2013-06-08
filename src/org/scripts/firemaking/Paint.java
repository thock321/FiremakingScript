package org.scripts.firemaking;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.tab.Skills;
import org.powerbot.game.api.util.Timer;
import org.scripts.firemaking.util.Util;


public class Paint {
	
	private static State state;
	
	public static void setState(State state) {
		Paint.state = state;
	}
	
	public static State getState() {
		return state;
	}
	
	private static int startXP;
	
	public static void setStartXP(int startXP) {
		Paint.startXP = startXP;
	}
	
	private static Timer timeRan = new Timer(0);
	
	private static Image cursor = getCursor();
	
	private static Image getCursor() {
		try {
			return ImageIO.read(new URL("http://cur.cursors-4u.net/user/images1/use3.png"));
		} catch (IOException e) {
			return null;
		}
	}
	
	private static int xpPerHour() {
		return (int) ((Skills.getExperience(Skills.FIREMAKING) - startXP)  * 3600000D / timeRan.getElapsed());
	}
	
	public static void setPaint(Graphics gfx) {
		Graphics2D g2d = (Graphics2D) gfx;
		g2d.drawString("Time ran: " + timeRan.toElapsedString(), 13, 100);
		g2d.drawString("XP per hour: " + Integer.toString(xpPerHour()), 13, 115);
		g2d.drawString("XP Gained: " + Integer.toString(Skills.getExperience(Skills.FIREMAKING) - startXP), 13, 130);
		g2d.drawString("State: " + Util.capitalize(state.toString().toLowerCase().replace("_", " ")), 13, 145);
		g2d.drawImage(cursor, Mouse.getX() - 31, Mouse.getY() - 31, null);
	}

}
