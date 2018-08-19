package main.domain.data;


import java.util.List;
import java.util.Map;


import main.utilities.StringUtilities;

public class TransportLoad implements LoadTypes<Transport> {


	@Override
	public void loadTypes(String string, Map<Integer, Transport> types) {		
		List<String> moreString = StringUtilities.decomposeValueSeperatedString(string, '|');
		Transport type = new Transport();
		type.setCode(Integer.parseInt(moreString.get(0)));
		type.setName(moreString.get(1));					
		types.put(type.getCode(), type);		
	}

}
