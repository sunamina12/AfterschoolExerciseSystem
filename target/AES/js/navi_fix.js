/**
 * Created by yangitano on 4/6/17.
 */
function getCoords(el) {

    if (typeof el == 'string') {
        el = Fid(el);
    }

    var box = el.getBoundingClientRect(), doc = el.ownerDocument, body = doc.body, html = doc.documentElement, clientTop = html.clientTop
        || body.clientTop || 0, clientLeft = html.clientLeft
        || body.clientLeft || 0, top = box.top
        + (self.pageYOffset || html.scrollTop || body.scrollTop)
        - clientTop, left = box.left
        + (self.pageXOffset || html.scrollLeft || body.scrollLeft)
        - clientLeft
    return {
        'top' : top,
        'left' : left
    };
};

function Fid(id) {
    return document.getElementById(id);
}

/***********************************πÃ∂®µƒ‘™Àÿ************************************/

/**
 * ◊¢“‚’ºŒª∑˚µƒ∏ﬂ∂»“ª∂®“™∫Õ∏°∂Ø≤„œ‡Õ¨

 * @param id string ‘™Àÿid
 * @param fixtop int ‘™ÀÿπÃ∂® ±æ‡¿Î∂•∂Àµƒæ‡¿Î
 * @param zIndex int ≤„º∂
 * @param string ’ºŒª∑˚µƒid(«ÎŒÕ¸º«≈∂)
 */
function fixeDiv(id, fixtop, zIndex, place) {
    //ªÒ»°scrolltop
    function getScrollTop() {
        var scrollTop = document.documentElement.scrollTop
            || document.body.scrollTop;
        return scrollTop;
    }

    var elementTop = getCoords(id).top;

    //w3c
    function navfixed() {
        //–Ë“™∂ØÃ¨ªÒ»°
        var scrollTop = getScrollTop();

        if (scrollTop > elementTop - fixtop) {
            Fid(id).style.position = 'fixed';
            Fid(id).style.zIndex = zIndex;
            Fid(id).style.top = fixtop + 'px';

            //’ºŒª∑˚
            if (place) {
                Fid(place).style.display = 'block';
            }
        } else {
            Fid(id).style.position = 'relative';
            if (place) {
                Fid(place).style.display = 'none';
            }
            Fid(id).style.top = '0px';
        }
    }

    //nav “ª÷±¥¶”⁄œ‡∂‘∂®Œª ◊¥Ã¨
    function navfixedie6() {
        var scrollTop = getScrollTop();
        if (scrollTop > elementTop - fixtop) {
            Fid(id).style.top = (scrollTop - elementTop + fixtop) + 'px';
            Fid(id).style.zIndex = zIndex;
        } else {
            Fid(id).style.top = '0px';
        }
    }

    //∑«IE‰Ø¿¿∆˜œ¬
    if (document.addEventListener) {
        window.addEventListener("load", navfixed, true);
        //window.addEventListener("resize",navfixed,true);
        window.addEventListener("scroll", navfixed, true);
    }
    ;
}
//∂•≤ø
fixeDiv('nav', 0, 10, 'place');

//◊Û≤‡
var navHeight = Fid('nav').offsetHeight;
fixeDiv('leftnav', navHeight, 10, 'leftplace');

/***********************************πÃ∂®µƒ‘™Àÿ***********************/
//ª¨∂Ø∂®Œª
function scroll_nav_pos(g_id_target_map) {
    //id∂‘”¶πÿœµ
    this.id_target_map = g_id_target_map;

    //ªÒ»°√ø∏ˆ‘™Àÿæ‡¿Î∂•∂Àæ‡¿Î
    this.header_top_map = {};

    //≥ı ºªØ
    this.init();
}

scroll_nav_pos.prototype = {
    getHeaderTop : function() {//ªÒ»°√ø∏ˆ‘™ÀÿµΩ“≥√Ê∂•∂Àµƒæ‡¿Î
        for ( var i in this.id_target_map) {
            if (Fid(i) && Fid(this.id_target_map[i])) {
                this.header_top_map[i] = getCoords(this.id_target_map[i]).top;
            }
        }
    },

    refreshHeaderTop : function() {//À¢–¬Œª÷√µƒ∂‘”¶πÿœµ
        this.getHeaderTop();
    },

    goTo : function(id)//µ„ª˜Ã¯◊™Ã¯÷∏∂®Œª÷√
    {
        if (this.header_top_map[id] == undefined) {
            return;
        }

        var scrollTop = this.header_top_map[id];
        var navHeight = Fid('nav').offsetHeight;

        document.documentElement.scrollTop = document.body.scrollTop = scrollTop
            - navHeight;
    },
    setHeaderStyle : function(id) {//…Ë÷√‘™Àÿ—˘ Ω
        for ( var i in this.id_target_map) {
            if (Fid(i) && Fid(this.id_target_map[i])) {
                if (i == id) {
                    Fid(i).className = 'on';
                } else {
                    Fid(i).className = '';
                }
            }
        }
    },
    scrollBind : function() {//∞Û∂®πˆ∂Ø ¬º˛
        var _this = this;
        function dynamic_set_header() {

            var scrollTop = document.body.scrollTop
                || document.documentElement.scrollTop;
            var navheight = Fid('nav').offsetHeight;

            for ( var i in _this.id_target_map) {
                var top = _this.header_top_map[i];

                if (scrollTop >= top - navheight) {
                    _this.setHeaderStyle(i);
                }
            }
        }

        //∑«IE‰Ø¿¿∆˜œ¬
        if (document.addEventListener) {
            window.addEventListener("load", dynamic_set_header, true);
            //window.addEventListener("resize",dynamic_set_header,true);
            window.addEventListener("scroll", dynamic_set_header, true);
        }

        if (document.attachEvent && window.ActiveXObject) {

            window.attachEvent("onload", dynamic_set_header);
            //window.attachEvent("onresize",dynamic_set_header);
            window.attachEvent("onscroll", dynamic_set_header);
        }
        ;
    },

    init : function() {//≥ı ºªØ
        this.getHeaderTop();
        this.clickBind();
        this.scrollBind();
    }
}

var navScrollObj = new scroll_nav_pos(g_id_target_map);

new scroll_nav_pos(g_id_target_map_2);