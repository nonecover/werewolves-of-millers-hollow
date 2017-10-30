package pt.up.fe.aiad.werewolves_of_millers_hollow.behaviours;

import java.util.Random;

import jade.lang.acl.ACLMessage;
import pt.up.fe.aiad.werewolves_of_millers_hollow.agents.Moderator;
import pt.up.fe.aiad.werewolves_of_millers_hollow.agents.Player;
import pt.up.fe.aiad.werewolves_of_millers_hollow.common.PlayerTypes;

public class FortuneTellerBehaviour extends BaseBehaviour {
	private static final long serialVersionUID = 8492835806340589393L;
	Player myAgent;

	public FortuneTellerBehaviour(Player agent) {
		super(agent);
		System.out.println(agent.getLocalName() + ": I am fortune teller");
		myAgent = agent;
		agent.players.remove(agent.getLocalName());
	}

	@Override
	public void action() {
		ACLMessage msg = this.myAgent.blockingReceive();
		int random = new Random().nextInt(myAgent.players.size());

		String playerName = (String) myAgent.players.keySet().toArray()[random];
		if (msg.getPerformative() == ACLMessage.INFORM && msg.getContent().equals(ModeratorBehaviour.NIGHT)) {

			sendMessage(ACLMessage.REQUEST, playerName, Moderator.NAME);
		}
		if (msg.getPerformative() == ACLMessage.REQUEST) {
			String type = msg.getContent();
			myAgent.players.put(playerName, PlayerTypes.valueOf(type));
			sendMessage(ACLMessage.CONFIRM, "OK", Moderator.NAME);
		}
	}

	@Override
	public boolean done() {
		return false;
	}

}
