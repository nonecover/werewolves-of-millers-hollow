package pt.up.fe.aiad.werewolves_of_millers_hollow.common;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import pt.up.fe.aiad.werewolves_of_millers_hollow.behaviours.VillagerBehaviour;
import pt.up.fe.aiad.werewolves_of_millers_hollow.behaviours.WerewolfBehaviour;

public enum PlayerTypes {
	VILLAGER, WEREWOLF;

	private final static Map<PlayerTypes, Function<Agent, Behaviour>> typesToBehaviours = new HashMap<>();
	static {
		typesToBehaviours.put(VILLAGER, agent -> new VillagerBehaviour(agent));
		typesToBehaviours.put(WEREWOLF, agent -> new WerewolfBehaviour(agent));
	}

	// lazy initialization
	public Behaviour getBehaviour(Agent agent) {
		return typesToBehaviours.get(this).apply(agent);
	}
}
