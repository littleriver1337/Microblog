package com.again.com;

import spark.ModelAndView;
import spark.Session;
import spark.Spark;
import spark.template.mustache.MustacheTemplateEngine;

import java.util.ArrayList;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        ArrayList<Post> posts = new ArrayList();
        Spark.staticFileLocation("/public");
        Spark.init();

        Spark.post(
                "/create-account",
                ((request, response) -> {
                String user = request.queryParams("username");
                Session session = request.session();
                session.attribute("username", user);
                response.redirect("/posts");
                return ("");
            })
        );
        Spark.post(
                "/create-post",
                ((request, response) -> {
                    Post post = new Post();
                    post.text = request.queryParams("postText");
                    posts.add(post);
                    response.redirect("/posts");
                    return ("");
                })
        );
        Spark.get(
                "/posts",
                ((request, response) -> {
                    HashMap m = new HashMap();
                    m.put("count", posts.size());
                    m.put("post", posts);
                    return new ModelAndView(m, "posts.html");
                }),
                new MustacheTemplateEngine()
        );

        //String users = new users();
    }
}
