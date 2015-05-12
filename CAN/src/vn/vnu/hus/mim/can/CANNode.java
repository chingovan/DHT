package vn.vnu.hus.mim.can;

import java.util.LinkedList;
import java.util.List;

import vn.vnu.hus.mim.can.util.NodeUtil;
import vn.vnu.hus.mim.can.util.ZoneFactoryUtil;

public class CANNode {
	private String			nodeId;
	private CANKey			canKey;

	private Zone			zone;
	private List<CANNode>	neighbors;

	public CANNode(String nodeId, Zone zone) {
		super();
		this.nodeId = nodeId;
		this.canKey = new CANKey(nodeId);
		this.zone = zone;
		neighbors = new LinkedList<CANNode>();
	}

	public CANNode(String nodeId) {
		super();
		this.nodeId = nodeId;
		this.canKey = new CANKey(nodeId);
		this.zone = ZoneFactoryUtil.getDefaultZone();
		neighbors = new LinkedList<CANNode>();
	}

	/**
	 * Join a {@link CANNode} into the hyper-rectangle
	 * 
	 * @author chinv
	 * @param node
	 *            The node which are joining into hyper-rectangle
	 * */
	public void join(CANNode node) {

		CANNode nearestNode = NodeUtil.findNearestNode(this, node.getNodeKey());
		
		if( nearestNode != null) {
			
			try {
				Zone[] zones = ZoneFactoryUtil.split( nearestNode.getZone());
				
				if( nearestNode.getNodeKey().getIdentifier().compareTo( node.getNodeKey().getIdentifier()) < 0) {
					
					nearestNode.setZone( zones[0]);
					node.setZone( zones[1]);
				} else {
					
					nearestNode.setZone( zones[1]);
					node.setZone( zones[0]);
				}
			} catch (CANException e) {

				e.printStackTrace();
			}
		}
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

	public List<CANNode> getNeighbors() {
		return neighbors;
	}

	public CANNode findOwner(CANKey key) {
		return null;
	}
}
