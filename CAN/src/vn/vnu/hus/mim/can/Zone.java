package vn.vnu.hus.mim.can;

public class Zone {

	public static final double	MAXIMUM_X	= 1D;
	public static final double	MINIMUM_X	= 0D;
	public static final double	MAXIMUM_Y	= 1D;
	public static final double	MINIMUN_Y	= 0D;

	private double				fromX;
	private double				toX;
	private double				fromY;
	private double				toY;

	public Zone() {

		super();
	}

	public Zone(double fromX, double toX, double fromY, double toY) {

		super();

		this.fromX = fromX;
		this.toX = toX;
		this.fromY = fromY;
		this.toY = toY;
	}

	public double getFromX() {
		return fromX;
	}

	public void setFromX(double fromX) throws CANException {

		if (fromX > this.getToX()) {

			throw new CANException("The from X coordinate are must less than to X coordinate");
		}

		if (MINIMUM_X > fromX || fromX > MAXIMUM_X) {

			throw new CANException("The from X coordinate is out of range (" + MINIMUM_X + ", " + MAXIMUM_X + ").");
		}

		this.fromX = fromX;
	}

	public double getToX() {

		return toX;
	}

	public void setToX(double toX) throws CANException {

		if (toX < getFromX()) {

			throw new CANException("The to X coordinate must be greater than from X coordinate");
		}

		if (MINIMUM_X > toX || toX > MAXIMUM_X) {

			throw new CANException("The toX coordinate is out of range (" + MINIMUM_X + ", " + MAXIMUM_X + ").");
		}

		this.toX = toX;
	}

	public double getFromY() {

		return fromY;
	}

	public void setFromY(double fromY) throws CANException {

		if (fromY > getToY()) {

			throw new CANException("The from Y coordinate must be less thanh to Y coordinate");
		}

		if (MINIMUN_Y > fromY || fromY > MAXIMUM_Y) {

			throw new CANException("The fromY coordinate is out of range (" + MINIMUN_Y + ", " + MAXIMUM_Y + ").");
		}

		this.fromY = fromY;
	}

	public double getToY() {

		return toY;
	}

	public void setToY(double toY) throws CANException {

		if (toY < getFromY()) {

			throw new CANException("The to Y coordinate must be greater thanh fromY coordinate");
		}

		if (MINIMUN_Y > toY || toY > MAXIMUM_Y) {

			throw new CANException("The fromY coordinate is out of range (" + MINIMUN_Y + ", " + MAXIMUM_Y + ").");
		}
		this.toY = toY;
	}

	/**
	 * Get length of X coordinate
	 * */
	public double getLengthOfX() {

		return getToX() - getFromX();
	}

	/**
	 * Get length of Y coordinate
	 * */
	public double getLengthOfY() {

		return getToY() - getFromY();
	}

	public boolean isSplitByHorizontal() {

		if (getLengthOfY() > getLengthOfX()) {

			return true;
		}

		return false;
	}
}
