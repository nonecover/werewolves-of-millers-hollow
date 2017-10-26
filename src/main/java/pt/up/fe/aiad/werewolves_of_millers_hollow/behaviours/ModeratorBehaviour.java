package pt.up.fe.aiad.werewolves_of_millers_hollow.behaviours;

import java.util.HashMap;
import java.util.Map;

import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import jade.wrapper.StaleProxyException;
import pt.up.fe.aiad.werewolves_of_millers_hollow.agents.Player;
import pt.up.fe.aiad.werewolves_of_millers_hollow.common.MessageBuilder;
import pt.up.fe.aiad.werewolves_of_millers_hollow.common.PlayerTypes;

public class ModeratorBehaviour extends BaseBehaviour {
	private static final long serialVersionUID = 8492835806340589393L;

	public ModeratorBehaviour(Agent agent) {
		super(agent);
	}

	private Map<String, PlayerTypes> players = new HashMap<>();

	@Override
	public void onStart() {
		super.onStart();

		MessageBuilder.create(myAgent).sendIf(true).message();

		players = ((Map<String, PlayerTypes>[]) myAgent.getArguments())[0];
		players.keySet().forEach(player -> createNewPlayer(player));
	}

	private void createNewPlayer(String name) {
		try {
			myAgent.getContainerController().createNewAgent(name, Player.class.getName(), null).start();
		} catch (StaleProxyException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void action() {
		ACLMessage msg = this.myAgent.blockingReceive();
		if (msg.getPerformative() == ACLMessage.SUBSCRIBE) {
			String key = msg.getSender().getLocalName();
			PlayerTypes type = players.get(key);
			ACLMessage reply = msg.createReply();
			reply.setContent(type.name());
			myAgent.send(reply);
		}
	}

	@Override
	public boolean done() {
		// TODO Auto-generated method stub
		return false;
	}
}