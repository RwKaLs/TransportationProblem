import java.util.Arrays;

public class TransportationProblem {

    private final int[] supplyInitial;
    private final int[] demandInitial;
    private int[] supply;
    private int[] demand;
    private final int[][] cost;

    public TransportationProblem(int[] supplyInitial, int[] demandInitial, int[][] cost) {
        this.supply = supplyInitial.clone();
        this.demand = demandInitial.clone();
        this.cost = new int[cost.length][];
        this.supplyInitial = supplyInitial.clone();
        this.demandInitial = demandInitial.clone();
        for (int i = 0; i < cost.length; i++) {
            this.cost[i] = cost[i].clone();
        }
    }

    private boolean isBalanced() {
        int totalSupply = 0;
        int totalDemand = 0;
        for (int supplyValue : supply) {
            totalSupply += supplyValue;
        }
        for (int demandValue : demand) {
            totalDemand += demandValue;
        }
        return totalSupply == totalDemand;
    }

    public int[][] northWestCornerMethod() {
        if (!isBalanced()) {
            throw new IllegalStateException("The problem is not balanced!");
        }
        int[][] solution = new int[supply.length][demand.length];
        int i = 0, j = 0;
        while (i < supply.length && j < demand.length) {
            int min = Math.min(supply[i], demand[j]);
            solution[i][j] = min;
            supply[i] -= min;
            demand[j] -= min;
            if (supply[i] == 0) {
                i++;
            }
            if (demand[j] == 0) {
                j++;
            }
        }
        supply = supplyInitial.clone();
        demand = demandInitial.clone();
        return solution;
    }

    public int[][] vogelsApproximationMethod() {
        if (!isBalanced()) {
            throw new IllegalStateException("The problem is not balanced!");
        }
        int[][] solution = new int[supply.length][demand.length];
        int[] rowDone = new int[supply.length];
        int[] collumnDone = new int[demand.length];
        int[] rowPenalties = new int[supply.length];
        int[] collumnPenalties = new int[demand.length];
        while(!Arrays.stream(rowDone).allMatch(b -> b == 1) && !Arrays.stream(collumnDone).allMatch(b -> b == 1)) {
            calculatePenalties(rowPenalties, collumnPenalties, rowDone, collumnDone);
            int penaltyType = 0; // 0 - row, 1 - collumn
            int idx = -1;
            int maxPenalty = -1;
            for (int i = 0; i < rowPenalties.length; i++) {
                if (rowDone[i] == 0 && rowPenalties[i] > maxPenalty) {
                    maxPenalty = rowPenalties[i];
                    idx = i;
                }
            }
            for (int j = 0; j < collumnPenalties.length; j++) {
                if (collumnDone[j] == 0 && collumnPenalties[j] > maxPenalty) {
                    maxPenalty = collumnPenalties[j];
                    idx = j;
                    penaltyType = 1;
                }
            }
            if (penaltyType == 0) {
                int minCostCollumn = -1;
                int minCost = Integer.MAX_VALUE;
                for (int j = 0; j < demand.length; j++) {
                    if (collumnDone[j] == 0 && cost[idx][j] < minCost) {
                        minCost = cost[idx][j];
                        minCostCollumn = j;
                    }
                }
                int quantity = Math.min(supply[idx], demand[minCostCollumn]);
                solution[idx][minCostCollumn] = quantity;
                supply[idx] -= quantity;
                demand[minCostCollumn] -= quantity;
                if (supply[idx] == 0) {
                    rowDone[idx] = 1;
                }
                if (demand[minCostCollumn] == 0) {
                    collumnDone[minCostCollumn] = 1;
                }
            } else {
                int minCostRow = -1;
                int minCost = Integer.MAX_VALUE;
                for (int i = 0; i < supply.length; i++) {
                    if (rowDone[i] == 0 && cost[i][idx] < minCost) {
                        minCost = cost[i][idx];
                        minCostRow = i;
                    }
                }
                int quantity = Math.min(supply[minCostRow], demand[idx]);
                solution[minCostRow][idx] = quantity;
                supply[minCostRow] -= quantity;
                demand[idx] -= quantity;
                if (supply[minCostRow] == 0) {
                    rowDone[minCostRow] = 1;
                }
                if (demand[idx] == 0) {
                    collumnDone[idx] = 1;
                }
            }
        }
        supply = supplyInitial.clone();
        demand = demandInitial.clone();
        return solution;
    }

