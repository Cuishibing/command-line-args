# command-line-args
一个命令行参数解析库，避免编写命令行工具时重复造轮子去解析参数，专注于逻辑实现。 
 
## JavaScript使用
````javascript
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
````
## Java使用
````java
ArgvResolver argvResolver = new ArgvResolver();

// 如果没有任何参数，则打印使用方法。
argvResolver.registerParamListener("",(paramName,values)->{
    System.out.println("show usage!");
});

// 参数名称前缀为‘－’。
argvResolver.registerParamListener("(^-\\w+)|", (paramName, values) -> {
    // 打印出参数名称和值。
    System.out.println(String.format("paramName:%s\tvalues:%s", paramName, values.toString()));
});

// 开始解析过程。
argvResolver.resolve(Arrays.asList("-action", "on"));
````