package com.xcl.webchat.core.utils;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @author :xiaochanglu
 * @Description :菜单工具类
 * @date :2019/1/24 10:28
 */
@Slf4j
public class MenuUtils {

    public MenuUtils() {
        log.info("菜单工具类初始化启动......");
    }

    @Data
    static class Menu {
        /**
         * id编号
         */
        private Integer id;
        /**
         * 标签
         */
        private String label;
        /**
         * 路径
         */
        private String path;
        /**
         * 父类id编号
         */
        private Integer pid;
        /**
         * 标签等级
         */
        private Integer level;
        /**
         * 子节点  信息
         */
        private List<Menu> children = new ArrayList<Menu>();

        public Menu(Integer id, String label, String path, Integer pid, Integer level) {
            this.id = id;
            this.label = label;
            this.path = path;
            this.pid = pid;
            this.level = level;
        }
    }
    /**
     * @param : [parent, list]
     * @return : xcl.com.manager.core.util.MenuUtils.Menu
     * @Description ：递归获取节点信息
     * @author : xcl
     * @date : 2019/1/24 10:33
     */
    public static Menu recursiveTree(Menu parent, List<Menu> list) {
        for (Menu menu : list) {
            if (parent.getId().equals(menu.getPid())) {
                menu = recursiveTree(menu, list);
                parent.getChildren().add(menu);
            }
        }
        return parent;
    }
    /**
     * @Description  ：解析菜单数据结构
     * @author       : xcl
     * @param        : [list]
     * @return       : java.util.List<xcl.com.manager.core.util.MenuUtils.Menu>
     * @date         : 2019/1/24 10:39
     */
    public static List<Menu> parseMenuTree(List<Menu> list){
        List<Menu> result = new ArrayList<Menu>();
        // 1、获取第一级节点
        for (Menu menu : list) {
            if(null == menu.getPid()) {
                result.add(menu);
            }
        }
        // 2、递归获取子节点
        for (Menu parent : result) {
            parent = recursiveTree(parent, list);
        }
        return result;
    }

//    public static void main(String[] args) {
//        Menu menu1 = new Menu(0,"顶级","/",null,1);
//        Menu menu2 = new Menu(1,"次级1","/xx/",0,2);
//        Menu menu22 = new Menu(2,"次级2","/oo/",0,2);
//        Menu menu3 = new Menu(3,"次次级3","/xx/aa",1,3);
//        Menu menu33 = new Menu(4,"次次级4","/xx/bb",1,3);
//        Menu menu333 = new Menu(5,"次次级5","/oo/cc",2,3);
//        Menu menu3333 = new Menu(6,"次次级6","/oo/dd",2,3);
//        List<Menu> list = new ArrayList<Menu>();
//        list.add(menu1);
//        list.add(menu2);
//        list.add(menu22);
//        list.add(menu3);
//        list.add(menu33);
//        list.add(menu333);
//        list.add(menu3333);
//
//        //树形结构数据生成
//        List<Menu> result = parseMenuTree(list);
//        System.out.println(new Gson().toJson(result).toString());
//    }

}
