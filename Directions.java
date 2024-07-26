public enum Directions {
    Up(0, -1), UpRight(1, -1), Right(1, 0), DownRight(1,1), Down(0, 1), DownLeft(-1, 1), Left(-1, 0), UpLeft(-1, -1);

    private int ShiftX;
    private int ShiftY;
    Directions(int shiftX, int shiftY){
        this.ShiftX = shiftX;
        this.ShiftY = shiftY;
    }

    public int getShiftX() {
        return ShiftX;
    }

    public int getShiftY() {
        return ShiftY;
    }
}
