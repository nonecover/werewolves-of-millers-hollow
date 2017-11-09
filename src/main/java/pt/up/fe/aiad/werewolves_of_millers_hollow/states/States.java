package pt.up.fe.aiad.werewolves_of_millers_hollow.states;

import java.util.function.Function;

import pt.up.fe.aiad.werewolves_of_millers_hollow.agents.Moderator;

//map enums to states actions behaviours
public enum States {
	NIGHT(new Night()), DAY(new Night()), GAME_START(new GameStart());

	private Function<Moderator, States> value;

	private States(Function<Moderator, States> value) {
		this.value = value;
	}

	public Function<Moderator, States> getValue() {
		return value;
	}

}
