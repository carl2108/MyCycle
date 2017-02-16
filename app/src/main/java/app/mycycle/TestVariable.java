package app.mycycle;

/**
 * Created by carloconnor on 16/02/17.
 */

public class TestVariable {

    private int i;
    private ChangeListener listener;


    public TestVariable() {
        this.i = 0;
    }

    public TestVariable(int i) {
        this.i = i;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public ChangeListener getListener() {
        return listener;
    }

    public void setListener(ChangeListener listener) {
        this.listener = listener;
    }

    public interface ChangeListener {
        void onChange();
    }

}
