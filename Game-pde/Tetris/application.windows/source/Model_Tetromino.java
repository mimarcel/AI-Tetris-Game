
public class Model_Tetromino extends Model_Abstract
{
    protected String code;
    protected short colorT;
    protected short[][][] pieces; // An array with 4 matrix's. Each matrix is a position of the tetromino (0 degrees, 90 degrees, 180 degrees and 270 degrees).
    protected short currentRotationNumber = 0;
    protected boolean[] rotationsDuplicated;
    protected int maxHeight;

    /**
     * Create tetromino.
     */
    public Model_Tetromino(String code, short colorT, short pieces[][]) throws Model_Exception {

        // Matrix should be square.
        for (short[] piece : pieces) {
            if (piece.length != pieces.length) {
                throw new Model_Exception("");
            }
        }

        this.code = code;
        this.colorT = colorT;

        short[][] pieces90 = this.rotateMatrixToRight90Degrees(pieces);
        short[][] pieces180 = this.rotateMatrixToRight90Degrees(pieces90);
        short[][] pieces270 = this.rotateMatrixToRight90Degrees(pieces180);
        this.pieces = new short[4][][];
        this.pieces[0] = this.removeZerosFromMargins(pieces);
        this.pieces[1] = this.removeZerosFromMargins(pieces90);
        this.pieces[2] = this.removeZerosFromMargins(pieces180);
        this.pieces[3] = this.removeZerosFromMargins(pieces270);

        // Check if rotations are duplicated.
        this.rotationsDuplicated = new boolean[4];
        this.maxHeight = 0;
        for (int i = 0; i < 4; i++) {
            if (this.pieces[i].length > this.maxHeight) {
                this.maxHeight = this.pieces[i].length;
            }
            int duplicatedTimes = 0;
            for (int j = 0; j < i; j++) {
                if (i != j && this.matrixAreTheSame(this.pieces[i], this.pieces[j])) {
                    duplicatedTimes++;
                    break;
                }
            }
            this.rotationsDuplicated[i] = duplicatedTimes != 0;
        }

        this.currentRotationNumber = 0;
    }

