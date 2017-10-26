package pt.up.fe.aiad.werewolves_of_millers_hollow.behaviours;

import jade.lang.acl.ACLMessage;
import pt.up.fe.aiad.werewolves_of_millers_hollow.agents.Moderator;
import pt.up.fe.aiad.werewolves_of_millers_hollow.agents.Player;
import pt.up.fe.aiad.werewolves_of_millers_hollow.common.PlayerTypes;

public class NewPlayerBehaviour extends BaseBehaviour {

	private Player player;

	public NewPlayerBehaviour(Player player) {
		super(player);
		this.player = player;
		System.out.println(player.getLocalName() + " added");
		askForTheType();
	}

	private void askForTheType() {
		sendMessage(ACLMessage.SUBSCRIBE, "WHO_I_AM", Moderator.NAME);
	}

	@Override
	public void action() {
		ACLMessage msg = this.myAgent.blockingReceive();
		if (msg.getPerformative() == ACLMessage.SUBSCRIBE) {
			String type = msg.getContent();
			player.addBehaviour(PlayerTypes.valueOf(type).getBehaviour(player));
		}
		player.removeBehaviour(this);
	}

	@Override
	public boolean done() {
		// TODO Auto-generated method stub
		return false;
	}

}
