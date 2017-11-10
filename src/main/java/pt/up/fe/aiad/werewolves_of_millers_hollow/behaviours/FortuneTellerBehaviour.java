package pt.up.fe.aiad.werewolves_of_millers_hollow.behaviours;

import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Stream;

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

		// if (notKnownPlayers.isEmpty()) {
		// myAgent.sendMessage(ACLMessage.CONFIRM, "OK", Moderator.NAME);
		// return;
		// }

	}

	private void nightAction(ACLMessage msg) {
		getRandomNotKnownPlayerName().ifPresent(players -> {
			String playerName = msg.getSender().getLocalName();
			if (msg.getPerformative() == ACLMessage.INFORM && msg.getContent().equals(ModeratorBehaviour.NIGHT)) {
				myAgent.sendMessage(ACLMessage.REQUEST, playerName, Moderator.NAME);
			}
			if (msg.getPerformative() == ACLMessage.REQUEST) {
				String type = msg.getContent();
				myAgent.players.put(playerName, PlayerTypes.valueOf(type));
				myAgent.sendMessage(ACLMessage.CONFIRM, "OK", Moderator.NAME);
			}
		});
	}

	private Optional<String> getRandomNotKnownPlayerName() {
		Stream<String> notKnownPlayers = myAgent.players.keySet().stream()
				.filter(key -> Objects.isNull(myAgent.players.get(key)));
		int randomSkip = new Random().nextInt((int) (notKnownPlayers.count() - 1));
		return notKnownPlayers.skip(randomSkip).findFirst();
	}

	@Override
	public boolean done() {
		return false;
	}

}
