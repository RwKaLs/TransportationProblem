import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TransportationProblemTest {

    private final int[] supply = {160, 140, 170};
    private final int[] demand = {120, 50, 190, 110};
    private final int[][] cost = {
            {7, 8, 1, 2},
            {4, 5, 9, 8},
            {9, 2, 3, 6}
    };
    private final TransportationProblem problem = new TransportationProblem(supply, demand, cost);

    @Test
    void northWestCornerMethod() {
        int[] supply = {160, 140, 170};
        int[] demand = {120, 50, 190, 110};
        int[][] cost = {
                {7, 8, 1, 2},
                {4, 5, 9, 8},
                {9, 2, 3, 6}
        };
        TransportationProblem problem = new TransportationProblem(supply, demand, cost);
        problem.printTable(cost, supply, demand);
        System.out.println("z = " + problem.countSolution(problem.northWestCornerMethod()));
        System.out.println();
        assertEquals(3220, problem.countSolution(problem.northWestCornerMethod()));
    }

    @Test
    void vogelsApproximationMethod() {
        problem.printTable(cost, supply, demand);
        System.out.println("z = " + problem.countSolution(problem.vogelsApproximationMethod()));
        System.out.println();
        assertEquals(1390, problem.countSolution(problem.vogelsApproximationMethod()));
        // note that results could vary in the cases of equality differences on some step
    }

    @Test
    void russellsApproximationMethod() {
        problem.printTable(cost, supply, demand);
        System.out.println("z = " + problem.countSolution(problem.russellsApproximationMethod()));
        System.out.println();
        assertEquals(1530, problem.countSolution(problem.russellsApproximationMethod()));
    }
}