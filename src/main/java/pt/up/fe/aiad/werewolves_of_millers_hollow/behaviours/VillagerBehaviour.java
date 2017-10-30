package pt.up.fe.aiad.werewolves_of_millers_hollow.behaviours;

import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import pt.up.fe.aiad.werewolves_of_millers_hollow.agents.Moderator;

public class VillagerBehaviour extends BaseBehaviour {
	private static final long serialVersionUID = 8492835806340589393L;

	public VillagerBehaviour(Agent agent) {
		super(agent);
		System.out.println(agent.getLocalName() + ": I am villager");
	}

	@Override
	public void action() {
		ACLMessage msg = this.myAgent.blockingReceive();

		if (msg.getContent().equals(ModeratorBehaviour.NIGHT) && msg.getPerformative() == ACLMessage.INFORM) {
			sendMessage(ACLMessage.CONFIRM, "OK", Moderator.NAME);
		}
	}

	@Override
	public boolean done() {
		return false;
	}

}
