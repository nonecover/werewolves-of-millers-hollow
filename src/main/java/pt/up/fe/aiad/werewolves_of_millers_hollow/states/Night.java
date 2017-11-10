package pt.up.fe.aiad.werewolves_of_millers_hollow.states;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import jade.lang.acl.ACLMessage;
import pt.up.fe.aiad.werewolves_of_millers_hollow.agents.Moderator;
import pt.up.fe.aiad.werewolves_of_millers_hollow.common.PlayerTypes;

class Night implements Function<Moderator, States> {
	Gson gson = new GsonBuilder().create();

	private int playerCounter = 0;
	private boolean contructorFired = false;

	private void contructor(Moderator myAgent) {
		if (contructorFired)
			return;
		contructorFired = true;
		playerCounter = myAgent.players.size();
	}

	@Override
	public States apply(Moderator myAgent) {
		contructor(myAgent);
		ACLMessage msg = myAgent.blockingReceive();
		if (msg.getPerformative() == ACLMessage.CONFIRM) {
			playerCounter--;
		}

		if (msg.getPerformative() == ACLMessage.REQUEST
				&& myAgent.players.get(msg.getSender().getLocalName()).equals(PlayerTypes.FORTUNE_TELLER)) {
			String player = msg.getContent();
			ACLMessage reply = msg.createReply();
			String type = myAgent.players.get(player).name();
			reply.setContent(type);
			myAgent.send(reply);
			System.out.println("moderator -> Fortune Teller: " + player + " is the " + type);
		}

		if (msg.getPerformative() == ACLMessage.REQUEST
				&& myAgent.players.get(msg.getSender().getLocalName()).equals(PlayerTypes.WEREWOLF)) {
			List<String> werewolfesNames = myAgent.players.keySet().stream()
					.filter(key -> myAgent.players.get(key).equals(PlayerTypes.WEREWOLF)
							&& !key.equals(msg.getSender().getLocalName()))
					.collect(Collectors.toList());

			ACLMessage reply = msg.createReply();
			reply.setContent(gson.toJson(werewolfesNames));
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
