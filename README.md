# Transportation Problem Solver

This Java program provides a solution to transportation problems using three different methods: the North-West Corner Method, Vogel's Approximation Method, and Russell's Approximation Method.

## Getting Started

### Prerequisites
- Java Development Kit (JDK) installed

### Usage
1. Compile the code:
   ```bash
   javac Main.java TransportationProblem.java
   ```

2. Run the program:
   ```bash
   java Main
   ```

3. Follow the on-screen prompts to input the number of suppliers, demand coefficients, supply coefficients, and the cost matrix.

4. The program will display the transportation table, and provide solutions using the North-West Corner Method, Vogel's Approximation Method, and Russell's Approximation Method.

## Classes

### `Main`
- The main class responsible for user input and interaction.
- Collects information about the transportation problem from the user, creates a `TransportationProblem` instance, and displays the results of various solution methods.

### `TransportationProblem`
- Represents a transportation problem with methods to solve it using different approaches.
- Three solution methods are implemented:
    1. **North-West Corner Method**
    2. **Vogel's Approximation Method**
    3. **Russell's Approximation Method**
- The class also contains methods for checking if the problem is balanced, calculating penalties, and printing the transportation table.

## TransportationProblem Methods

### `northWestCornerMethod()`
- Implements the North-West Corner Method for solving transportation problems.

### `vogelsApproximationMethod()`
- Implements Vogel's Approximation Method for solving transportation problems.

### `russellsApproximationMethod()`
- Implements Russell's Approximation Method for solving transportation problems.

### `countSolution(int[][] solution)`
- Calculates the cost of a given solution.

### `printTable(int[][] cost, int[] supply, int[] demand)`
- Prints the transportation table.

## Contributors
- Aliya Bogapova
- Mikita Drazdou
- Egor Meganov
- Egor Solodovnikov
## License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.