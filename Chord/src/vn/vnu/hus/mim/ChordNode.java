package vn.vnu.hus.mim;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class ChordNode {

	String nodeId;

	ChordKey nodeKey;

	ChordNode predecessor;

	ChordNode successor;

	FingerTable fingerTable;

	List<ChordData> datas;

	public ChordNode(String nodeId) {
		this.nodeId = nodeId;
		this.nodeKey = new ChordKey(nodeId);
		this.fingerTable = new FingerTable(this);
		this.datas = new ArrayList<ChordData>();
		this.create();
	}

	/**
	 * Lookup a successor of given identifier
	 * 
	 * @param identifier
	 *            an identifier to lookup
	 * @return the successor of given identifier
	 */
	public ChordNode findSuccessor(String identifier) {
		ChordKey key = new ChordKey(identifier);
		return findSuccessor(key);
	}

	/**
	 * Lookup a successor of given key
	 * 
	 * @param identifier
	 *            an identifier to lookup
	 * @return the successor of given identifier
	 */
	public ChordNode findSuccessor(ChordKey key) {

		if (this == successor) {
			return this;
		}

		if (key.isBetween(this.getNodeKey(), successor.getNodeKey())
				|| key.compareTo(successor.getNodeKey()) == 0) {
			return successor;
		} else {
			ChordNode node = closestPrecedingNode(key);
			if (node == this) {
				return successor.findSuccessor(key);
			}
			return node.findSuccessor(key);
		}
	}

	private ChordNode closestPrecedingNode(ChordKey key) {
		for (int i = Hash.KEY_LENGTH - 1; i >= 0; i--) {
			Finger finger = fingerTable.getFinger(i);
			ChordKey fingerKey = finger.getNode().getNodeKey();
			if (fingerKey.isBetween(this.getNodeKey(), key)) {
				return finger.getNode();
			}
		}
		return this;
	}

	/**
	 * Creates a new Chord ring.
	 */
	public void create() {
		predecessor = null;
		successor = this;
	}

	/**
	 * Joins a Chord ring with a node in the Chord ring
	 * 
	 * @param node
	 *            a bootstrapping node
	 */
	public void join(ChordNode node) {
		predecessor = null;
		successor = node.findSuccessor(this.getNodeId());
	}

	/**
	 * Verifies the successor, and tells the successor about this node. Should
	 * be called periodically.
	 */
	public void stabilize() {
		ChordNode node = successor.getPredecessor();
		if (node != null) {
			ChordKey key = node.getNodeKey();
			if ((this == successor)
					|| key.isBetween(this.getNodeKey(), successor.getNodeKey())) {
				successor = node;
			}
		}
		successor.notifyPredecessor(this);
	}

	private void notifyPredecessor(ChordNode node) {
		ChordKey key = node.getNodeKey();
		if (predecessor == null
				|| key.isBetween(predecessor.getNodeKey(), this.getNodeKey())) {
			predecessor = node;
		}
	}

	/**
	 * Refreshes finger table entries.
	 */
	public void fixFingers() {
		for (int i = 0; i < Hash.KEY_LENGTH; i++) {
			Finger finger = fingerTable.getFinger(i);
			ChordKey key = finger.getStart();
			finger.setNode(findSuccessor(key));
		}
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("ChordNode[");
		sb.append("ID=" + nodeId);
		sb.append(",KEY=" + nodeKey);
		sb.append("]");
		return sb.toString();
	}

	public void printFingerTable(PrintStream out) {
		out.println("=======================================================");
		out.println("FingerTable: " + this);
		out.println("-------------------------------------------------------");
		out.println("Predecessor: " + predecessor);
		out.println("Successor: " + successor);
		out.println("-------------------------------------------------------");
		for (int i = 0; i < Hash.KEY_LENGTH; i++) {
			Finger finger = fingerTable.getFinger(i);
			out.println(finger.getStart() + "\t" + finger.getNode());
		}
		out.println("=======================================================");
	}

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public ChordKey getNodeKey() {
		return nodeKey;
	}

	public void setNodeKey(ChordKey nodeKey) {
		this.nodeKey = nodeKey;
	}

	public ChordNode getPredecessor() {
		return predecessor;
	}

	public void setPredecessor(ChordNode predecessor) {
		this.predecessor = predecessor;
	}

	public ChordNode getSuccessor() {
		return successor;
	}

	public void setSuccessor(ChordNode successor) {
		this.successor = successor;
	}

	public FingerTable getFingerTable() {
		return fingerTable;
	}

	public void setFingerTable(FingerTable fingerTable) {
		this.fingerTable = fingerTable;
	}

	public List<ChordData> getDatas() {
		return datas;
	}

	public ChordData getDataByChordKey(ChordKey chordKey) {
		if (chordKey != null && datas != null && datas.size() > 0) {
			for (ChordData data : datas) {
				if (chordKey.compareTo(data.getChordKey()) == 0) {
					return data;
				}
			}
		}
		return null;
	}

	private void addData(ChordData data) {
		if (data != null) {
			if (datas == null) {
				datas = new ArrayList<ChordData>();
			}

			boolean isExisted = false;
			for (int i = 0; i < datas.size(); i++) {
				if (data.getChordKey() != null
						&& data.getChordKey().compareTo(
								datas.get(i).getChordKey()) == 0) {
					isExisted = true;
					break;
				}
			}
			if (isExisted == false) {
				datas.add(data);
			}
		}
	}

	private void removeData(ChordData data) {
		if (data != null && datas != null && datas.size() > 0) {
			for (int i = 0; i < datas.size(); i++) {
				if (data.getChordKey() != null
						&& data.getChordKey().compareTo(
								datas.get(i).getChordKey()) == 0) {
					datas.remove(i);
					break;
				}
			}
		}
	}

	/**
	 * Find consist node and store data.
	 * */
	public ChordNode storeData(ChordData data) {
		if (data != null && data.getChordKey() != null) {
			ChordNode successor = findSuccessor(data.getChordKey());
			if (successor != null) {
				successor.addData(data);
			}

			return successor;
		}
		return null;
	}
	
	public ChordNode lookupData(ChordData data) {
		if (data != null && data.getChordKey() != null) {
			ChordNode successor = findSuccessor(data.getChordKey());
			if (successor != null) {
				List<ChordData> datas = successor.getDatas();
				if( datas != null && datas.size() > 0) {
					for (ChordData chordData : datas) {
						if( data.getChordKey().compareTo( chordData.getChordKey()) == 0) {
							return successor;
						}
					}
				}
			}
		}
		return null;
	}
}
