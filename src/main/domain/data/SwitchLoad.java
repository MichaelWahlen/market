package main.domain.data;

import java.util.List;
import java.util.Map;

import main.utilities.StringUtilities;

public class SwitchLoad implements LoadTypes<Switch> {


	@Override
	public void loadTypes(String string, Map<Integer, Switch> types) {
		List<String> moreString = StringUtilities.decomposeValueSeperatedString(string, '|');
		Switch localSwitch = new Switch();
		localSwitch.setCode(Integer.parseInt(moreString.get(0)));
		localSwitch.setName(moreString.get(1));	
		localSwitch.setConnectorA(FactoryHolder.getInstance().getTransportInstance().getType(Integer.parseInt(moreString.get(2))));
		localSwitch.setConnectorB(FactoryHolder.getInstance().getTransportInstance().getType(Integer.parseInt(moreString.get(3))));
		types.put(localSwitch.getCode(), localSwitch);		
	}

}
