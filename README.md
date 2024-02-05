# Julia Set Generator
This demo application draw a Julia Set with specified parameters using Java 
multithreading. For educational purposes one fractal generating twice: with 1 thread and with max threads of your CPU.
## Usage
There are 3 arguments which should be specified in order to draw Julia Set:
 - -d - result image dimensions, with values width;height. 
**Example**: 1920;1080
 - -c - value of integration constant, with values Real-part;Imaginary-part.
 **Example**: -0.75;0.11
 - -o - result image name.
**Example**: test.png

**Full command example:** java -jar juliasetgen.jar -d 4096;4096 -c -0.75;0.11 -o test.png
