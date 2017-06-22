package com.wenda.service;

import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by chen on 2017/6/12.
 */
@Service
public class SensitiveService implements InitializingBean{

    //设置一个空的根
    private TrieNode rootNode = new TrieNode();

    @Override
    public void afterPropertiesSet() throws Exception {
        try{
            InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("SensitiveWords.txt");
            InputStreamReader reader = new InputStreamReader(is);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String lineTxt;
            while ((lineTxt = bufferedReader.readLine()) != null){
                addWord(lineTxt.trim());
            }
            reader.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 增加一个敏感词
     * @param lineTxt
     */
    private void addWord(String lineTxt){
        TrieNode tempNode = rootNode;
        for (int i = 0; i < lineTxt.length(); i++) {
            Character c = lineTxt.charAt(i);
            //判断是否有子节点
            TrieNode node = tempNode.getSubNode(c);

            if (node == null){
                //如果不存在子节点，新建一个子节点
                node = new TrieNode();
                tempNode.addSubNode(c,node);
            }

            // 当前节点再指向下一个节点
            tempNode = node;

            //判断是否是敏感词的结尾
            if (i == lineTxt.length() - 1){
                tempNode.setKeywordEnd(true);
            }

        }
   }

    //代替词
    private static final String DEFAULT_REPLACEMENT = "**";

    private class TrieNode{
        //是否是关键词的结尾
        private boolean end = false;
        /**
         * key下一个字符，value是对应的节点
         */
        private Map<Character,TrieNode> subNodes = new HashMap<Character,TrieNode>();

        /**
         * 向指定位置添加节点树
         * @param key
         * @param node
         */
        void addSubNode(Character key,TrieNode node){
            subNodes.put(key,node);
        }

        /**
         * 获取下一个节点
         * @param key
         * @return
         */
        TrieNode getSubNode(Character key){
            return subNodes.get(key);
        }

        /**
         * 判断是否是最后的节点
         * @return
         */
        boolean isKeywordEnd(){
           return end;
        }

        /**
         * 设置最后节点
         * @param end
         */
        void setKeywordEnd(boolean end){
            this.end = end;
        }

        /**
         * 获取树的大小
         * @return
         */
        public int getSubNodeCount(){
            return subNodes.size();
        }


    }

    /**
     * 判断是否是一个符号
     */
    public boolean isSymbol(char c) {
        int ic = (int) c;
        // 0x2E80-0x9FFF 东亚文字范围
        return !CharUtils.isAsciiAlphanumeric(c) && (ic < 0x2E80 || ic > 0x9FFF);
    }


    public String filter(String text){
        if (StringUtils.isBlank(text)){
            return text;
        }

        StringBuffer result = new StringBuffer();

        TrieNode tempNode = rootNode;
        int begin = 0;
        int position = 0;

        while (position<text.length()){
            //取出当前字符
            char c = text.charAt(position);

            if (isSymbol(c)){
                if (tempNode == rootNode){
                    result.append(c);
                    ++begin;
                }
                ++position;
                continue;
            }

           tempNode = tempNode.getSubNode(c);

            if (tempNode == null){
                result.append(text.charAt(begin));
                position = begin + 1;
                begin = position;
                tempNode = rootNode;
            }else if (tempNode.isKeywordEnd()){
                result.append(DEFAULT_REPLACEMENT);
                position = position +1;
                begin = position;
                tempNode = rootNode;
            }else {
                ++position;
            }
        }

        result.append(text.substring(begin));

        return result.toString();
    }


/*    public static void main(String[] args) {
        SensitiveService s = new SensitiveService();
        s.addWord("色情");
        s.addWord("赌博");
        System.out.println(s.filter("你好色*情 赌博"));

    }*/
}
