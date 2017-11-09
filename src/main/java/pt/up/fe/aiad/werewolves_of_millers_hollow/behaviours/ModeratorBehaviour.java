package pt.up.fe.aiad.werewolves_of_millers_hollow.behaviours;

import java.util.Set;

import jade.wrapper.StaleProxyException;
import pt.up.fe.aiad.werewolves_of_millers_hollow.agents.Moderator;
import pt.up.fe.aiad.werewolves_of_millers_hollow.agents.Player;
import pt.up.fe.aiad.werewolves_of_millers_hollow.common.PlayerTypes;
import pt.up.fe.aiad.werewolves_of_millers_hollow.states.States;

public class ModeratorBehaviour extends BaseBehaviour {
	private static final long serialVersionUID = 8492835806340589393L;

	public static String NIGHT = "NIGHT";

	States currentState = States.GAME_START;
	private Moderator myAgent;

	public ModeratorBehaviour(Moderator agent) {
		super(agent);
		myAgent = agent;
	}

	@Override
	public void onStart() {
		super.onStart();
		// creates all players agents
		myAgent.players.keySet().forEach(player -> createNewPlayer(player));
	};

	private void createNewPlayer(String name) {
		PlayerTypes type = myAgent.players.get(name);
		Set<String> names = myAgent.players.keySet();
		Object[] args = { type, names };
		try {
			myAgent.getContainerController().createNewAgent(name, Player.class.getName(), args).start();
		} catch (StaleProxyException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void action() {
		currentState = currentState.getValue().apply(myAgent);
	}

	@Override
	public boolean done() {
		return false;
	}
}