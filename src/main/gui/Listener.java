package main.gui;

import java.util.List;

public interface Listener {
	public void update(List<String> input,Controller controller);

	public void update(String[] args, Controller controller);
}
