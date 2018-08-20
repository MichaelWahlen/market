package main.domain.data;

import java.util.ArrayList;
import java.util.List;

import main.utilities.StringUtilities;

public class Switch implements StaticData {
	
	private Transport connectorA;
	private Transport connectorB;
	private int code;
	private String name;
	
	public Transport getConnectorA() {
		return connectorA;
	}
	
	public void setConnectorA(Transport connectorA) {
		this.connectorA = connectorA;
	}
	
	public Transport getConnectorB() {
		return connectorB;
	}
	
	public void setConnectorB(Transport connectorB) {
		this.connectorB = connectorB;
	}
	
	@Override
	public int getCode() {
		return code;
	}
	
	public void setCode(int code) {
		this.code = code;
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public List<String> getDetailsInOrder(){
		List<String> returns = new ArrayList<String>();
		returns.add(""+code);
		returns.add(name);
		returns.add(connectorA.getName());
		returns.add(connectorB.getName());
		return returns;
	}

	@Override
	public StaticData get(String string) {
		List<String> moreString = StringUtilities.decomposeValueSeperatedString(string, '|');
		Switch localSwitch = new Switch();
		localSwitch.setCode(Integer.parseInt(moreString.get(0)));
		localSwitch.setName(moreString.get(1));	
		localSwitch.setConnectorA(FactoryHolder.getInstance().getGenericFactory(Transport.class).getType(Integer.parseInt(moreString.get(2))));
		localSwitch.setConnectorB(FactoryHolder.getInstance().getGenericFactory(Transport.class).getType(Integer.parseInt(moreString.get(3))));
		return localSwitch;
	}

		
	
}