    private void calculatePenalties(int[] rowPenalties, int[] collumnPenalties, int[] rowDone, int[] collumnDone) {
        for (int i = 0; i < supply.length; i++) {
            if (rowDone[i] == 0) {
                int[] costs = Arrays.stream(cost[i]).filter(c -> c != Integer.MAX_VALUE).sorted().toArray();
                rowPenalties[i] = costs.length > 1 ? costs[1] - costs[0] : 0;
            }
        }
        for (int j = 0; j < demand.length; j++) {
            if (collumnDone[j] == 0) {
                final int collumnIndex = j;
                int[] costs = Arrays.stream(cost).mapToInt(row -> row[collumnIndex]).filter(c -> c != Integer.MAX_VALUE).sorted().toArray();
                collumnPenalties[j] = costs.length > 1 ? costs[1] - costs[0] : 0;
            }
        }
    }

    public int[][] russellsApproximationMethod() {
        if (!isBalanced()) {
            throw new IllegalStateException("The problem is not balanced!");
        }
        int[][] solution = new int[supply.length][demand.length];
        int[] rowDone = new int[supply.length];
        int[] collumnDone = new int[demand.length];
        while(!Arrays.stream(rowDone).allMatch(b -> b == 1) && !Arrays.stream(collumnDone).allMatch(b -> b == 1)) {
            int[] u = new int[supply.length];
            int[] v =  new int[demand.length];
            for (int i = 0; i < supply.length; i++) {
                for (int j = 0; j < demand.length; j++) {
                    if (rowDone[i] == 0 && collumnDone[j] == 0) {
                        if (u[i] < cost[i][j]) {
                            u[i] = cost[i][j];
                        }
                        if (v[j] < cost[i][j]) {
                            v[j] = cost[i][j];
                        }
                    }
                }
            }
            int maxI = -1;
            int maxJ = -1;
            int maxCostDifference = Integer.MAX_VALUE;
            for (int i = 0; i < supply.length; i++) {
                for (int j = 0; j < demand.length; j++) {
                    if (rowDone[i] == 0 && collumnDone[j] == 0) {
                        int costDifference = cost[i][j] - u[i] - v[j];
                        if (costDifference < maxCostDifference) {
                            maxCostDifference = costDifference;
                            maxI = i;
                            maxJ = j;
                        }
                    }
                }
            }
            if (maxI != -1 && maxJ != -1) {
                int allocation = Math.min(supply[maxI], demand[maxJ]);
                solution[maxI][maxJ] = allocation;
                supply[maxI] -= allocation;
                demand[maxJ] -= allocation;
                if (supply[maxI] == 0) {
                    rowDone[maxI] = 1;
                }
                if (demand[maxJ] == 0) {
                    collumnDone[maxJ] = 1;
                }
            } else {
                break;
            }
        }
        supply = supplyInitial.clone();
        demand = demandInitial.clone();
        return solution;
    }

    public int countSolution(int[][] solution) {
        int result = 0;
        for (int i = 0; i < solution.length; i++) {
            for (int j = 0; j < solution[i].length; j++) {
                result += solution[i][j] * cost[i][j];
            }
        }
        return result;
    }

    public void printTable(int[][] cost, int[] supply, int[] demand) {
        System.out.print("      ");
        for (int j = 0; j < demand.length; j++) {
            System.out.printf("B%d     ", j + 1);
        }
        System.out.println("Supply ");
        System.out.println("-------------------------------------------------");
        for (int i = 0; i < cost.length; i++) {
            System.out.printf("A%d |", i + 1);
            for (int j = 0; j < cost[i].length; j++) {
                System.out.printf("  %2d   ", cost[i][j]);
            }
            System.out.printf("  %3d", supply[i]);
            System.out.println();
        }
        System.out.println("-------------------------------------------------");
        System.out.print("Demand");
        for (int demandValue : demand) {
            System.out.printf("  %2d  ", demandValue);
        }
        System.out.println();
    }
}