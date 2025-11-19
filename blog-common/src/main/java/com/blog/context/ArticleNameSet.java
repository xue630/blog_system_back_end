package com.blog.context;

import java.util.HashSet;

public class ArticleNameSet {

    private static volatile HashSet<String> articleNameSet;
    private ArticleNameSet(){}

    public static HashSet<String> getInstance(){
        if(articleNameSet == null){
            synchronized (ArticleNameSet.class){
                if(articleNameSet == null)
                    articleNameSet = new HashSet<>();
            }
        }
        return articleNameSet;
    }
}
