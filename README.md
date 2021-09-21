# Concurrency_in_JAVA_using_Simulation
Practicing multithreading in JAVA using a simulation problem of delivery drivers. This program shows knowledge of concurrency in JAVA.

Program will simulate the events that occur in the day of the several drivers that work for a delivery company. There are three events that can occur throughout the day: a driver can make a delivery, a
driver can fill up their vehicle with gas, and a driver can load up on new deliverables at the company
warehouse. There is a single gas station in town that the drivers can use, and this gas station has only
two pumps available for use. The warehouse only has a single loading dock, and so only a single driver
can load up at the warehouse at one time.

Solution is implemented using JAVA concurrency library. Each driver is given their
own thread, allowing program to simulate each driver completing their tasks simultaneously. Input file is used to provide events of day. Each event has different time standardized according to below table.

| Driver type | Delivery time (d) | Time to fill up on gas (g) | Time to load at warehouse (w) |
| --- | --- | --- | --- |
| Personal driver (P) | 200 – 1300 | 800 | 1000 |
| Business driver (B) | 1000 – 3000 | 1500 | 2100 |
| Long-distance driver (L) | 2000 – 6500 | 3000 | 4000 |

The gas station and warehouse events are treated as limited, non-consumable resources. Since there
is a limit to the number of drivers that can use these resources at the same time, these resources are accessed using semaphores to ensure mutual exclusion. And all outputs are also made thread safe.

A First come first served/FIFO scheduling policy is also implemented to increase efficiency, to select which route to start next. Output shows events as they are completed.
