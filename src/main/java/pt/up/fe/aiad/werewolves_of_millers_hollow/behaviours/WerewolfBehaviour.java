package pt.up.fe.aiad.werewolves_of_millers_hollow.behaviours;

import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import pt.up.fe.aiad.werewolves_of_millers_hollow.agents.Moderator;

public class WerewolfBehaviour extends BaseBehaviour {
	private static final long serialVersionUID = 754398824446122467L;

	public WerewolfBehaviour(Agent agent) {
		super(agent);
		System.out.println(agent.getLocalName() + ": I am werewolf");
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
		// TODO Auto-generated method stub
		return false;
	}

}
