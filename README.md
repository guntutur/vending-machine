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

Still refer to `vending.md`, we assume for condition of Business Rule number 3 :

``3. Conditions to determine change possibility 
        Change is impossible if the coins contained in the Vending Machine’s vault is in the below conditions:
        - 10 JPY coin: Less than 9 coins
        - 100 JPY coin: Less than 4 coins
        In case if vending machine running out of 10 JPY coin, then 50 JPY, 100 JPY, 500 JPY coins cannot be used.
        In case if vending machine running out of 100 JPY coin, then 500 JPY coins cannot be used.``
        
as interpreted as follows : 
- vending machine running out of 10 JPY coin means that 10 JPY coin in stock **IS LESS THAN 9 coins** thus *system won't accept 50, 100, 500 coin denomination*
- vending machine running out of 100 JPY coin means that 100 JPY coin in stock **IS LESS THAN 4 coins** thus *system won't accept 500 coin denomination*

# Build Test, and Run
This application build using Java and Maven, we ship Maven Wrapper along the way to seamlessly build on any platform.
Test Suite would automatically executed upon build with the result.

## Build and Test
To build, from the project root : 
- linux or macos, execute `./bin/build`
- windows or unix based, execute `./bin/build_windows`

## To run, from the project root
-  execute `./bin/run_vending`
