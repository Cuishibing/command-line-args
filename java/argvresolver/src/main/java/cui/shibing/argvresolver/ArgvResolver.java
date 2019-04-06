package cui.shibing.argvresolver;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ArgvResolver {

    private Map<String, ParamListener> paramListeners;

    private Map<String, TwoTuple<List<String>, ParamListener>> params;

    private static class TwoTuple<K, V> {
        private K first;
        private V second;

        public TwoTuple(K first, V second) {
            this.first = first;
            this.second = second;
        }
    }

    public ArgvResolver() {
        this.paramListeners = new HashMap<>();
        this.params = new HashMap<>();
    }

    public void registerParamListener(String pattern, ParamListener listener) {
        this.paramListeners.put(pattern, listener);
    }

    public void clearAllParamListeners() {
        this.paramListeners.clear();
    }

    public void resolve(Collection<String> argv) {
        if (argv != null) {
            String[] argvArray = new String[argv.size()];
            argv.toArray(argvArray);
            resolve(argvArray);
        } else
            resolve((String[]) null);
    }

    public void resolve(String[] argv) {
        if (argv == null || argv.length < 1) {
            ParamListener pl = this.paramListeners.get("");
            if (pl != null)
                pl.onParamAppear("", Collections.<String>emptyList());
            return;
        }
        String lastParamName = null;
        List<String> lastParamValues = new ArrayList<>(2);
        ParamListener lastListener = null;
        for (String arg : argv) {
            ParamListener paramListener = isParamName(arg);
            if (paramListener != null) {// is param name
                if (lastParamName != null) {
                    this.params.put(lastParamName, new TwoTuple<>(lastParamValues, lastListener));
                }
                lastParamName = arg;
                lastListener = paramListener;
                lastParamValues = new ArrayList<>(2);
            } else {
                lastParamValues.add(arg);
            }
        }
        if (lastParamName != null) {
            this.params.put(lastParamName, new TwoTuple<>(lastParamValues, lastListener));
        }

        this.invoke();
    }

    private void invoke() {
        this.params.forEach((paramName, valuesAndListener) ->
                valuesAndListener.second.onParamAppear(paramName, valuesAndListener.first));
        this.params.clear();
    }

    private ParamListener isParamName(String arg) {
        List<Map.Entry<String, ParamListener>> matchedListeners = this.paramListeners.entrySet().stream()
                .filter(entry -> Pattern.matches(entry.getKey(), arg))
                .collect(Collectors.toList());
        // 只取第一个
        if (matchedListeners.size() > 0) {
            return matchedListeners.get(0).getValue();
        }
        return null;
    }
}