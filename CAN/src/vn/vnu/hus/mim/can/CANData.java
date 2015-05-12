package vn.vnu.hus.mim.can;

public class CANData {
	private String	data;
	private CANKey	key;
	private CANNode	owner;

	public CANData(String data, CANKey key) {
		super();
		this.data = data;
		this.key = key;
	}

	public CANData(String data, CANKey key, CANNode owner) {
		super();
		this.data = data;
		this.key = key;
		this.owner = owner;
	}

	public CANNode getOwner() {
		return owner;
	}

	public void setOwner(CANNode owner) {
		this.owner = owner;
	}

	public String getData() {
		return data;
	}

	public CANKey getKey() {
		return key;
	}

}
