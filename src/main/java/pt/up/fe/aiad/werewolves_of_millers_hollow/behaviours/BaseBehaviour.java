package pt.up.fe.aiad.werewolves_of_millers_hollow.behaviours;

import jade.core.Agent;
import jade.core.behaviours.SimpleBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;

public abstract class BaseBehaviour extends SimpleBehaviour {
	public BaseBehaviour(Agent agent) {
		super(agent);
	}

	void sendMessage(int type, String content, String reciever) {
		// System.out.println(type + " " + content + " " + reciever);

		DFAgentDescription template = new DFAgentDescription();
		ServiceDescription description = new ServiceDescription();
		description.setType(reciever);
		template.addServices(description);
		try {
			DFAgentDescription[] result = DFService.search(myAgent, template);
			if (result.length > 0) {
				ACLMessage msg = new ACLMessage(type);
				msg.addReceiver(result[0].getName());
				msg.setContent(content);
				myAgent.send(msg);
			}
		} catch (FIPAException e) {
			e.printStackTrace();
		}
	}

}
