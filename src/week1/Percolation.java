package src.week1;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;


/**
 * Implementation of the percolation data structure.
 * @author Manas
 */
public class Percolation {

    private final WeightedQuickUnionUF quickUnionStructure;
    private final WeightedQuickUnionUF quickUnionStructureForIsFull;

    private int gridSize;
    private final boolean[][] gridstatus;  // to check if the site is open.

    private int virtualTopSite;
    private int virtualBottomSite;
    private int openSites;


    public Percolation(int n) { // create N-by-N grid, with all the sites blocked
        if (n <= 0) {
            throw new IllegalArgumentException("The size of the grid must be at least 1");
        }
        gridSize = n;
        gridstatus = new boolean[n][n];

        quickUnionStructure = new WeightedQuickUnionUF(n * n + 2);  // elements are 0 to N + 1.
        quickUnionStructureForIsFull = new WeightedQuickUnionUF(n * n + 1); // elememts are 0 to N.

        virtualTopSite = 0; // index for the virtal top side in quickUnionStructure.
        virtualBottomSite = n * n + 1; //index for virtual bottom side in the quickUnionStructure.
    }

    public void open(int row, int col) {    // open site (row , column ) if it is not open already
        int fieldIndex = getSingleDimensionIndex(row, col);


        if (!isOpen(row, col)) {
            if (row == 1) { // if it is the first row, irrespective of the column.
                quickUnionStructure.union(virtualTopSite, fieldIndex);
                quickUnionStructureForIsFull.union(virtualTopSite, fieldIndex);
            }
            if (row == gridSize) { // if it is the last row, irresepective of whatever column it is.
                quickUnionStructure.union(virtualBottomSite, fieldIndex);
            }

            connectIfOpen(fieldIndex, row + 1, col);
            connectIfOpen(fieldIndex, row - 1, col);
            connectIfOpen(fieldIndex, row, col - 1);
            connectIfOpen(fieldIndex, row, col + 1);

            gridstatus[row - 1][col - 1] = true;
            openSites++;

        }
    }

    public boolean isOpen(int row, int col) {   // is site (row i, column j) open?
        return gridstatus[row - 1][col - 1];
    }

    public boolean isFull(int row, int col) {   // is site (row i, column j) connected to top?
        if (isOpen(row, col)) { // first, is it open?
            int fieldIndex = getSingleDimensionIndex(row, col);
            return quickUnionStructureForIsFull.connected(virtualTopSite, fieldIndex);
        }
        return false;
    }

    public boolean percolates() {   // does the system percolate?
        return quickUnionStructure.connected(virtualTopSite, virtualBottomSite);
    }

    public int numberOfOpenSites() {   // number of open sites?
        return openSites;
    }

    private void connectIfOpen(int fieldIndex, int i, int j) {
        try {
            if (isOpen(i, j)) // if adjacent sites are open, connect current site to them. also connect the current site with adjacent site in the quickUnionStructureForIsFull.
            {
                int neighbourFieldIndex = getSingleDimensionIndex(i, j);
                quickUnionStructure.union(neighbourFieldIndex, fieldIndex);
                quickUnionStructureForIsFull.union(neighbourFieldIndex, fieldIndex);
            }
        } catch (IndexOutOfBoundsException e) {
            // Ignore the adjancent rows and cols that are outside the grid.
        }
    }

    private int getSingleDimensionIndex(int row, int col) {
        checkIfAcceptableRowCol(row, col);
        return (row - 1) * gridSize + col;
    }

    private void checkIfAcceptableRowCol(int row, int col)
    {
        if(row < 1 || col < 1)
        {
            throw new IllegalArgumentException("Row or Column cannot be less than 1");
        }
    }

}