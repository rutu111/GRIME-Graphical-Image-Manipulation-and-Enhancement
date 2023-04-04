A text README file explaining your design. Your README file should give the graders an overview of what the purposes are for every class, interface, etc. that you include in your submission, so that they can quickly get a high-level overview of your code. It does not replace the need for proper Javadoc!

model:
Basic idea:
To better understand the design, here we segregate 1.Images 2. Type of Object in the image
Imagine an image to be a matrix containing objects of type T where T can be 1. Any chennel (3, 4, 5 etc). 2. Channels of any type (eg: int, double, float). The design tries to make the code exinsible by keeping in mind that images could have 1. Different number of channels 2. Each channel of different types (int, double etc).

Classes concerning Images:
1. TypeOFImage: This interface represents an image of ANY type. Classes implementing this should represent a matrix with a width and a height. 
3. CommonOperations: This abstract class implements TypeOFImage and contains implementations for methods that are common for any kind of image such as 1. Equals (comparing two TypeOFImage objects) 2. Horizontal flip 3. Vertical flip. 
4. ThreeChannelImageOperations: This abstract class extends CommonOperations and contains implementations for methods that are common for threeChannelImages such as combining 3 images into 1. 
5. RGBIntegerImage: Represents an image containing RGBIntegerObjects. Here we have operatiosn that are specific to this type. Contains an inner class builder which builds an RGBIntegerImage. This inner class builder extends ImageBuilder. The builder is the only way to create this image. 
6. ImageBuilder: Builds a matrix of any type TypeOfImageObject[][] that extends it. The specific type of TypeOfImageObject[][] to build is determine by builder classes that extend this class. 

Classes concerning objects inside an image: 

7. TypeOfImageObject: Interface representing objects of type T.
8. ThreeChannelImageObject: extends TypeOfImageObject and contains methods specific to an object containing 3 channels.
9. RGBIntergerObject: extends ThreeChannelImageObject. Represents a 3 channel object of integers - RGB. 

So, TypeOFImage contains TypeOfImageObject[][], width and height. 

10. model:The controller only interacts with this class. This class has helper methods for each operation. The model class maintains a hashmap which contains TypeOfImage objects and the names of those object (Strings). The idea is: controlled passes a key (name of object) to the model. If image exists, then the requested operation is called on that image (dynamic dispatch) and then, the new image object is stored in the hashmap with the name provided by the user. If image not found in hashmap, an exception is thrown.
11. Operations interface: Contains signatures for the model. Implemented by model and MockModel (test).

Other classes. 
12. ComponentRGB: Enum for RGB componenets.
13. MeasuremenType: Enum for value, intensite and luma. 

controller:
1. ImageController: Takes inputs from the view (command line), tells the model what to do and returns an output back to the view. 
2. ImageUtils: Reads and Saves PPM files. Has direct access to the appropiate builder. 

===================

HOW TO RUN:

1. Please run main.java file. 
2. You will be prompted to type things on the view (Command line). 
3. You can type commands in the exact same way as provided in the example in the assignment. The formatting is the exact same. 
4. You can run the entire script at once by typing "run script.txt" and you will see images in the images/ folder inside your main folder. 
5. Press exit to exit the program at any time.

====================
Citation: Me. I like to take photos.  