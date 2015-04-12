package vn.vnu.hus.mim.chord;

public class ChordData {
	private String data;
	private ChordKey chordKey;
	private ChordNode owner;

	public ChordData(String data) {
		this.data = data;
		chordKey = new ChordKey(data);
	}

	public String getData() {
		return data;
	}

	public ChordKey getChordKey() {
		return chordKey;
	}

	public ChordNode getOwner() {
		return owner;
	}

}
