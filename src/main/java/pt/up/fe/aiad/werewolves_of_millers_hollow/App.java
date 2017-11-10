package pt.up.fe.aiad.werewolves_of_millers_hollow;

import jade.wrapper.StaleProxyException;
import pt.up.fe.aiad.werewolves_of_millers_hollow.common.PlayerTypes;

public class App {
	public static void main(String[] args) throws StaleProxyException {
		// String[] services = { "-gui" };
		// Boot.main(services);
		

		
		
		
		GameBuilder.create()
		.addPlayer("player1", PlayerTypes.WEREWOLF)
		.addPlayer("player2", PlayerTypes.WEREWOLF)
		.addPlayer("player3", PlayerTypes.FORTUNE_TELLER)
		.addPlayer("player4", PlayerTypes.VILLAGER)
		.addPlayer("player5", PlayerTypes.VILLAGER)
		.addPlayer("player6", PlayerTypes.WEREWOLF)
		.addPlayer("player7", PlayerTypes.WEREWOLF)
		.addPlayer("player8", PlayerTypes.VILLAGER)
		.startGame();
	}
}
