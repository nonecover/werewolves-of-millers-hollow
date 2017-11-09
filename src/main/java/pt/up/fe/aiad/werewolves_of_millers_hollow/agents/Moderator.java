package pt.up.fe.aiad.werewolves_of_millers_hollow.agents;

import java.util.HashMap;
import java.util.Map;

import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import pt.up.fe.aiad.werewolves_of_millers_hollow.behaviours.ModeratorBehaviour;
import pt.up.fe.aiad.werewolves_of_millers_hollow.common.PlayerTypes;

public class Moderator extends Agent {
	public static final String NAME = "moderator";
	public Map<String, PlayerTypes> players = new HashMap<>();

	protected void setup() {
		try {
			DFService.register(this, createDFAgentDescriptionWithType(Moderator.NAME));
		} catch (FIPAException e) {
			e.printStackTrace();
		}
		players = ((Map<String, PlayerTypes>[]) getArguments())[0];

		addBehaviour(new ModeratorBehaviour(this));
	}

	protected void takeDown() {

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

	public void sendToAll(int type, String content, String reciever) {
		DFAgentDescription template = new DFAgentDescription();
		ServiceDescription description = new ServiceDescription();
		description.setType(reciever);
		template.addServices(description);
		try {
			DFAgentDescription[] result = DFService.search(this, template);
			for (int i = 0; i < result.length; ++i) {
				ACLMessage msg = new ACLMessage(type);
				msg.addReceiver(result[i].getName());
				msg.setContent(content);
				this.send(msg);
			}
		} catch (FIPAException e) {
			e.printStackTrace();
		}
	}
}
