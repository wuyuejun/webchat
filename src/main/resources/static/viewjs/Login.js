$(function () {
  let app = function () {
    /**
     * 静态变量
     * @type {{BTN: {}, URL: {}, PAGE_ELEMENT: {}}}
     */
    const COMMON = {
      //访问URL
      URL : {},
      //页面元素
      PAGE_ELEMENT : {
        PASSWORD : $("#password"),
        LEFTHAND : $("#left_hand"),
        RIGHTHAND : $("#right_hand")
      },
      //页面按钮
      BTN : {}
    };

    //得到焦点
    COMMON.PAGE_ELEMENT.PASSWORD.focus(function () {
      //执行 CSS 属性集的自定义动画
      //通过CSS样式将元素从一个状态改变为另一个状态
      COMMON.PAGE_ELEMENT.LEFTHAND.animate(
          {
            left:"150",
            top:"-38"
          },
          {
            step:function () {
              if(parseInt(COMMON.PAGE_ELEMENT.LEFTHAND.css("left"))>140){
                COMMON.PAGE_ELEMENT.LEFTHAND.attr("class","left_hand");
              }
            }
          },
          2000
      );

      COMMON.PAGE_ELEMENT.RIGHTHAND.animate(
          {
            right:"-64",
            top:"-38px"
          },
          {
            step:function () {
              if(parseInt(COMMON.PAGE_ELEMENT.RIGHTHAND.css("right"))>-70){
                COMMON.PAGE_ELEMENT.RIGHTHAND.attr("class","right_hand");
              }
            }
          },
          2000
      );
    });
    //失去焦点
    COMMON.PAGE_ELEMENT.PASSWORD.blur(function () {
      COMMON.PAGE_ELEMENT.LEFTHAND.attr("class", "initial_left_hand");
      COMMON.PAGE_ELEMENT.LEFTHAND.attr("style", "left:100px;top:-12px;");
      COMMON.PAGE_ELEMENT.LEFTHAND.attr("class", "initial_right_hand");
      COMMON.PAGE_ELEMENT.LEFTHAND.attr("style", "right:-112px;top:-12px");
    });
    

    /**
     * 页面监听
     */
    function setLister() {

    }

    return {
      init:function () {
        setLister();
      }
    }
  }();
});