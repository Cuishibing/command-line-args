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