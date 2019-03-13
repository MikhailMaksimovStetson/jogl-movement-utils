package move;

/**
 * Class for storing and automating numerical values. Uses KeyDictionary.
 * @author Mikhail Maksimov
 */
public class AutoVal
{
	public double value, inc, min, max;
	public KeyDictionary listener;

	/**
	 * Constructor for numerical values you want to automatically increase/decrease.
	 * @param value		number you want to store in the AutoVal
	 * @param inc		amount added to your value each time it is incremented: affects movement speed
	 * @param min		minimum for your value
	 * @param max		maximum for your value
	 */
	public AutoVal(double value, double inc, double min, double max)
	{
		this.value = value;
		this.inc = inc;
		this.min = min;
		this.max = max;
	}

	/**
	 * Constructor for numerical values you want increase/decrease when keyboard keys are pressed.
	 * @param value		number you want to store in the AutoVal
	 * @param inc		amount added to your value each time it is incremented: affects movement speed
	 * @param listener	KeyDictionary instance you want to get hash-map of pressed keys
	 */
	public AutoVal(double value, double inc, KeyDictionary listener)
	{
		this.value = value;
		this.inc = inc;
		this.listener = listener;
	}

	/**
	 * Raises and lowers value, perpetually bouncing it from the minimum to the maximum and back.
	 * Like dribbling a basketball. Should run once every frame.
	 */
	public void oscillate()
	{
		if (value > max)
		{
			value = max;
			inc = -Math.abs(inc * 2);
		}
		if (value < min)
		{
			value = min;
			inc = Math.abs(inc / 2);
		}
		value += inc;
	}

	/**
	 * Raises and lowers value as if it was the radius of an ellipse.
	 * Minimum becomes the width of the ellipse and maximum becomes the length.
	 * @param radian	current angle for radius in radians
	 */
	public void ellipseRadius(double radian)
	{
		double calc1 = Math.pow(min, 2) * Math.pow(Math.sin(radian), 2);
		double calc2 = Math.pow(max, 2) * Math.pow(Math.cos(radian), 2);
		value = ((min * max) / Math.sqrt(calc1 + calc2));
	}

	/**
	 * Raises and lowers value based on key pressed. 
	 * @param minusKey	name of key for raising the value
	 * @param plusKey	name of key for lowering the value
	 */
	public void keyPressMovement(String minusKey, String plusKey)
	{
		minusKey = minusKey.toUpperCase();
		plusKey = plusKey.toUpperCase();

		if (listener.dictionary.get(minusKey) != null)
		{
			boolean minus = listener.dictionary.get(minusKey);
			if (minus == true)
			{
				value -= inc;
			}
		} else
		{
			System.out.println(minusKey + " key is not in dictionary");
		}

		if (listener.dictionary.get(plusKey) != null)
		{
			boolean plus = listener.dictionary.get(plusKey);
			if (plus == true)
			{
				value += inc;
			}
		} else
		{
			System.out.println(plusKey + " key is not in dictionary");
		}
	}
}
