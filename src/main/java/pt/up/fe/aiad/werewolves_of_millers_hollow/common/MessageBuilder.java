package pt.up.fe.aiad.werewolves_of_millers_hollow.common;

import jade.core.Agent;

public class MessageBuilder {
	private Agent agent;

	public static Send create(Agent agent) {
		return new MessageBuilder(agent)::send;
	}

	private MessageBuilder(Agent agent) {
		this.agent = agent;

	}

	private Message send() {
		return this::message;
	}

	private void message() {

	}

	@FunctionalInterface
	public interface Message {
		void message();
	}

	@FunctionalInterface
	public interface Send {
		Message send();

		default Message sendIf(boolean condition) {
			if (condition)
				return this::send;
			else
				return () -> {
				};
		};
	}
}
