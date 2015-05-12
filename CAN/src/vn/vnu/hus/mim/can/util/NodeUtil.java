package vn.vnu.hus.mim.can.util;

import vn.vnu.hus.mim.can.CANKey;
import vn.vnu.hus.mim.can.CANNode;

public class NodeUtil {
	/**
	 * Find nearest {@link CANNode}
	 * */
	public static CANNode findNearestNode(CANNode node, CANKey key) {
		if (node != null) {

			if (node.getNeighbors() == null || node.getNeighbors().isEmpty()) {

				return null;
			} else {

				CANNode returnNode = null;
				for (CANNode neighborNode : node.getNeighbors()) {

					CANKey neighborKey = neighborNode.getNodeKey();
					if (key.getIdentifier().compareTo(neighborKey.getIdentifier()) > 0) {

						if (returnNode == null) {

							returnNode = neighborNode;
						} else {

							if (NodeUtil.getPrefixLength(key.getIdentifier(), neighborKey.getIdentifier()) < NodeUtil.getPrefixLength(key.getIdentifier(),
									returnNode.getNodeKey().getIdentifier())) {
								returnNode = neighborNode;
							} else if (NodeUtil.getPrefixLength(key.getIdentifier(), neighborKey.getIdentifier()) == NodeUtil.getPrefixLength(
									key.getIdentifier(), returnNode.getNodeKey().getIdentifier())) {
								if (Math.abs(key.getIdentifier().compareTo(neighborKey.getIdentifier())) > Math.abs(key.getIdentifier().compareTo(
										returnNode.getNodeKey().getIdentifier()))) {
									returnNode = neighborNode;
								}
							}
						}
					}
				}

				if (returnNode != null) {

					return findNearestNode(returnNode, key);
				} else {

					return node;
				}
			}
		}
		return null;
	}

	public static int getPrefixLength(String s1, String s2) {

		if (s1 != null && s2 != null) {

			int length = s1.length() < s2.length() ? s1.length() : s2.length();

			for (int i = 0; i < length; i++) {
				if (s1.charAt(i) != s2.charAt(i)) {
					return i + 1;
				}
			}
		}

		return 0;
	}
}
