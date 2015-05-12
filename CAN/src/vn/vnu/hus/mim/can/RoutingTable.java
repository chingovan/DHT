package vn.vnu.hus.mim.can;

import java.util.ArrayList;
import java.util.List;

public class RoutingTable {
	List<CANNode>	nodes;

	public RoutingTable() {
		super();
		nodes = new ArrayList<CANNode>();
	}

	public RoutingTable(List<CANNode> nodes) {
		super();
		this.nodes = nodes;
		if (this.nodes == null) {
			this.nodes = new ArrayList<CANNode>();
		}
	}
}
