package org.scripts.firemaking.nodes;

import org.powerbot.core.script.job.Task;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.Game;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.util.Filter;
import org.powerbot.game.api.wrappers.node.SceneObject;
import org.powerbot.game.api.wrappers.widget.WidgetChild;
import org.scripts.firemaking.FiremakingScript;
import org.scripts.firemaking.Paint;
import org.scripts.firemaking.State;

public class FiremakingNode extends Node {

	@Override
	public boolean activate() {
		return Game.isLoggedIn() && Inventory.getItem(FiremakingScript.getInstance().getLogToBurn().getLogId()) != null && 
				Players.getLocal().getAnimation() == -1;
	}
	
	private static final WidgetChild ACTION_BAR = Widgets.get(640,4);
	private static final WidgetChild FIREMAKING_OPTIONS = Widgets.get(1179, 0);
	private static final WidgetChild ADD_TO_BONFIRE = Widgets.get(1179, 33);

	@Override
	public void execute() {
		if (ACTION_BAR.visible()) {
			Widgets.get(640,30).interact("Minimise");
			Task.sleep(50, 70);
		}
		switch (FiremakingScript.getInstance().getMethod()) {
		case BONFIRES:
			
			SceneObject bonfire = SceneEntities.getNearest(new Filter<SceneObject>() {
				
				@Override
				public boolean accept(SceneObject arg0) {
					if (arg0.getDefinition() != null && arg0.getDefinition().getName() != null && 
							arg0.getDefinition().getName().toLowerCase().contains("fire")) {
						return true;
					}
					return false;
				}
				
			});
			if (bonfire == null) {
				Paint.setState(State.LIGHTING_FIRE);
			} else {
				Paint.setState(State.GOING_TO_ADD_TO_BONFIRE);
				if (!bonfire.isOnScreen()) {
					Camera.turnTo(bonfire);
					Task.sleep(500, 1000);
				}
				if (!FIREMAKING_OPTIONS.visible()) {
					bonfire.interact("Use");
					if (Calculations.distance(bonfire, Players.getLocal().getLocation()) > 1)
						Task.sleep(1000, 1200);
					while (Players.getLocal().isMoving()) {
						Task.sleep(20);
					};
					Task.sleep(500, 600);
				}
				if (FIREMAKING_OPTIONS.visible()) {
					ADD_TO_BONFIRE.interact("Select");
					Task.sleep(500, 600);
					Paint.setState(State.ADDING_TO_BONFIRE);
					long lastNotIdle = System.currentTimeMillis();
					while (System.currentTimeMillis() - lastNotIdle < 2000 && Inventory.contains(FiremakingScript.getInstance().getLogToBurn().getLogId())) {
						if (Players.getLocal().getAnimation() != -1) {
							lastNotIdle = System.currentTimeMillis();
						}
					}
				}
			}
			break;
		default:
			break;
		
		}
	}
	
	

}
