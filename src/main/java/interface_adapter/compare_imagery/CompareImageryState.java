package interface_adapter.compare_imagery;

/**
 * Everything the satellite comparison screen shows. Owner: Member D.
 */
public class CompareImageryState {

    private int imageWidth;
    private int imageHeight;
    private int[] beforePixels = new int[0];
    private int[] afterPixels = new int[0];
    private String beforeCaption = "";
    private String afterCaption = "";
    private String changeSummary = "";
    private String error = "";

    public int getImageWidth() {
        return imageWidth;
    }

    public void setImageWidth(int imageWidth) {
        this.imageWidth = imageWidth;
    }

    public int getImageHeight() {
        return imageHeight;
    }

    public void setImageHeight(int imageHeight) {
        this.imageHeight = imageHeight;
    }

    public int[] getBeforePixels() {
        return beforePixels;
    }

    public void setBeforePixels(int[] beforePixels) {
        this.beforePixels = beforePixels;
    }

    public int[] getAfterPixels() {
        return afterPixels;
    }

    public void setAfterPixels(int[] afterPixels) {
        this.afterPixels = afterPixels;
    }

    public String getBeforeCaption() {
        return beforeCaption;
    }

    public void setBeforeCaption(String beforeCaption) {
        this.beforeCaption = beforeCaption;
    }

    public String getAfterCaption() {
        return afterCaption;
    }

    public void setAfterCaption(String afterCaption) {
        this.afterCaption = afterCaption;
    }

    public String getChangeSummary() {
        return changeSummary;
    }

    public void setChangeSummary(String changeSummary) {
        this.changeSummary = changeSummary;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
