# command-line-args
一个命令行参数解析库，避免编写命令行工具时重复造轮子去解析参数，专注于逻辑实现。 
 
## JavaScript使用
````javascript
const ArgvResolver = require("./argvresolver.js").ArgvResolver;
const process = require("process");

const argvResolver = new ArgvResolver(process.argv);

const param = {};

// If there are no args, the usage are displayed.
argvResolver.registerCommandListener("", () => {
    console.info("show usage!");
})

//  The prefix of the option is '-'.
argvResolver.registerCommandListener(/^-\w+/, (name, values) => {
    // Save options name and value to the param object.
    param[name.substring(1)] = values[0];
})

// Start the resolve process.
argvResolver.resolve();

function doSomething(param){
    // Something...
}
````
## Java使用（待实现）