    protected boolean matrixAreTheSame(short[][] m1, short[][] m2) {
        if (m1.length != m2.length) {
            return false;
        }
        for (int i = 0; i < m1.length; i++) {
            if (m1[i].length != m2[i].length) {
                return false;
            }
            for (int j = 0; j < m1[i].length; j++) {
                if (m1[i][j] != m2[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean currentRotationIsDuplicated() {
        return this.rotationsDuplicated[this.currentRotationNumber];
    }

    /**
     * Copy tetromino.
     */
    public Model_Tetromino(Model_Tetromino tetromino) {
        this.code = tetromino.code;
        this.colorT = tetromino.colorT;
        this.currentRotationNumber = tetromino.currentRotationNumber;
        this.pieces = new short[4][][];
        this.rotationsDuplicated = new boolean[4];
        for (int k = 0; k < 4; k++) {
            this.rotationsDuplicated[k] = tetromino.rotationsDuplicated[k];
            this.pieces[k] = new short[tetromino.pieces[k].length][];
            for (int i = 0; i < tetromino.pieces[k].length; i++) {
                this.pieces[k][i] = new short[tetromino.pieces[k][i].length];
                for (int j = 0; j < tetromino.pieces[k][i].length; j++) {
                    this.pieces[k][i][j] = tetromino.pieces[k][i][j];
                }
            }
        }
    }

    /**
     * Rotate the squared matrix pieces by 90 degrees.
     */
    protected short[][] rotateMatrixToRight90Degrees(short[][] pieces) {
        short[][] newPieces = new short[pieces.length][pieces[0].length];
        for (int i = 0; i < pieces.length; i++) {
            for (int j = 0; j < pieces[i].length; j++) {
                newPieces[i][j] = pieces[j][pieces.length - 1 - i];
            }
        }
        return newPieces;
    }

    /**
     * Remove zeros from margins. The matrix is supposed to have the same number of columns per each line.
     */
    private short[][] removeZerosFromMargins(short[][] matrix) {
        int startTopLine = 0;
        // Count how many lines at the top contain only zeros.
        for (startTopLine = 0; startTopLine < matrix.length; startTopLine++) {
            boolean isBadLine = true;
            for (int j = 0; j < matrix[startTopLine].length; j++) {
                if (matrix[startTopLine][j] == 1) {
                    isBadLine = false;
                    break;
                }
            }
            if (!isBadLine) {
                break;
            }
        }

        int endTopLine = 0;
        // Count how many lines at the bottom contain only zeros.
        for (endTopLine = matrix.length - 1; endTopLine >= 0; endTopLine--) {
            boolean isBadLine = true;
            for (int j = 0; j < matrix[endTopLine].length; j++) {
                if (matrix[endTopLine][j] == 1) {
                    isBadLine = false;
                    break;
                }
            }
            if (!isBadLine) {
                break;
            }
        }

        int startLeftColumn = 0;
        // Count how many lines at the left contain only zeros.
        for (startLeftColumn = 0; startLeftColumn < matrix.length; startLeftColumn++) {
            boolean isBadColumn = true;
            for (int i = 0; i < matrix.length; i++) {
                if (matrix[i][startLeftColumn] == 1) {
                    isBadColumn = false;
                    break;
                }
            }
            if (!isBadColumn) {
                break;
            }
        }

        int endRightColumn = 0;
        // Count how many lines at the left contain only zeros.
        for (endRightColumn = matrix.length - 1; endRightColumn >= 0; endRightColumn--) {
            boolean isBadColumn = true;
            for (int i = 0; i < matrix.length; i++) {
                if (matrix[i][endRightColumn] == 1) {
                    isBadColumn = false;
                    break;
                }
            }
            if (!isBadColumn) {
                break;
            }
        }

        int goodLines = endTopLine - startTopLine + 1;
        int goodColumns = endRightColumn - startLeftColumn + 1;

        short[][] newM = new short[goodLines][goodColumns];
        for (int i = startTopLine; i <= endTopLine; i++) {
            for (int j = startLeftColumn; j <= endRightColumn; j++) {
                newM[i - startTopLine][j - startLeftColumn] = matrix[i][j];
            }
        }

        return newM;
    }

    public String toString() {
        String s = "";
        s += this.code + "\n";
        int degrees = 0;
        for (short[][] p : this.pieces) {
            s += degrees + ":" + (this.rotationsDuplicated[degrees / 90] ? "(duplicated)" : "") + "\n";
            for (int i = 0; i < p.length; i++) {
                for (int j = 0; j < p[0].length; j++) {
                    s += p[i][j] + " ";
                }
                s += "\n";
            }
            degrees += 90;
        }
        return s;
    }

    public String toStringOnlyCurrentPosition() {
        String s = "";
        s += this.code + "\n";
        short[][] p = this.pieces[this.currentRotationNumber];
        for (int i = 0; i < p.length; i++) {
            for (int j = 0; j < p[0].length; j++) {
                s += p[i][j] + " ";
            }
            s += "\n";
        }
        return s;
    }

    public boolean equals(Object obj) {
        Model_Tetromino t = (Model_Tetromino) obj;
        return t != null && t.code.equals(this.code);
    }

    /**
     * Get current matrix.
     */
    public short[][] getCurrentRotation() {
        return this.pieces[this.currentRotationNumber];
    }

    public short getColor() {
        return this.colorT;
    }

    public String getCode() {
        return code;
    }

    /**
     * Rotate tetromino to right.
     */
    public void rotateToRight() {
        this.currentRotationNumber = (short) ((this.currentRotationNumber + 1) % 4);
    }

    /**
     * Rotate tetromino to left.
     */
    public void rotateToLeft() {
        this.currentRotationNumber++;
        this.currentRotationNumber %= 4;
    }

    public short getCurrentRotationNumber() {
        return currentRotationNumber;
    }

    public void setCurrentRotationNumber(short currentRotationNumber) {
        this.currentRotationNumber = currentRotationNumber;
    }

    public int getMaxHeightOfAnyRotationOfThisTetromino() {
        return maxHeight;
    }
}
