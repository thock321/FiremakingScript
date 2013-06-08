package org.scripts.firemaking;

import java.awt.Graphics;
import java.util.logging.Logger;

import javax.swing.SwingUtilities;

import org.powerbot.core.event.listeners.PaintListener;
import org.powerbot.core.script.ActiveScript;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.core.script.job.state.Tree;
import org.powerbot.game.api.Manifest;
import org.powerbot.game.api.methods.Game;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.input.Mouse.Speed;
import org.powerbot.game.api.methods.tab.Skills;
import org.powerbot.game.api.util.Random;
import org.scripts.firemaking.nodes.*;

@Manifest(authors = { "Thock321" }, description = "A firemaking script to help you train firemaking.", name = "Thock's Firemaker", version = 1.0)
public class FiremakingScript extends ActiveScript implements PaintListener {
	
	private static FiremakingScript instance;
	
	private static Method method;
	
	public static Method getMethod() {
		return method;
	}
	
	public static void setMethod(Method method) {
		FiremakingScript.method = method;
	}
	
	private static Log logToBurn;
	
	public static Log getLogToBurn() {
		return logToBurn;
	}
	
	public static void setLogToBurn(String logToBurn) {
		FiremakingScript.logToBurn = new Log(logToBurn);
	}
	
	private static boolean active;
	
	public static boolean active() {
		return active;
	}

	public static void setActive(boolean active) {
		FiremakingScript.active = active;
	}
	
	private static Tree tree;
	
	public static synchronized void queueNodes(Node...nodes) {
		tree = new Tree(nodes);
	}
	
	@Override
	public void onStart() {
		instance = this;
		Paint.setState(State.STARTING_UP);
		queueNodes(new BankingNode(), new FiremakingNode(), new FireSpiritNode(), new AntiBanNode());
        Mouse.setSpeed(Speed.NORMAL);
        Paint.setStartXP(Skills.getExperience(Skills.FIREMAKING));
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
            	try {
            		StartupGUI gui = new StartupGUI();
            		gui.setVisible(true);
            	} catch (Exception e) {
            		e.printStackTrace();
            	}
            }
        });
	}
	
	@Override
	public int loop() {
		if (tree == null || logToBurn == null || Game.getClientState() != Game.INDEX_MAP_LOADED)
			return 300;
		if (tree != null) {
			Node node = tree.state();
			if (node != null) {
				tree.set(node);
				getContainer().submit(node);
				node.join();
			}
		}
		return Random.nextInt(50, 150);
	}
	
	@Override
	public void onRepaint(Graphics render) {
		Paint.setPaint(render);
	}
	
	public static FiremakingScript getInstance() {
		return instance;
	}
	
	public Logger getLogger() {
		return log;
	}

}
