package pt.up.fe.aiad.werewolves_of_millers_hollow.behaviours;

import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import jade.lang.acl.ACLMessage;
import pt.up.fe.aiad.werewolves_of_millers_hollow.agents.Moderator;
import pt.up.fe.aiad.werewolves_of_millers_hollow.agents.Player;
import pt.up.fe.aiad.werewolves_of_millers_hollow.common.PlayerTypes;

public class WerewolfBehaviour extends BaseBehaviour {
	private static final long serialVersionUID = 754398824446122467L;
	private Gson gson = new GsonBuilder().create();
	private Player myAgent;

	public WerewolfBehaviour(Player player) {
		super(player);
		this.myAgent = player;
		System.out.println(player.getLocalName() + ": I am werewolf");
	}

	private boolean isAsked = false;

	private void askForWerefolfesNames(ACLMessage msg) {
		if (isAsked)
			return;
		if (msg.getContent().equals(ModeratorBehaviour.NIGHT) && msg.getPerformative() == ACLMessage.INFORM) {
			myAgent.sendMessage(ACLMessage.REQUEST, "WHO ARE OTHER WEREWOLFS", Moderator.NAME);
		}
		if (msg.getPerformative() == ACLMessage.REQUEST) {
			Type listType = new TypeToken<List<String>>() {
			}.getType();
			List<String> werewolfesNames = gson.fromJson(msg.getContent(), listType);
			werewolfesNames.forEach(werewolfName -> myAgent.players.put(werewolfName, PlayerTypes.WEREWOLF));

			System.out.println(myAgent.getLocalName() + ": " + werewolfesNames.toString());
			isAsked = true;
			myAgent.sendMessage(ACLMessage.CONFIRM, "OK", Moderator.NAME);
		}
	}

	@Override
	public void action() {
		ACLMessage msg = this.myAgent.blockingReceive();
		askForWerefolfesNames(msg);

	}

	@Override
	public boolean done() {
		// TODO Auto-generated method stub
		return false;
	}

}
