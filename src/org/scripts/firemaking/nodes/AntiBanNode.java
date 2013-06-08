package org.scripts.firemaking.nodes;

import org.powerbot.core.script.job.Task;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.Tabs;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.wrappers.node.SceneObject;
import org.scripts.firemaking.Paint;
import org.scripts.firemaking.State;

public class AntiBanNode extends Node {

	@Override
	public boolean activate() {
		return Paint.getState() == State.ADDING_TO_BONFIRE && Random.nextInt(1, 3) == 1;
	}

	@Override
	public void execute() {
		Paint.setState(State.ANTI_BAN);
		int random = Random.nextInt(0, 100);
		if (random <= 40) {
			int randomCamera = Random.nextInt(1, 3);
			switch (randomCamera) {
			case 1:
				Camera.setAngle(Random.nextInt(0, 359));
				break;
			case 2:
				Camera.setPitch(Random.nextInt(0, 100));
				break;
			case 3:
				Camera.setAngle(Random.nextInt(0, 359));
				Camera.setPitch(Random.nextInt(0, 100));
				break;
			}

		} else if (random <= 80) {
			Mouse.move(Mouse.getX() + Random.nextInt(-50, 50), Mouse.getY() + Random.nextInt(-50, 50));
		} else if (random <= 95) {
			Tabs.STATS.open();
			Mouse.move(704 + Random.nextInt(-10, 10), 384 + Random.nextInt(-10, 10));
			Task.sleep(1000, 2000);
			Tabs.INVENTORY.open();
		} else {
			examineRandomObject();
		}
	}
	
	private void examineRandomObject() {
		SceneObject[] objects = SceneEntities.getLoaded();
		SceneObject randomObject = objects[Random.nextInt(0, objects.length - 1)];
		if (randomObject.isOnScreen()) {
			randomObject.interact("Examine");
		} else {
			examineRandomObject();
		}
	}

}
