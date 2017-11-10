package pt.up.fe.aiad.werewolves_of_millers_hollow.behaviours;

import jade.lang.acl.ACLMessage;
import pt.up.fe.aiad.werewolves_of_millers_hollow.agents.Moderator;
import pt.up.fe.aiad.werewolves_of_millers_hollow.agents.Player;

public class VillagerBehaviour extends BaseBehaviour {
	private static final long serialVersionUID = 8492835806340589393L;
	private Player myAgent;

	public VillagerBehaviour(Player player) {
		super(player);
		this.myAgent = player;
		System.out.println(player.getLocalName() + ": I am villager");
	}

	@Override
	public void action() {
		ACLMessage msg = this.myAgent.blockingReceive();

		if (msg.getContent().equals(ModeratorBehaviour.NIGHT) && msg.getPerformative() == ACLMessage.INFORM) {
			myAgent.sendMessage(ACLMessage.CONFIRM, "OK", Moderator.NAME);
		}
	}

	@Override
	public boolean done() {
		return false;
	}

}
