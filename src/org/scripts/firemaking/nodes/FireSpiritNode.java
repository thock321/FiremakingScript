package org.scripts.firemaking.nodes;

import org.powerbot.core.script.job.Task;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.interactive.NPCs;
import org.powerbot.game.api.util.Filter;
import org.powerbot.game.api.wrappers.interactive.NPC;

public class FireSpiritNode extends Node {
	
	private NPC fireSpirit;

	@Override
	public boolean activate() {
		fireSpirit = NPCs.getNearest(new Filter<NPC>() {

			@Override
			public boolean accept(NPC arg0) {
				return arg0.getName().toLowerCase().contains("fire spirit");
			}
			
		});
		return fireSpirit != null;
	}

	@Override
	public void execute() {
		if (fireSpirit != null) {
			fireSpirit.interact("Collect-reward");
			Task.sleep(1500, 2000);
		}
	}

}
