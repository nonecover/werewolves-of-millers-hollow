package pt.up.fe.aiad.werewolves_of_millers_hollow.agents;

import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import pt.up.fe.aiad.werewolves_of_millers_hollow.behaviours.NewPlayerBehaviour;

public class Player extends Agent {
	protected void setup() {
		System.out.println("player created");
		try {
			DFService.register(this, createDFAgentDescriptionWithType("player"));
		} catch (FIPAException e) {
			e.printStackTrace();
		}
		addBehaviour(new NewPlayerBehaviour(this));
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

	protected void takeDown() {

	}
}
