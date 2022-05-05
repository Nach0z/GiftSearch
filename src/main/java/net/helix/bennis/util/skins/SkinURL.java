package net.helix.bennis.util.skins;

import static net.helix.bennis.util.Constants.MINECRAFT_TEXTURE_PREFIX;

public class SkinURL {

    public static String GetUrl(String skinID) {
        return MINECRAFT_TEXTURE_PREFIX + skinID;
    }
}
