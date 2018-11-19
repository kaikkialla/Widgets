package banana.digital.widget;

public final class SoundMananger {

    private static SoundMananger sInstance;

    private SoundMananger() {}

    public static SoundMananger getInstance() {
        if (sInstance == null) {
            sInstance = new SoundMananger();
        }
        return sInstance;
    }


}