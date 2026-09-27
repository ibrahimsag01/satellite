package satellite;

import java.io.*;

public class Satellite {

    static int[][] oldImage;
    static int[][] newImage;
    static int noOfRows, noOfCols;

    public static void main(String[] args) {
        try {
            process();
        } catch (IOException e) {
            System.err.println("Error reading input file: " + e.getMessage());
        }
    }

    static void process() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("input.txt"));
        readSizes(reader);
        oldImage = readImage(reader);
        newImage = readImage(reader);
        reader.close();
        printResult();
    }

    static void readSizes(BufferedReader reader) throws IOException {
        noOfRows = Integer.parseInt(reader.readLine().trim());
        noOfCols = Integer.parseInt(reader.readLine().trim());
    }

    // Single method used for reading BOTH the old and the new image.
    static int[][] readImage(BufferedReader reader) throws IOException {
        int[][] img = new int[noOfRows][noOfCols];
        for (int row = 0; row < noOfRows; row++) {
            String[] parts = reader.readLine().trim().split("\\s+");
            for (int col = 0; col < noOfCols; col++)
                img[row][col] = Integer.parseInt(parts[col]);
        }
        return img;
    }

    static void printResult() {
        int x1 = findBound(true, true);
        int x2 = findBound(true, false);
        int y1 = findBound(false, true);
        int y2 = findBound(false, false);
        System.out.println(x1 > x2 || y1 > y2
                ? "The two images are the same"
                : (x1 + 1) + " " + (y1 + 1) + " " + (x2 + 1) + " " + (y2 + 1));
    }

    // Single method used for determining x1, x2, y1 and y2:
    // isRow -> look at rows or columns; fromStart -> scan from the beginning or from the end.
    static int findBound(boolean isRow, boolean fromStart) {
        int size = isRow ? noOfRows : noOfCols;
        int step = fromStart ? 1 : -1;
        int idx = fromStart ? 0 : size - 1;
        while (idx >= 0 && idx < size && isEqualLine(isRow, idx)) idx += step;
        return idx;
    }

    // Single method used for checking whether a row or a column is equal in both images.
    static boolean isEqualLine(boolean isRow, int index) {
        int size = isRow ? noOfCols : noOfRows;
        for (int i = 0; i < size; i++) {
            int r = isRow ? index : i;
            int c = isRow ? i : index;
            if (oldImage[r][c] != newImage[r][c]) return false;
        }
        return true;
    }
}
