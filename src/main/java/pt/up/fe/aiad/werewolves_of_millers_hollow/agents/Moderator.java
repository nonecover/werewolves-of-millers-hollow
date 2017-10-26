package pt.up.fe.aiad.werewolves_of_millers_hollow.agents;

import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import pt.up.fe.aiad.werewolves_of_millers_hollow.behaviours.ModeratorBehaviour;

public class Moderator extends Agent {
	public static final String NAME = "moderator";

	protected void setup() {
		try {
			DFService.register(this, createDFAgentDescriptionWithType(Moderator.NAME));
		} catch (FIPAException e) {
			e.printStackTrace();
		}
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
}
