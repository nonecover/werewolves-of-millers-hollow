package pt.up.fe.aiad.werewolves_of_millers_hollow.common;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import pt.up.fe.aiad.werewolves_of_millers_hollow.agents.Player;
import pt.up.fe.aiad.werewolves_of_millers_hollow.behaviours.FortuneTellerBehaviour;
import pt.up.fe.aiad.werewolves_of_millers_hollow.behaviours.VillagerBehaviour;
import pt.up.fe.aiad.werewolves_of_millers_hollow.behaviours.WerewolfBehaviour;

public enum PlayerTypes {
	VILLAGER, WEREWOLF, FORTUNE_TELLER;

	private final static Map<PlayerTypes, Function<Agent, Behaviour>> typesToBehaviours = new HashMap<>();
	static {
		typesToBehaviours.put(VILLAGER, agent -> new VillagerBehaviour(agent));
		typesToBehaviours.put(WEREWOLF, agent -> new WerewolfBehaviour(agent));
		typesToBehaviours.put(FORTUNE_TELLER, agent -> new FortuneTellerBehaviour((Player) agent));
	}

	// lazy initialization
	public Behaviour getBehaviour(Agent agent) {
		return typesToBehaviours.get(this).apply(agent);
	}
}
