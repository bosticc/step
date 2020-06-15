<<<<<<< HEAD
=======


>>>>>>> e9655925b1c5f3ce6993495b8bbe7ab4c88e3930
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import com.google.sps.data.Comment;
import com.google.gson.Gson;
import javax.servlet.http.HttpServletResponse;

/** Servlet that returns some example content. */
@WebServlet("/data")
public class DataServlet extends HttpServlet {

<<<<<<< HEAD
   @Override
   public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    Query query = new Query("Comment").addSort("timestamp", SortDirection.DESCENDING);

    //Makes a new datastore object and intializes it with the comment
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    PreparedQuery results = datastore.prepare(query);

    
    //creates a new array for all the comments
=======
   public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    Query query = new Query("Comment").addSort("timestamp", SortDirection.DESCENDING);

    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    PreparedQuery results = datastore.prepare(query);

>>>>>>> e9655925b1c5f3ce6993495b8bbe7ab4c88e3930
    List<Comment> comments = new ArrayList<>();
    for (Entity entity : results.asIterable()) {
      long id = entity.getKey().getId();
      String title = (String) entity.getProperty("title");
      long timestamp = (long) entity.getProperty("timestamp");
      Comment comment = new Comment(id, title, timestamp);
      comments.add(comment);
    }
<<<<<<< HEAD
    /**prints all the comments back to the holder in 
    html
    */
    Gson gson = new Gson();
    response.setContentType("application/json;");
    response.getWriter().println(gson.toJson(comments));
    }
=======

    Gson gson = new Gson();

    response.setContentType("application/json;");
    response.getWriter().println(gson.toJson(comments));
  }
>>>>>>> e9655925b1c5f3ce6993495b8bbe7ab4c88e3930


  @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String title = request.getParameter("title");
        long timestamp = System.currentTimeMillis();
<<<<<<< HEAD
=======

>>>>>>> e9655925b1c5f3ce6993495b8bbe7ab4c88e3930
        Entity taskEntity = new Entity("Comment");
        taskEntity.setProperty("title", title);
        taskEntity.setProperty("timestamp", timestamp); 
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        datastore.put(taskEntity);
        response.sendRedirect("/index.html");
   }


<<<<<<< HEAD
=======



>>>>>>> e9655925b1c5f3ce6993495b8bbe7ab4c88e3930
  /**
   * @return the request parameter, or the default value if the parameter
   *         was not specified by the client
   */
  private String getParameter(HttpServletRequest request, String name, String defaultValue) {
    String value = request.getParameter(name);
    if (value == null) {
<<<<<<< HEAD
      return null;
=======
      return defaultValue;
>>>>>>> e9655925b1c5f3ce6993495b8bbe7ab4c88e3930
    }
    return value;
  }

}
