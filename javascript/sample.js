const ArgvResolver = require("./argvresolver.js").ArgvResolver;
const process = require("process");

const argvResolver = new ArgvResolver();

const param = {};

// 如果没有任何参数，则打印使用方法。
argvResolver.registerParamListener("", () => {
    console.info("show usage!");
})

// 参数名称前缀为‘－’。
argvResolver.registerParamListener(/^-\w+/, (name, values) => {
    // 保存参数到param对象中。
    param[name.substring(1)] = values[0];
})

// 开始解析过程。
// node js环境下要去掉第一和第二个参数。
let realArgv = [];
for (let i = 2; i < process.argv.length; i++) {
    realArgv.push(process.argv[i]);
}
argvResolver.resolve(realArgv);

(
    function doSomething(param) {
        // 打印出参数信息。
        for(let k in param){
            console.log("paramName:%s\tparamValue:%s",k,param[k]);
        }
    }
)(param);