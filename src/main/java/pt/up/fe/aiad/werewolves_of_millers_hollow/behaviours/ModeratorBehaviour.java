package pt.up.fe.aiad.werewolves_of_millers_hollow.behaviours;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import jade.wrapper.StaleProxyException;
import pt.up.fe.aiad.werewolves_of_millers_hollow.agents.Player;
import pt.up.fe.aiad.werewolves_of_millers_hollow.common.PlayerTypes;
import pt.up.fe.aiad.werewolves_of_millers_hollow.common.States;

public class ModeratorBehaviour extends BaseBehaviour {
	private static final long serialVersionUID = 8492835806340589393L;

	public static String NIGHT = "NIGHT";

	public ModeratorBehaviour(Agent agent) {
		super(agent);
	}

	private Map<String, PlayerTypes> players = new HashMap<>();

	private int playerCounter = 0;

	@Override
	public void onStart() {
		super.onStart();

		players = ((Map<String, PlayerTypes>[]) myAgent.getArguments())[0];
		players.keySet().forEach(player -> createNewPlayer(player));
		playerCounter = players.size();
	};

	private void createNewPlayer(String name) {
		PlayerTypes type = players.get(name);
		Set<String> names = players.keySet();
		Object[] args = { type, names };
		try {
			myAgent.getContainerController().createNewAgent(name, Player.class.getName(), args).start();
		} catch (StaleProxyException e) {
			e.printStackTrace();
		}
	}

	States currentState = States.GAME_START;

	@Override
	public void action() {
		ACLMessage msg = this.myAgent.blockingReceive();
		if (currentState.equals(States.GAME_START)) {
			gameStart(msg);
		} else if (currentState.equals(States.NIGHT)) {
			night(msg);
		}

	}

	private void night(ACLMessage msg) {
		if (msg.getPerformative() == ACLMessage.CONFIRM) {
			playerCounter--;
		}

		if (msg.getPerformative() == ACLMessage.REQUEST
				&& players.get(msg.getSender().getLocalName()).equals(PlayerTypes.FORTUNE_TELLER)) {
			String player = msg.getContent();
			ACLMessage reply = msg.createReply();
			reply.setContent(players.get(player).name());
			myAgent.send(reply);
		}

		if (playerCounter == 0) {
			System.out.println("its day");
			// playerCounter = players.size();
			// currentState = States.NIGHT;
			// sendToAll(ACLMessage.INFORM, "NIGHT", "player");
		}
	}

	private void gameStart(ACLMessage msg) {
		if (msg.getPerformative() == ACLMessage.CONFIRM) {
			playerCounter--;
		}
		if (playerCounter == 0) {
			System.out.println("its night");
			playerCounter = players.size();
			currentState = States.NIGHT;
			sendToAll(ACLMessage.INFORM, NIGHT, "player");
		}
	}

	@Override
	public boolean done() {
		// TODO Auto-generated method stub
		return false;
	}

}