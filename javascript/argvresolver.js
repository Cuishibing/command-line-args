// 命令行参数解析
class ArgvResovler {
    constructor(argv) {
        this.argv = argv;
        this.strCmdListeners = new Map();
        this.regExpCmdListeners = new Map();
        this.paramMap = new Map();
    }

    resolve() {
        if (this.argv.length <= 2) {
            let cml = this.strCmdListeners.get("");
            if (cml)
                cml("", []);
            return;
        }
        let lastParamName;
        let values = [];
        let lastParamListener;
        for (let curIndex = 2; curIndex < this.argv.length; curIndex++) {
            let curArg = this.argv[curIndex];
            let curListener;
            if ((curListener = this._isParamName(curArg))) {
                if (lastParamName != undefined) {
                    this.paramMap.set(lastParamName, [values, lastParamListener]);
                }
                lastParamName = curArg;
                values = [];
                lastParamListener = curListener;
            } else {
                values.push(curArg);
            }
        }
        if (lastParamName != undefined)
            this.paramMap.set(lastParamName, [values, lastParamListener]);

        this._invoke();
    }

    _invoke() {
        this.paramMap.forEach((valuesAndListener, paramName) => {
            valuesAndListener[1](paramName, valuesAndListener[0]);
        });
    }

    _isParamName(arg) {
        return this.strCmdListeners.get(arg) || this._isRegExpParamName(arg);
    }

    _isRegExpParamName(arg) {
        let result = null;
        this.regExpCmdListeners.forEach((v, k) => {
            let regExp = new RegExp(k.substring(1, k.length - 1));
            if (regExp.test(arg)) {
                result = v;
                return false;
            }
        })
        return result;
    }

    registerCommandListener(pattern, listener = (() => { })) {
        if (typeof pattern == "string")
            this.strCmdListeners.set(pattern, listener);
        else if (pattern instanceof RegExp) {// regexp mode
            this.regExpCmdListeners.set(pattern.toString(), listener);
        } else {
            throw new Error("Unsupported pattern!");
        }
    }
}

exports.ArgvResovler = ArgvResovler;