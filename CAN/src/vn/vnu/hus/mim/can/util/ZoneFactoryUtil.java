package vn.vnu.hus.mim.can.util;

import vn.vnu.hus.mim.can.CANException;
import vn.vnu.hus.mim.can.Zone;

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

	public static boolean isNeighbor(Zone zone1, Zone zone2) {

		if (zone1 != null && zone2 != null) {

			double minX = zone1.getFromX() < zone2.getFromX() ? zone1.getFromX() : zone2.getFromX();
			double maxX = zone1.getToX() > zone2.getToX() ? zone1.getToX() : zone2.getToX();

			double minY = zone1.getFromY() < zone2.getFromY() ? zone1.getFromY() : zone2.getFromY();
			double maxY = zone1.getToY() > zone2.getToY() ? zone1.getToY() : zone2.getToY();

			double totalX = zone1.getToX() - zone1.getFromX() + zone2.getToX() - zone2.getFromX();
			double totalY = zone1.getToY() - zone1.getFromY() + zone2.getToY() - zone2.getFromY();

			if ((totalX == maxX - minX) && (totalY == maxY - minY)) {

				return false;
			} else if ((totalX <= maxX - minX) && (totalY <= maxY - minY)) {

				return true;
			} else {

				return false;
			}
		}

		return false;
	}

	public static int compareTo(String key1, String key2) {

		if (key1 != null && key2 != null) {

			return key1.compareTo(key2);
		}

		return 0;
	}

	public static Zone getDefaultZone() {

		return new Zone(Zone.MINIMUM_X, Zone.MAXIMUM_X, Zone.MINIMUN_Y, Zone.MAXIMUM_Y);
	}
}
