$(function() {
    parsePageName();
});

/**
 * 分析参数字符串，参数字符串是"c:className_a:action_rid:xxx"这样的格式，
 * 参数字符串放在eventTarget的id里。
 * @class Querystring
 */
/**
 * @constructor Querystring
 */
Querystring = function() {
    this.params = {};
};

Querystring.prototype = {
    /**
     * 将参数qs分解为 key:value的形式，并存放在 this.params里。
     * @method parse
     * @param qs 待分解的字符串，字符串格式："c:className_a:action_rid:xxx"
     */
    parse:function(qs) {
        for (var a in this.params) {
            delete this.params[a];
        }
        if (qs === null || qs.length === 0) {
            //jslog("js error","Querystring.parse qs");
            return;
        }
        qs = qs.replace(/\+/g, ' ');
        var args = qs.split('_');
        for (var i = 0; i < args.length; i++) {
            var pair = args[i].split(':');
            var name = pair[0];
            var value = pair[1];
            this.params[name] = value;
        }
    },
    get:function(key, _default) {
        var value = this.params[key];
        return (value !== null) ? value : _default;
    },
    set:function(key, value) {
        this.params[key] = value;
    },
    has:function(key) {
        var value = this.params[key];
        return (value !== null && value !== undefined);
    }
}

/**
 * 分析当前页面的<code id="pagename">c:xxx_a:xxx_par1:xxx_par2:xxx...</code>
 */
function parsePageName() {
    var qs = new Querystring();
    var def = Globals.define;
    def.pgn = {c:"", a:""};
    var code = $("code#pagename");
    if (code.length == 0) {
        return false;
    }
    qs.parse(code.html());
    def.pgn.c = qs.has("c") ? qs.get("c") : '';
    def.pgn.a = qs.has("a") ? qs.get("a") : '';
    try {
        if(def.pgn.c === "") {
            return;
        }
        def.pgn.c = def.pgn.c.substr(0, 1).toUpperCase() + def.pgn.c.substr(1);
        var command  = 'var obj = new ' + def.pgn.c + 'Class(); ';
        if(def.pgn.a !== "") {
            command += 'obj.' + def.pgn.a + '()';
        }
        eval(command);
    } catch (e) {
//        jslog("jslog", "parsePageName", def.pgn.c, def.pgn.a);
    }
    qs = null;
}



/**
 * 动态调入js
 */
loaderClass = function() {
    this.nidx = 0;
}
loaderClass.prototype = {
    /**
     * Generates an HTML element, this is not appended to a document
     * @method _node
     * @param type {string} the type of element
     * @param attr {string} the attributes
     * @param win {Window} optional window to create the element in
     * @return {HTMLElement} the generated node
     * @private
     */
    _node: function(type, attr, win) {
        //var w = win || window, d=w.document, n=d.createElement(type);
        var n = document.createElement(type);
        for (var i in attr) {
            //if (attr[i] && YAHOO.lang.hasOwnProperty(attr, i)) {
            if (attr[i]) {
                n.setAttribute(i, attr[i]);
            }
        }

        return n;
    },

    /**
     * Generates a link node
     * @method _linkNode
     * @param url {string} the url for the css file
     * @param win {Window} optional window to create the node in
     * @return {HTMLElement} the generated node
     * @private
     */
    _linkNode: function(url, win, charset) {
        var c = charset || "utf-8";
        return _node("link", {
            "id":      "dyn_" + (nidx++),
            "type":    "text/css",
            "charset": c,
            "rel":     "stylesheet",
            "href":    url
        }, win);
    },

    /**
     * Generates a script node
     * @method _scriptNode
     * @param url {string} the url for the script file
     * @param win {Window} optional window to create the node in
     * @return {HTMLElement} the generated node
     * @private
     */
    _scriptNode: function(url, win, charset) {
        var c = charset || "utf-8";
        return this._node("script", {
            "id":      "dyn_" + (this.nidx++),
            "type":    "text/javascript",
            "charset": c,
            "src":     url
        }, win);
    },
    /**
     * Detects when a node has been loaded.  In the case of
     * script nodes, this does not guarantee that contained
     * script is ready to use.
     * @method _track
     * @param n {HTMLElement} the node to track
     * @param url {string} the url that is being loaded
     * @param trackfn {Function} function to execute when finished
     * the default is _next
     * @private
     */
    _track:function(n, opts) {
        var f = opts.fn, id = opts.id;

        // IE supports the readystatechange event for script and css nodes
        //if (ua.ie) {
        if ($.browser.msie) {
            n.onreadystatechange = function() {
                var rs = this.readyState;
                if ("loaded" === rs || "complete" === rs) {
                    n.onreadystatechange = null;
                    f(id);
                }
            };

            // webkit prior to 3.x is problemmatic
        } else if ($.browser.safari) {

            // Safari 3.x supports the load event for script nodes (DOM2)
            if ($.browser.version >= 420) {

                n.addEventListener("load", function() {
                    f(id);
                });

                // Nothing can be done with Safari < 3.x except to pause and hope
                // for the best, particularly after last script is inserted. The
                // scripts will always execute in the order they arrive, not
                // necessarily the order in which they were inserted.  To support
                // script nodes with complete reliability in these browsers, script
                // nodes either need to invoke a function in the window once they
                // are loaded or the implementer needs to provide a well-known
                // property that the utility can poll for.
            } else {
                // Poll for the existence of the named variable, if it
                // was supplied.
                /*if(this.list[id]) {
                 f(id);
                 }*/

            }

            // FireFox and Opera support onload (but not DOM2 in FF) handlers for
            // script nodes.  Opera, but not FF, supports the onload event for link
            // nodes.
        } else {
            n.onload = function() {
                f(id);
            };
        }
    },
    script:function(url, opts) {
        var node = this._scriptNode(url);
        //var par = getEl("pageName");
        var par = document.body;
        this._track(node, opts);
        par.appendChild(node);
    }
}

