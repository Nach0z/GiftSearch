package net.helix.bennis.util.skins;

public class SkinPair {
    private String closedPresent;
    private String openPresent;
    private String skinName;
    public SkinPair(String closedPresentSkinBase64, String openPresentSkinBase64, String skinName) {
        this.openPresent = openPresentSkinBase64;
        this.closedPresent = closedPresentSkinBase64;
        this.skinName = skinName;
    }

    public String getClosed() {
        return SkinURL.GetUrl(closedPresent);
    }

    public String getOpened() {
        return SkinURL.GetUrl(openPresent);
    }
    public String getSkinName() {
        return skinName;
    }
}
