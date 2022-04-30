package net.helix.bennis.util.database;

public class Queries {
    private static final String GET_GIFT_LOCATIONS = "SELECT X_COORD, Y_COORD, Z_COORD FROM dbo.GiftBlocks";

    public static String BuildGetGiftLocationsQuery() {
        return GET_GIFT_LOCATIONS;
    }
}
