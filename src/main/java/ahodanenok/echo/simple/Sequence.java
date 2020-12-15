package ahodanenok.echo.simple;

import org.springframework.beans.factory.BeanNameAware;

import java.beans.ConstructorProperties;

public final class Sequence implements BeanNameAware {

    private int num;
    private final int step;
    private boolean resume;
    private String name;

    @ConstructorProperties({"startFrom", "step"})
    public Sequence(int startFrom, int step) {
        this.num = startFrom;
        this.step = step;
    }

    public void setResume(boolean resume) {
        this.resume = resume;
    }

    @Override
    public void setBeanName(String name) {
        this.name = name;
    }

    public int next() {
        int result = num;
        num += step;
        return result;
    }

    private void init() {
        checkName();
        System.out.println(getClass().getSimpleName() + ": Initializing sequence '" + name + "'");
        if (!resume) {
            return;
        }

        // todo: load current state from a file
    }

    private void destroy() {
        checkName();
        System.out.println(getClass().getSimpleName() + ": Destroying sequence '" + name + "'");

        // todo: save current state to a file
    }

    private void checkName() {
        if (name == null || name.isEmpty()) {
            throw new IllegalStateException("Name is not set");
        }
    }
}
