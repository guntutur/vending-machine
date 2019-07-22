# Vending Machine
Create a console program that imitate the Vending Machine, receives the standard input and generate the standard output.

# Problem Definition

Based on `vending.md`, we assume there is no interface to manage money and goods in-stock, 
so this case would be hardcoded by default with condition as follows :

## Money

- 10 JPY = 15
- 100 JPY = 10

## Goods In-Stock

- Canned Coffee (120 JPY) = 3
- Water PET Bottle (100 JPY) = 0
- Sports Drinks (150 JPY) = 5

# Build Test, and Run
This application build using Java and Maven, we ship Maven Wrapper along the way to seamlessly build on any platform.
Test Suite would automatically executed upon build with the result.

## Build and Test
To build, from the project root : 
- linux or macos, execute `./bin/build`
- windows or unix based, execute `./bin/build_windows`

## To run, from the project root
-  execute `./bin/run_vending`
