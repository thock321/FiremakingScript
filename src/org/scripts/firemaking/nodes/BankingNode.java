package org.scripts.firemaking.nodes;

import org.powerbot.core.script.job.Task;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.interactive.NPCs;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Bank;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.util.Filter;
import org.powerbot.game.api.wrappers.interactive.NPC;
import org.powerbot.game.api.wrappers.node.Item;
import org.powerbot.game.api.wrappers.node.SceneObject;
import org.scripts.firemaking.FiremakingScript;
import org.scripts.firemaking.Paint;
import org.scripts.firemaking.State;

public class BankingNode extends Node {

	@Override
	public boolean activate() {
		if (Inventory.getItems() != null && FiremakingScript.getLogToBurn().getLogId() < 1) {
			for (Item item : Inventory.getItems()) {
				if (item.getName().toLowerCase().contains(FiremakingScript.getLogToBurn().getLogName().toLowerCase()) && 
						item.getName().toLowerCase().contains("log")) {
					FiremakingScript.getLogToBurn().setLogId(item.getId());
					break;
				}
			}
		}
		return FiremakingScript.getLogToBurn().getLogId() < 1 || Inventory.getItems() == null || !Inventory.contains(FiremakingScript.getLogToBurn().getLogId());
	}
	
	private static class BankEntity {
		Object bankEntity;
		double distance;
		
		private BankEntity(Object bankEntity, double distance) {
			this.bankEntity = bankEntity;
			this.distance = distance;
		}
	}
	
	private SceneObject getBank(SceneObject[] banks) {
		BankEntity[] bankz = new BankEntity[banks.length];
		for (int i = 0; i < banks.length; i++) {
			bankz[i] = new BankEntity(banks[i], Calculations.distance(banks[i], Players.getLocal()));
		}
		BankEntity closestBank = new BankEntity(null, Double.MAX_VALUE);
		for (BankEntity bank : bankz) {
			if (bank.distance < closestBank.distance)
				closestBank = bank;
		}
		return (SceneObject) closestBank.bankEntity;
	}
	
	private NPC getBank(NPC[] banks) {
		BankEntity[] bankz = new BankEntity[banks.length];
		for (int i = 0; i < banks.length; i++) {
			bankz[i] = new BankEntity(banks[i], Calculations.distance(banks[i], Players.getLocal()));
		}
		BankEntity closestBank = new BankEntity(null, Double.MAX_VALUE);
		for (BankEntity bank : bankz) {
			if (bank.distance < closestBank.distance)
				closestBank = bank;
		}
		return (NPC) closestBank.bankEntity;
	}

	@Override
	public void execute() {
		SceneObject bank = getBank(SceneEntities.getLoaded(new Filter<SceneObject>() {

			@Override
			public boolean accept(SceneObject arg0) {
				return arg0 != null && arg0.getDefinition() != null && arg0.getDefinition().getName().toLowerCase().contains("bank")
						&& Calculations.distance(arg0, Players.getLocal()) < 30;
			}
			
		}));
		NPC banker = null;
		if (bank == null) {
			banker = getBank(NPCs.getLoaded(new Filter<NPC>() {
				
				@Override
				public boolean accept(NPC arg0) {
					if (arg0 != null && arg0.getName().toLowerCase().contains("bank") && Calculations.distance(arg0, Players.getLocal()) < 30) {
						return true;
					}
					return false;
				}
				
			}));
		}
		if (!Bank.isOpen()) {
			if (Calculations.distance(bank != null ? bank : banker, Players.getLocal().getLocation()) > 10) {
				
				Paint.setState(State.WALKING_TO_BANK);
				if (!Walking.isRunEnabled())
					Walking.setRun(true);
				if (Calculations.distance(bank != null ? bank : banker, Players.getLocal()) < 20) {
					Camera.turnTo(bank != null ? bank : banker);
					Task.sleep(800, 1200);
				}
				if (bank != null) {
					Walking.walk(bank);
				} else if (banker != null) {
					Walking.walk(banker);
				}
				while (Players.getLocal().isMoving()) {
					Task.sleep(20);
				}
			} else {
				Paint.setState(State.OPENING_BANK);
				if (bank != null) {
					if (!bank.isOnScreen()) {
						Camera.turnTo(bank);
						Task.sleep(200, 400);
					} else {
						Bank.open();
					}
				} else if (banker != null) {
					if (!banker.isOnScreen()) {
						Camera.turnTo(banker);
						Task.sleep(200, 400);
					} else {
						Bank.open();
					}
				}
			}
		} else {
			if (FiremakingScript.getLogToBurn().getLogId() < 1) {
				for (Item item : Bank.getItems()) {
					if (item.getName().toLowerCase().contains(FiremakingScript.getLogToBurn().getLogName().toLowerCase()) && 
							item.getName().toLowerCase().contains("log")) {
						FiremakingScript.getLogToBurn().setLogId(item.getId());
						break;
					}
				}
			}
			Paint.setState(State.BANKING);
			if (Inventory.getCount() > 0) {
				Bank.depositInventory();
			}
			if (Bank.getItem(FiremakingScript.getLogToBurn().getLogId()) == null) {
				FiremakingScript.getInstance().shutdown();
			} else {
				Bank.withdraw(FiremakingScript.getLogToBurn().getLogId(), 28);
				Task.sleep(100, 150);
				Paint.setState(State.CLOSING_BANK);
				Bank.close();
			}
		}
	}
	
	

}
