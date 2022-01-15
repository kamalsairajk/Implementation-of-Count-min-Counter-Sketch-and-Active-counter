# Implementation-of-Count-min-Counter-Sketch-and-Active-counter
CIS6930 Internet Data Streaming

Project Implementation of Count min, Counter Sketch and Active counter

By,
Kamal Sai Raj Kuncha


About the code:
The source code includes a applrun.java file which has classes applrun which basically runs the file,
Flowparameters java class which contains ip address, original size and recorded size variables, Countmin class and Counter Sketch class,
contains contains s array for the multiple hash functions, k is the number of counter arrays and w being the size of each of these arrays,
Counterarr being the array counters will be added and the Active Counter has numbits and expbits each specifying the number of bits in number and exponent.
Countmin class and Counter Sketch class have record for taking in the flow and and its size and adding these to the counter arr and lookup (query) functionality
to get these recorded values according to the properties of each class. Active counter class has counter processing that adds 1 upto
given number and if the number bits are overflowed, the exponent bits are added by a chance.



Input tried:
The code generates flows and entries to be equal.
1) CountMin =  number of flows n=10000 (proj3input.txt) ,number of counter arrays, k=3, number of counters in each array w = 3000
2) Counter Sketch = number of flows n=10000 (proj3input.txt) ,number of counter arrays, k=3,number of counters in each array w = 3000
3) Active Counter =  number of number bits =16, number of exponent bits= 16 , n=1000000

Output:
Output files attached for the above input.

Steps To Run the project:
1) Import the project into an IDE such as Eclipse or IntelliJ and run the applrun.java file.
2) Enter 1 for CountMin, 2 for Counter Sketch and 3 for Active Counter.
3) Enter the number of counters and subsequently other parameters when prompted.
4) The output would print the Avg error among all flows and the next 100 lines consists of the flows of the largest
estimated sizes, the flow id, its estimated size and its true size. This is for the case of CountMin
and Counter Sketch. For Active Counter, the output is the final value of the active counter in decimal.
Also output files are simultaneously created and output is written into the files while printing.
