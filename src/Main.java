import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.print("The number of suppliers: ");
        int numOfSupply = in.nextInt();
        System.out.println("A vector of coefficients of supply:");
        int[] supplyInput = new int[numOfSupply];
        for (int i = 0; i < numOfSupply; i++) {
            supplyInput[i] = in.nextInt();
        }
        System.out.print("The number of demands: ");
        int numOfDemand = in.nextInt();
        System.out.println("A vector of coefficients of demand:");
        int[] demandInput = new int[numOfDemand];
        for (int i = 0; i < numOfDemand; i++) {
            demandInput[i] = in.nextInt();
        }
        System.out.println("A matrix of coefficients of costs:");
        int[][] cost = new int[numOfSupply][numOfDemand];
        for (int i = 0; i < numOfSupply; i++) {
            for (int j = 0; j < numOfDemand; j++) {
                cost[i][j] = in.nextInt();
            }
        }
        TransportationProblem problem = new TransportationProblem(supplyInput, demandInput, cost);
        problem.printTable(cost, supplyInput, demandInput);
        int[][] nwSolution = problem.northWestCornerMethod();
        System.out.printf("North-West Corner Method: z = %d\n", problem.countSolution(nwSolution));
        int[][] vSolution = problem.vogelsApproximationMethod();
        System.out.printf("Vogel's Approximation Method: z = %d\n", problem.countSolution(vSolution));
        int[][] rSolution = problem.russellsApproximationMethod();
        System.out.printf("Russel's Approximation Method: z = %d\n", problem.countSolution(rSolution));
        in.close();
    }
}
