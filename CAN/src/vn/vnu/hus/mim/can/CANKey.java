package vn.vnu.hus.mim.can;

public class CANKey {
	private String	identifier;
	private byte[]	key;

	public CANKey() {
		super();
	}

	public CANKey(String identifier) {
		super();
		this.identifier = identifier;
		this.key = Hash.hash(identifier);
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
		key = Hash.hash(identifier);
	}

	public byte[] getKey() {
		return key;
	}
}
