package cui.shibing.argvresolver;

import cui.shibing.argvresolver.exception.ResolveException;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;
import java.util.function.Consumer;

/**
 * 参数定义
 */
@Data
@NoArgsConstructor
public class OptionDefinition {
    private String pattern;
    private int minOption = 0;
    private int maxOption = Integer.MAX_VALUE;

    private Consumer<Option> onOption = option -> {};

    public OptionDefinition(String pattern,Consumer<Option> onOption) {
        if (pattern == null || pattern.equals("")) {
            throw new RuntimeException("pattern can not be null or ''.");
        }
        this.pattern = pattern;
        if(onOption != null) {
            this.onOption = onOption;
        }
    }

    public OptionDefinition(String pattern, int minOption, int maxOption,Consumer<Option> onOption) {
        this(pattern,onOption);
        this.minOption = minOption;
        this.maxOption = maxOption;
        if(this.minOption < 0 || this.minOption > this.maxOption) {
            throw new RuntimeException("minOption < 0 or minOption > maxOption");
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (obj instanceof OptionDefinition) {
            OptionDefinition that = (OptionDefinition) obj;
            return Objects.equals(pattern, that.pattern);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(pattern);
    }
}
