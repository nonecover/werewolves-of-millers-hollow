package pt.up.fe.aiad.werewolves_of_millers_hollow.agents;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import pt.up.fe.aiad.werewolves_of_millers_hollow.common.PlayerTypes;

public class Player extends Agent {
	public Map<String, PlayerTypes> players = new HashMap<>();

	protected void setup() {
		System.out.println("player created");
		try {
			DFService.register(this, createDFAgentDescriptionWithType("player"));
		} catch (FIPAException e) {
			e.printStackTrace();
		}
		Object[] args = this.getArguments();
		PlayerTypes type = (PlayerTypes) args[0];
		Set<String> names = (Set<String>) args[1];
		names.forEach(name -> players.put(name, null));

		this.addBehaviour(type.getBehaviour(this));
		sendMessage(ACLMessage.CONFIRM, "OK", Moderator.NAME);
	}

	private DFAgentDescription createDFAgentDescriptionWithType(String type) {
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(this.getAID());
		ServiceDescription sd = new ServiceDescription();
		sd.setName(this.getName());
		sd.setType(type);
		dfd.addServices(sd);
		return dfd;
	}

	void sendMessage(int type, String content, String reciever) {
		DFAgentDescription template = new DFAgentDescription();
		ServiceDescription description = new ServiceDescription();
		description.setType(reciever);
		template.addServices(description);
		try {
			DFAgentDescription[] result = DFService.search(this, template);
			if (result.length > 0) {
				ACLMessage msg = new ACLMessage(type);
				msg.addReceiver(result[0].getName());
				msg.setContent(content);
				this.send(msg);
			}
		} catch (FIPAException e) {
			e.printStackTrace();
		}
	}

	protected void takeDown() {

	}
}
