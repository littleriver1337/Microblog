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

        Spark.post(
                "/create-account",
                ((request, response) -> {
                String user = request.queryParams("username");
                Session session = request.session();
                session.attribute("username", user);
                response.redirect("/");
                return ("");
            })
        );
        Spark.post(
                "/create-post",
                ((request, response) -> {
                    Post post = new Post();
                    post.id = posts.size() + 1;
                    post.text = request.queryParams("text");
                    posts.add(post);
                    response.redirect("/");
                    return ("");
                })
        );
        Spark.post(
                "/delete-post",
                ((request, response) -> {
                    String id = request.queryParams("postid");
                    try {
                        int idNum = Integer.valueOf(id);
                        posts.remove(idNum - 1);
                        for ( int i = 0; i < posts.size(); i++){
                            posts.get(i).id = i + 1;
                        }
                    } catch (Exception e) {

                    }
                    response.redirect("/");
                    return "";
                })
        );
        Spark.post(
                "/edit-post",
                ((request, response) -> {
                    String id = request.queryParams("postid");
                    String edit = request.queryParams("editpost");
                    try {
                        int idNum = Integer.valueOf(id);
                        posts.get(idNum - 1).text = edit;
                        for (int i = 0; i < posts.size(); i++){
                            posts.get(i).id = i + 1;
                        }
                    }
                    catch (Exception e){

                    }
                    response.redirect("/");
                    return "";
                })
        );
        Spark.get(
                "/",
                ((request, response) -> {
                    Session session = request.session();
                    String user = session.attribute("username");
                    if (user == null) {
                        return new ModelAndView(new HashMap(), "index.html");
                    }
                    HashMap m = new HashMap();
                    m.put("username", user);
                    m.put("post", posts);
                    return new ModelAndView(m, "posts.html");
                }),
                new MustacheTemplateEngine()
        );
    }
}
