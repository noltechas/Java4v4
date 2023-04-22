public enum ShotType {
    LAYUP,
    DUNK,
    CLOSE_SHOT,
    MID_RANGE_SHOT,
    THREE_POINT,
    MID_RANGE,
    POST_UP,
    POST_FADE;

    // Define optimal x and y positions for each shot type
    private static final double LAYUP_X = 25.0;
    private static final double LAYUP_Y = 5.0;
    private static final double DUNK_X = 25.0;
    private static final double DUNK_Y = 3.0;
    private static final double MID_RANGE_X = 25.0;
    private static final double MID_RANGE_Y = 10.0;
    private static final double THREE_POINT_X = 25.0;
    private static final double THREE_POINT_Y = 23.75;

    public double getOptimalXPosition() {
        switch (this) {
            case LAYUP:
                return LAYUP_X;
            case DUNK:
                return DUNK_X;
            case MID_RANGE:
                return MID_RANGE_X;
            case THREE_POINT:
                return THREE_POINT_X;
            default:
                throw new IllegalStateException("Invalid ShotType: " + this);
        }
    }

    public double getOptimalYPosition() {
        switch (this) {
            case LAYUP:
                return LAYUP_Y;
            case DUNK:
                return DUNK_Y;
            case MID_RANGE:
                return MID_RANGE_Y;
            case THREE_POINT:
                return THREE_POINT_Y;
            default:
                throw new IllegalStateException("Invalid ShotType: " + this);
        }
    }
}