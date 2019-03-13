package move;

import java.awt.event.ActionEvent;
import java.util.HashMap;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

/**
 * Class that keeps track of keys pressed with a hash table
 * @author Mikhail Maksimov
 */
@SuppressWarnings("serial")
public class KeyDictionary extends JComponent
{
	private static final int IFW = WHEN_IN_FOCUSED_WINDOW;
	//A hash-map used to record if certain keys are pressed or not
	HashMap<String, Boolean> dictionary = new HashMap<String, Boolean>();

	/**
	 * Default constructor that binds keys to boolean values in our dictionary.
	 */
	public KeyDictionary()
	{
		for (char alphabet = 'A'; alphabet <= 'Z'; alphabet++)
		{
			String keyName = String.valueOf(alphabet);
			keyBind(keyName);
		}
		for (int numbers = 0; numbers <= 9; numbers++)
		{
			String keyName = String.valueOf(numbers);
			keyBind(keyName);
		}
		keyBind("SPACE");
		keyBindModifier("ctrl", "CONTROL");
		keyBindModifier("shift", "SHIFT");
		keyBindModifier("alt", "ALT");
	}

	/**
	 * Creates a key-bind to an Action for a regular key.
	 * @param key		the name of the key
	 */
	public void keyBind(String key)
	{
		dictionary.put(key, false);
		getInputMap(IFW).put(KeyStroke.getKeyStroke("pressed " + key), key + " true");
		getActionMap().put(key + " true", new UpdateDictionary(dictionary, key, true));
		getInputMap(IFW).put(KeyStroke.getKeyStroke("released " + key), key + " false");
		getActionMap().put(key + " false", new UpdateDictionary(dictionary, key, false));
	}

	/**
	 * Creates a key-bind to an Action for a modifier key.
	 * @param modifier	the modifier text for the key being pressed
	 * @param key		the name of the key
	 */
	public void keyBindModifier(String modifier, String key)
	{
		dictionary.put(key, false);
		getInputMap(IFW).put(KeyStroke.getKeyStroke(modifier + " pressed " + key), key + " true");
		getActionMap().put(key + " true", new UpdateDictionary(dictionary, key, true));
		getInputMap(IFW).put(KeyStroke.getKeyStroke("released " + key), key + " false");
		getActionMap().put(key + " false", new UpdateDictionary(dictionary, key, false));
	}
}

/**
 * Action for changing the boolean value of one key in our dictionary. Can be used add new keys.
 * @author Mikhail Maksimov
 */
@SuppressWarnings("serial")
class UpdateDictionary extends AbstractAction
{
	HashMap<String, Boolean> dictionary;
	String key;
	boolean val;

	/**
	 * Constructor for Action.
	 * @param dictionary	hash-map used to record if certain keys are pressed or not
	 * @param key			the name of the key that needs to be updated
	 * @param val			he new boolean value for the key: true == pressed, false == not pressed
	 */
	public UpdateDictionary(HashMap<String, Boolean> dictionary, String key, boolean val)
	{
		this.dictionary = dictionary;
		this.key = key;
		this.val = val;
	}

	/**
	 * Method that gets called when Action is performed. Updates values in the dictionary.
	 */
	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		dictionary.put(key, val);
	}
}