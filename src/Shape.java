public class Shape {

    private Tetrominoes pieceShape;
    private int coords[][];
    private static final int[][][] coordsTable = new int[][][]{
            {{0, 0}, {0, 0}, {0, 0}, {0, 0}},
            {{0, -1}, {0, 0}, {-1, 0}, {-1, 1}},
            {{0, -1}, {0, 0}, {1, 0}, {1, 1}},
            {{0, -1}, {0, 0}, {0, 1}, {0, 2}},
            {{-1, 0}, {0, 0}, {1, 0}, {0, 1}},
            {{0, 0}, {1, 0}, {0, 1}, {1, 1}},
            {{-1, -1}, {0, -1}, {0, 0}, {0, 1}},
            {{1, -1}, {0, -1}, {0, 0}, {0, 1}}
    };

    public Shape() { //
        coords = new int[4][2];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 2; j++) {
                coords[i][j] = coordsTable[Tetrominoes.NoShape.ordinal()][i][j];
            }
        }
        pieceShape = Tetrominoes.NoShape;
    }

    public void setShape(Tetrominoes shapeType) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 2; j++) {
                coords[i][j] = coordsTable[shapeType.ordinal()][i][j];
            }
        }
        pieceShape = shapeType;
    }

    public void setX(int index, int x) {
        coords[index][0] = x;
    }

    public void setY(int index, int y) {
        coords[index][1] = y;
    }

    public int getX(int index) {
        return coords[index][0];
    }

    public int getY(int index) {
        return coords[index][1];
    }

    public Tetrominoes getShape() {
        return pieceShape;
    }

    public void setRandomShape() {
        int random = (int) (Math.random() * 7 + 1);//Al no estar el 0, no esta Noshape.
        Tetrominoes[] values = Tetrominoes.values();
        setShape(values[random]);
    }

    public Shape rotateRight() {
        Shape s = new Shape();
        s.setShape(pieceShape);
        if (s.getShape() != Tetrominoes.SquareShape)
        for (int i = 0; i < 4; i++) {
            s.setX(i, getY(i));
            s.setY(i, -getX(i));
        }
        return s;
    }

    public int maxX() {
        int n = 0;
        for (int i = 0; i < 4; i++) {
            if (getX(i) > n) {
                n = getX(i);
            }
        }
        return n;
    }

    public int minX() {
        int n = 10;
        for (int i = 0; i < 4; i++) {
            if (getX(i) < n) {
                n = getX(i);
            }
        }
        return n;
    }

    public int maxY() {
        int n = 0;
        for (int i = 0; i < 4; i++) {
            if (getY(i) > n) {
                n = getY(i);
            }
        }
        return n;
    }

    public int minY() {
        int n = 2;
        for (int i = 0; i < 4; i++) {
            if (getY(i) < n) {
                n = getY(i);
            }
        }
        return n;
    }

}
