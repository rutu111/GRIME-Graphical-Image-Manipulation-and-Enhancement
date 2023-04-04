Assingment 5
A README file in the root submission folder (that contains src, test and res). The README file should document which parts of the program are complete, and design changes and justifications.

Changes and justifications:
model:
1. Since now we need to store images of various types, PNG images have alpha channels. So we added a new object class called RGBIntegerAlphaObject (implements TypeOfImageObject) that stores objects of 4 channels (RGBA). Next, we created a new image class called RGBIntegerAlphaImage (extends ThreeChannelOperations abstract class - reason it extends this class is because we still do operations on RGB, alpha remains as is) that has an inner builder class that creates an RGBIntegerAlphaImage containing RGBIntegerAlphaObjects. 
2. We added new operations (dither, sepia etc) to the TypeOfimage interface. We have not used inheritance or composiiton for the following reasons:
(a) Composition is typically used when you don't want to reuse all operations, but only some. In this case, an image should be able to perform all operations (old + new) so composiiton is not the best option. 
(b) Dither is an image operation vs dither has an image operation. The former makes more sense, so composition might not be the best way forward given our design
Okay, so then inheritance? No, because:
(a) We are only adding new operations. We are not modifying existing operations. Client still has access to old operations and now, also has access to new operation. Doing this does not break/affect clients using code from assingment 4. 
(b) using inheritance does not benefit us for incorporatig these new operations our design. Why create multiple new classes and make the design more complicated to understand when we can just add to the current interface and classes without breaking the existing code? Moreover, if inheritance made more sense from the "being able to revert back" stand-point then it still does not stand true because whether we use inheritance or add to exisitng classes, reverting back would require multiple changes. 
So, since inheritance does not hold any added value for this very specific case, we decided to not go ahead with it. 
So below are the additions made to incorporate new operations:
3. Added new operations to the TypeOfImage interface
4. Implemented them in the existing ThreeChannelObjectOperations abstract class. 
5. Added new operations to Operations.java and model.java.
That is it. 
Other changes in model:
6. Added a hasAlpha9() method to TypeOfImageObject which returns the alpha channel if one exists and null otherwise. 
7. Added/modified two abstract methods to ThreeChannelObjectoperations: (a) getField: this gets the fields of the respective classes (b) getObject: this was already existing but we added a 4th paramter to this which now gets the alpha channel too.  

view:
Created a view interface and class because the previous design did not have a view folder. 

controller:
1. Now prints outputs through the view instead of doing it directly. 
2. Command design pattern adapted. Now there are no switch cases. Each switch case is now coverted into its own class. These objects are stored in a hashmap - same methodology as taught in class. (new files: commands folder and CommandDesignOperations interface). Appropiate changes in the controller (such as removing switch statements and added a hashmap etc). 
3. ImageUtils: Added ImageIORead and ImageIOWrite functions to read and write jpg, png and bmp formats. 
Idea: Read buffer image -> store in our format (TypeOfImage) -> do operations -> convert back to buffer image -> save in requested format. 
Read: Read image as a buffer image -> store in intermediate format (TypeOfImage). So we convert the buffered image into our format and do operations on it as done with ppm. 
Write: fetch image from the model -> convert the buffer image -> save to file format requested. 
Note: We build the loaded image based on its type (RGBintegerAlphaImage vs RGBintegerImage). For example, if we have a PNG image with an alpha channel, it will build a RGBintegerAlphaImage. If we have a Bmp image without alpha, it will build RGBintegerImage. 
Everything done for PPM images in the previous assingment remains the same.  

How to run new operations:
Blur: filter-blur imageName newimageName
Dither: dither imageName newimageName
Sharpen: filter-sharpen imageName newimageName
Greyscale Transformation: transform-greyscale imageName newimageName
Sepia Transformation: transform-sepia fimageName newimageName

============================================================
Assignment 4
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