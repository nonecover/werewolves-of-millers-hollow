package pt.up.fe.aiad.werewolves_of_millers_hollow.behaviours;

import jade.core.Agent;
import jade.core.behaviours.SimpleBehaviour;

public class WerewolfBehaviour extends SimpleBehaviour {
	private static final long serialVersionUID = 754398824446122467L;

	public WerewolfBehaviour(Agent agent) {
		super(agent);
		System.out.println(agent.getLocalName() + ": I am werewolf");
	}

	@Override
	public void action() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean done() {
		// TODO Auto-generated method stub
		return false;
	}

}
