package Logic;

public class MatchTable
{
    private static int defaultRow = 3;
    private int row = defaultRow;
    private static int defaultColumn = 3;
    private int column = defaultColumn;
    private Cell[][] table = new Cell[row][column];
    private int numOfXOsForWin = 3;
    private int numOfTawInTheTable = 0;
    private int[] coordinateOfLastTaw = new int[2];
    private int[] coordinateOfSecondFromTheEndTaw = new int[2];

    public Cell[][] getTable()
    {
        return this.table;
    }

    public static void changeDefaultTable(int row, int column)
    {
        MatchTable.defaultRow = row;
        MatchTable.defaultColumn = column;
    }

    public int getColumn()
    {
        return this.column;
    }

    public int getRow()
    {
        return this.row;
    }

    public int getNumOfTawInTheTable()
    {
        return this.numOfTawInTheTable;
    }

    public void increaseNumOfTheTawInTheTable()
    {
        this.numOfTawInTheTable ++;
    }

    public void decreaseNumOfTheTawInTheTable()
    {
        this.numOfTawInTheTable --;
    }

    public void setCoordinateOfLastAndSecondFromTheEndTaw(int row, int column)
    {
        this.coordinateOfSecondFromTheEndTaw[0] = this.coordinateOfLastTaw[0];
        this.coordinateOfSecondFromTheEndTaw[1] = this.coordinateOfLastTaw[1];
        this.coordinateOfLastTaw[0] = row;
        this.coordinateOfLastTaw[1] = column;
    }

    public void removeTheLastTaw()
    {
        int row = this.coordinateOfLastTaw[0];
        int column = this.coordinateOfLastTaw[1];
        this.table[row][column] = null;
        this.coordinateOfLastTaw[0] = this.coordinateOfSecondFromTheEndTaw[0];
        this.coordinateOfLastTaw[1] = this.coordinateOfSecondFromTheEndTaw[1];
        this.decreaseNumOfTheTawInTheTable();
    }

    public boolean checkWinCondition(Player player)
    {
        int row = this.getRow();
        int column = this.getColumn();
        char typeOFTaw = player.getTypeOFTaw();
        if(row > 3 && column > 3)
        {
            this.numOfXOsForWin = 4;
        }
        char[][] table = new char[row][column];
        for (int i=0; i < row; i++)
        {
            for (int j=0;j < column; j++)
            {
                //table[i][j] = this.table[i][j]; //TODO
            }
        }
        return winnerPronounced(table, typeOFTaw);
    }

    public boolean winnerPronounced(char[][] table, char typeOFTaw)
    {
        int numOfRows = getRow();
        int numOfColumns = (getColumn() + 1) / 2;
        int[] stateX = {1, 0, 1, -1};
        int[] stateY = {0, 1, 1, -1};
        for (int i = 0;i < numOfRows;i++)
        {
            for (int j=  0;j < numOfColumns;j++)
            {
                for (int k=0;k < 4;k++)
                {
                    int nmOfTawConsecutive = 0;
                    for (int m = 0;m < this.numOfXOsForWin;m++)
                    {
                        int row = i + m * stateX[k];
                        int column = j + m * stateY[k];
                        if (row >= 0 && row < numOfRows && column >= 0 && column < numOfColumns && table[row][column] == typeOFTaw)
                        {
                            nmOfTawConsecutive ++;
                        }
                    }
                    if (nmOfTawConsecutive == numOfXOsForWin)
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
