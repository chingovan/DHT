package vn.vnu.hus.mim.can;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

public class CAN {
	private List<CANNode>				nodeList		= new ArrayList<CANNode>();
	private SortedMap<CANKey, CANNode>	sortedNodeMap	= new TreeMap<CANKey, CANNode>();

	public void createNode(String indentify) throws CANException {
		CANNode node = new CANNode(indentify);

		nodeList.add(node);
		if (sortedNodeMap.get(node.getNodeKey()) != null) {
			throw new CANException("Duplicated key: " + node);
		}
		sortedNodeMap.put(node.getNodeKey(), node);
	}

	public CANNode getNode(int i) {
		return (CANNode) nodeList.get(i);
	}
}
