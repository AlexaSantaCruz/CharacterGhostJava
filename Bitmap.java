public class Bitmap{
    private final int rows = 7;
    private final int columns = 7;
    private int[][] pixels;
    public Bitmap(int[][] values){
        pixels = new int[7][7];
        for (int i = 0; i < columns; i++) {
            for (int j = 0; j < rows; j++) {
                pixels[i][j] = values[i][j];
            }
        }
    }
    public int getPixel(int x, int y){
        return pixels[x][y];
    }

}