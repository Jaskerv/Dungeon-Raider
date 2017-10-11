package lib;

import java.io.Serializable;

public class DataSave implements Serializable {

	private static final long serialVersionUID = 1L;

	public String data ="I AM DATA";
	public String newData ="I AM New DATA";
	public int numData = 8008;


	@Override
	public String toString() {
		StringBuilder out = new StringBuilder();

		out.append(data + "\n");
		out.append(newData + "\n");
		out.append(numData + "\n");

		return out.toString();
	}




}
