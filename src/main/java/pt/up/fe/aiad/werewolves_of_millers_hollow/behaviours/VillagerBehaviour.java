package pt.up.fe.aiad.werewolves_of_millers_hollow.behaviours;

import jade.core.Agent;
import jade.core.behaviours.SimpleBehaviour;

public class VillagerBehaviour extends SimpleBehaviour {
	private static final long serialVersionUID = 8492835806340589393L;

	public VillagerBehaviour(Agent agent) {
		super(agent);
		System.out.println(agent.getLocalName() + ": I am villager");
	}

	@Override
	public void action() {

	}

	@Override
	public boolean done() {
		return false;
	}

}
