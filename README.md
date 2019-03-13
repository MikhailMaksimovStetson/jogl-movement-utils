# jogl-movement-utils

This repository is designed to help users move various objects in their JOGL scenes. The classes included can be used to add keyboard controls to your project and add basic movement to your shapes.

KeyDictionary
: JComponent that listens for key presses and stores them in a hash table. It can be used for any aplication that wants to implement keyboard controls. In the class, keys are key bound to an Action object using InputMap and ActionMap classes that are part of JComponent. This Action updates the values in the hash table contained in the KeyDictionary. Keys are stored in a `HashMap<String, Boolean>`  object where String is the name of the key and Boolean denotes if it's pressed or not. The dictionary is updated every time a key is pressed or released. The hash table can be read by other classes to interpret key presses. KeyDictionary can be added to any AWT container like JFrame or JLabel with the  `add(Component)` method.
**note**: some keys are not included in the KeyDictionary, and you will have to add them yourself in the constructor if you want to use them.

AutoVal
: Object for storing and automating numerical values. It can be used to oscillate numbers from minimum to maximum and back at a set speed. This is mostly useful if you want the position of a model to bounce back and forth between 2 positions. It can also interpret the KeyDictionary hash table to increase or decrease a value based on a specific key press. 

## Camera movement example with keyboard controls
I am assuming that you already have a JOGL project where you want to move the camera with keys. Here are the steps for adding camera controls to an existing project:

To use my classes, add my "move" package to your project.
Alternately, you can add my jogl-movement-utils project to your project's build path.

Add these imports
~~~~
import move.AutoVal;
import move.KeyDictionary;
~~~~

Create these properties before constructor. The format is `AutoVal(initial position or rotation, movement speed, KeyDictionary used)`.
~~~~
private KeyDictionary listener = new KeyDictionary();
private AutoVal cameraLocX = new AutoVal(0, 0.1, listener);
private AutoVal cameraLocY = new AutoVal(0, 0.1, listener);
private AutoVal cameraLocZ = new AutoVal(5, 0.1, listener);
private AutoVal cameraRotX = new AutoVal(0, 0.02, listener);
private AutoVal cameraRotY = new AutoVal(0, 0.02, listener);
private AutoVal cameraRotZ = new AutoVal(0, 0.02, listener);
~~~~

Add the KeyDictoinary to your JFrame in the constructor **before** adding your GLCanvas
~~~~
this.add(listener);
~~~~

Add this to constructor if canvas is not already animated
~~~~
FPSAnimator fpsAnimtr = new FPSAnimator(myCanvas, 60);
fpsAnimtr.start();
~~~~

Add these into your display method
~~~~
cameraLocX.keyPressMovement("a", "d");
cameraLocY.keyPressMovement("shift", "space");
cameraLocZ.keyPressMovement("w", "s");
cameraRotX.keyPressMovement("i", "k");
cameraRotY.keyPressMovement("j", "l");
cameraRotZ.keyPressMovement("u", "o");
~~~~

Include your AutoVals in matrix multiplications for the view
~~~~
viewMatrixName.translate((float) -cameraLocX.value, (float) -cameraLocY.value, (float) -cameraLocZ.value);
viewMatrixName.rotateXYZ((float) -cameraRotX.value, (float) -cameraRotY.value, (float) -cameraRotZ.value);
~~~~
