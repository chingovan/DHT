package vn.vnu.hus.mim.can;

public class ZoneFactoryUtil {

	public static Zone[] split(Zone zone) throws CANException {
		if (zone == null) {
			throw new CANException("Cannot split empty zone!");
		}

		Zone[] zones = new Zone[2];
		if (zone.isSplitByHorizontal()) {
			double y = (zone.getLengthOfY() / 2) + zone.getFromY();
			zones[0] = new Zone(zone.getFromX(), zone.getToX(), zone.getFromY(), y);
			zones[1] = new Zone(zone.getFromX(), zone.getToX(), y, zone.getToY());
		} else {
			double x = (zone.getLengthOfX() / 2) + zone.getFromX();
			zones[0] = new Zone(zone.getFromX(), x, zone.getFromY(), zone.getToY());
			zones[1] = new Zone(x, zone.getToX(), zone.getFromY(), zone.getToY());
		}
		return zones;
	}

	public static int compareTo(String key1, String key2) {

		if (key1 != null && key2 != null) {

			return key1.compareTo(key2);
		}

		return 0;
	}
}
