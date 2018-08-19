package main.domain.data;

import java.util.ArrayList;
import java.util.List;

public class Transport implements StaticData{
	private String name = "Nothing";
	private int code;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	@Override
	public List<String> getDetailsInOrder() {
		List<String> returns = new ArrayList<String>();
		returns.add(""+code);
		returns.add(name);		
		return returns;
	}
	
}
