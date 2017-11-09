package pt.up.fe.aiad.werewolves_of_millers_hollow.states;

import java.util.function.Function;

import jade.lang.acl.ACLMessage;
import pt.up.fe.aiad.werewolves_of_millers_hollow.agents.Moderator;
import pt.up.fe.aiad.werewolves_of_millers_hollow.behaviours.ModeratorBehaviour;

class GameStart implements Function<Moderator, States> {
	private int playerCounter = 8;

	private boolean contructorFired = false;

	private void contructor(Moderator myAgent) {
		if (contructorFired)
			return;
		contructorFired = true;
	}

	@Override
	public States apply(Moderator myAgent) {
		contructor(myAgent);
		ACLMessage msg = myAgent.blockingReceive();
		if (msg.getPerformative() == ACLMessage.CONFIRM) {
			playerCounter--;
		}
		if (playerCounter == 0) {
			System.out.println("its night");
			playerCounter = myAgent.players.size();
			myAgent.sendToAll(ACLMessage.INFORM, ModeratorBehaviour.NIGHT, "player");
			return States.NIGHT;
		}
		return States.GAME_START;
	}
}
