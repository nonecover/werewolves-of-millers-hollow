package pt.up.fe.aiad.werewolves_of_millers_hollow.common;

import java.util.function.Supplier;

import jade.lang.acl.ACLMessage;
import pt.up.fe.aiad.werewolves_of_millers_hollow.agents.Player;

public class Logger {

	public static Logger getInstance(Player player) {
		return new Logger(player);
	}

	public Logger(Player player) {
		this.type = () -> player.myType.toString();
		this.name = () -> player.getLocalName();
	}

	private Supplier<String> type;
	private Supplier<String> name;

	public void logMessage(int msgType, String content, String reciever) {
		System.out.println(String.format("%s ( %s ) --> %s --> %s: %s", type.get(), name.get(),
				ACLMessage.getPerformative(msgType), reciever, content));
	}

}
