package vn.vnu.hus.mim.can;

public class CANNode {
	private String	nodeId;
	private CANKey	canKey;

	private Zone	zone;

	public CANNode(String nodeId, Zone zone) {
		super();
		this.nodeId = nodeId;
		this.canKey = new CANKey(nodeId);
		this.zone = zone;
	}

	public CANNode(String nodeId) {
		super();
		this.nodeId = nodeId;
		this.canKey = new CANKey(nodeId);
	}

	public Zone getZone() {
		return zone;
	}

	public void setZone(Zone zone) {
		this.zone = zone;
	}

	public String getNodeId() {
		return nodeId;
	}

	public CANKey getNodeKey() {
		return this.canKey;
	}

	public CANNode findOwner(CANKey key) {
		return null;
	}
}
