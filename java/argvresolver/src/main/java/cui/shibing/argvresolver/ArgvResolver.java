package cui.shibing.argvresolver;

import cui.shibing.argvresolver.exception.ResolveException;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static cui.shibing.argvresolver.exception.ResolveException.ERROR.*;

public class ArgvResolver {
    private Set<OptionDefinition> definitions = new HashSet<>();

    public void addOptionDefinition(OptionDefinition definition) {
        if (definition == null) {
            throw new RuntimeException("definition can not be null.");
        }
        if (definitions.contains(definition)) {
            throw new RuntimeException("duplicate definition.");
        }
        definitions.add(definition);
    }

    public List<String> resolve(String[] args) throws ResolveException {
        if (args == null || args.length <= 0) {
            throw new ResolveException("args can not be null or empty.", NO_ARGS,null);
        }
        boolean isFirst = true;
        Option lastOption = null;
        for (String arg : args) {
            OptionDefinition od = match(arg);
            if (od != null) {
                invokeOptionCallback(lastOption);
                lastOption = new Option(arg, od);
                isFirst = false;
            } else {
                if (isFirst) {
                    throw new ResolveException("un known option name.", UN_KNOWN_OPTION,null);
                } else {
                    lastOption.getOptionValues().add(arg);
                }
            }
        }
        lastOption.setLast(true);
        return invokeOptionCallback(lastOption);
    }

    private List<String> invokeOptionCallback(Option option) throws ResolveException {
        List<String> result = Collections.emptyList();
        if (option != null) {
            if (!option.isLast()) {
                checkOptionNum(option,true,true);
                option.getDefinition().getOnOption().accept(option);
                return result;
            }
            checkOptionNum(option,true,false);
            OptionDefinition def = option.getDefinition();
            int maxOption = def.getMaxOption();
            List<String> optionValues = option.getOptionValues();
            if(optionValues.size() > maxOption) {
                List<String> realValues = optionValues.subList(0, maxOption);
                option.setOptionValues(realValues);
                // get remaining option values.
                result = optionValues.subList(maxOption, optionValues.size());
            }
            option.getDefinition().getOnOption().accept(option);
            return result;
        }
        return result;
    }

    private void checkOptionNum(Option option, boolean minCheck, boolean maxCheck) throws ResolveException {
        OptionDefinition definition = option.getDefinition();
        int min = definition.getMinOption();
        int max = definition.getMaxOption();
        List<String> values = option.getOptionValues();
        boolean result = (minCheck ? values.size() >= min : true)
                && (maxCheck ? values.size() <= max : true);
        if (!result)
            throw new ResolveException(
                    String.format("option [%s]'s length not matching settings.", option.getOptionName()),
                    MISMATCH_OPTION_NUM,option);
    }

    private OptionDefinition match(String value) {
        List<OptionDefinition> matched = definitions
                .stream()
                .filter(entry -> Pattern.matches(entry.getPattern(), value))
                .collect(Collectors.toList());
        return matched.size() > 0 ? matched.get(0) : null;
    }

}
