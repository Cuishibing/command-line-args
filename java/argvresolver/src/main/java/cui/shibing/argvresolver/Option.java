package cui.shibing.argvresolver;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Option {
    private OptionDefinition definition;
    private String optionName;
    private List<String> optionValues = new ArrayList<>();

    private boolean isLast;

    public Option(String optionName) {
        this.optionName = optionName;
    }
    public Option(String optionName,OptionDefinition definition) {
        this(optionName);
        this.definition = definition;
    }
}
