// 命令行参数解析
class ArgvResolver {
    constructor() {
        this.paramListeners = new Map();
        this.params = new Map();
    }

    resolve(argv) {
        if (argv == null || argv == undefined || argv.length < 1) {
            let pl = this.paramListeners.get("");
            if (pl)
                pl("", []);
            return;
        }
        let lastParamName;
        let values = [];
        let lastParamListener;
        for (let curIndex = 0; curIndex < argv.length; curIndex++) {
            let curArg = argv[curIndex];
            let curListener;
            if ((curListener = this._isParamName(curArg))) {
                if (lastParamName != undefined) {
                    this.params.set(lastParamName, [values, lastParamListener]);
                }
                lastParamName = curArg;
                values = [];
                lastParamListener = curListener;
            } else {
                values.push(curArg);
            }
        }
        if (lastParamName != undefined)
            this.params.set(lastParamName, [values, lastParamListener]);

        this._invoke();
    }

    _invoke() {
        this.params.forEach((valuesAndListener, paramName) => {
            valuesAndListener[1](paramName, valuesAndListener[0]);
        });
        this.params.clear();
    }

    _isParamName(arg) {
        let result = null;
        this.paramListeners.forEach((v, k) => {
            if(k == "")
                return true;
            let regExp = new RegExp(k.substring(1, k.length - 1));
            if (regExp.test(arg)) {
                result = v;
                return false;
            }
        })
        return result;
    }

    registerParamListener(pattern, listener = (() => { })) {
        if (typeof pattern == "string")
            this.paramListeners.set(pattern, listener);
        else if (pattern instanceof RegExp) {// regexp
            this.paramListeners.set(pattern.toString(), listener);
        } else {
            throw new Error("Unsupported pattern!");
        }
    }

    clearAllParamListeners() {
        this.paramListeners.clear();
    }
}

exports.ArgvResolver = ArgvResolver;