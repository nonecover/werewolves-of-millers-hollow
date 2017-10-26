package pt.up.fe.aiad.werewolves_of_millers_hollow;

import java.util.HashMap;
import java.util.Map;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;
import pt.up.fe.aiad.werewolves_of_millers_hollow.agents.Moderator;
import pt.up.fe.aiad.werewolves_of_millers_hollow.common.PlayerTypes;

public class GameBuilder {

	public static GameBuilder create() {
		return new GameBuilder();
	}

	private final ContainerController containerController;
	private Map<String, PlayerTypes> players = new HashMap<>();

	private GameBuilder() {
		Profile profile = new ProfileImpl(true);
		containerController = jade.core.Runtime.instance().createMainContainer(profile);
	}

	public GameBuilder addPlayer(String name, PlayerTypes playerType) {
		players.put(name, playerType);
		return this;
	}

	public void startGame() throws StaleProxyException {
		Map<String, PlayerTypes>[] arguments = new Map[1];
		arguments[0] = players;

		AgentController moderatorController = containerController.createNewAgent(Moderator.NAME,
				Moderator.class.getName(), arguments);
		moderatorController.start();
	}
}
