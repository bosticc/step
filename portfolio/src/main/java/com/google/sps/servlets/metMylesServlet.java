// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.sps.servlets;


import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/met-Myles")
public class metMylesServlet extends HttpServlet {

  public Map<String, Integer> metVotes = new HashMap<>();

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    Query query = new Query("Votes").addSort("timestamp", SortDirection.DESCENDING);

    //Makes a new datastore object and intializes it with the comment
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    PreparedQuery results = datastore.prepare(query);
    
    //prints out to chart container
    response.setContentType("application/json");
    Gson gson = new Gson();
    String json = gson.toJson(metVotes);
    response.getWriter().println(json);
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String metMyles = request.getParameter("metMyles");
    int currentVotes = metVotes.containsKey(metMyles) ? metVotes.get(metMyles) : 0;
    metVotes.put(metMyles, currentVotes + 1);

    //storing objects in the datastore entity 
    long timestamp = System.currentTimeMillis();
    Entity taskEntity = new Entity("Votes");
    taskEntity.setProperty("title", metMyles);
    taskEntity.setProperty("timestamp", timestamp); 
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    datastore.put(taskEntity);
    //sends the user back to the chart
    response.sendRedirect("/index.html#chart-container");
  }
  
  private String getParameter(HttpServletRequest request, String name, String defaultValue) {
    String value = request.getParameter(name);
    return value;
  }
}
