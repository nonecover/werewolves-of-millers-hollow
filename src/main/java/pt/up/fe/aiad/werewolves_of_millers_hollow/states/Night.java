package pt.up.fe.aiad.werewolves_of_millers_hollow.states;

import java.util.function.Function;

import jade.lang.acl.ACLMessage;
import pt.up.fe.aiad.werewolves_of_millers_hollow.agents.Moderator;
import pt.up.fe.aiad.werewolves_of_millers_hollow.common.PlayerTypes;

class Night implements Function<Moderator, States> {
	private int playerCounter = 0;

	@Override
	public States apply(Moderator myAgent) {
		ACLMessage msg = myAgent.blockingReceive();
		if (msg.getPerformative() == ACLMessage.CONFIRM) {
			playerCounter--;
		}

		if (msg.getPerformative() == ACLMessage.REQUEST
				&& myAgent.players.get(msg.getSender().getLocalName()).equals(PlayerTypes.FORTUNE_TELLER)) {
			String player = msg.getContent();
			ACLMessage reply = msg.createReply();
			reply.setContent(myAgent.players.get(player).name());
			myAgent.send(reply);
		}

		if (playerCounter == 0) {
			System.out.println("its day");
			// playerCounter = players.size();
			// currentState = States.NIGHT;
			// sendToAll(ACLMessage.INFORM, "NIGHT", "player");
		}
		return States.NIGHT;
	}
}