/* $Revision: 6650 $
 *       $Id: util.js 4826 2009-05-21 05:00:03Z pekky $
 */

function jslog(type, msg, className, actionName) {
    var img = new Image();
    var src = Globals.define.urlPrefix + "/jslog?t=" +
              encodeURIComponent(type) +
              "&uid=" + Globals.define.myid +
              "&msg=" + msg;
    if (className) {
        src += "&c=" + encodeURIComponent(className);
    }
    if (actionName) {
        src += "&a=" + encodeURIComponent(actionName);
    }
    img.src = src;
}

var log = function(args) {
    if (window.console && window.console.log) {
        window.console.log(args);
    }
}

function jsexceptionlog(e, info) {
    var exmsg = "exception info -- name: " + e.name + " | msg: " + e.message + " | extra msg: " + info.msg;
    var className = info.className ? info.className : "";
    var action = info.action ? info.action : "";
//    jslog("js exception", exmsg, className, action);
}

// Inspired by base2 and Prototype
(function(){
  var initializing = false, fnTest = /xyz/.test(function(){xyz;}) ? /\b_super\b/ : /.*/;

  // The base Class implementation (does nothing)
  this.Class = function(){};

  // Create a new Class that inherits from this class
  Class.extend = function(prop) {
    var _super = this.prototype;

    // Instantiate a base class (but only create the instance,
    // don't run the init constructor)
    initializing = true;
    var prototype = new this();
    initializing = false;

    // Copy the properties over onto the new prototype
    for (var name in prop) {
      // Check if we're overwriting an existing function
      prototype[name] = typeof prop[name] == "function" &&
        typeof _super[name] == "function" && fnTest.test(prop[name]) ?
        (function(name, fn){
          return function() {
            var tmp = this._super;

            // Add a new ._super() method that is the same method
            // but on the super-class
            this._super = _super[name];

            // The method only need to be bound temporarily, so we
            // remove it when we're done executing
            var ret = fn.apply(this, arguments);
            this._super = tmp;

            return ret;
          };
        })(name, prop[name]) :
        prop[name];
    }

    // The dummy class constructor
    function Class() {
      // All construction is actually done in the init method
      if ( !initializing && this.init )
        this.init.apply(this, arguments);
    }

    // Populate our constructed prototype object
    Class.prototype = prototype;

    // Enforce the constructor to be what we expect
    Class.constructor = Class;

    // And make this class extendable
    Class.extend = arguments.callee;

    return Class;
  };
})();
