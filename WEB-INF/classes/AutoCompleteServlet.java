import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.HashMap;
import java.util.Iterator;

/**
 *
 * @author nbuser
 */
public class AutoCompleteServlet extends HttpServlet {

    private ServletContext context;
    private ComposerData compData = new ComposerData();
    private HashMap composers = compData.getComposers();
     static HashMap<Integer,Product> hmap ;
    @Override
    public void init(ServletConfig config) throws ServletException {
        this.context = config.getServletContext();
    }

    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        String action = request.getParameter("action");
        String targetId = request.getParameter("id");

        HashMapProducts hmp=new HashMapProducts();
          hmp.setHashMapProduct();
          hmap=hmp.getHashMapProduct();

        StringBuffer sb = new StringBuffer();

        if (targetId != null) {
            targetId = targetId.trim().toLowerCase();
        } else {
            context.getRequestDispatcher("/error.jsp").forward(request, response);
        }

        boolean namesAdded = false;
        if (action.equals("complete")) {

            // check if user sent empty string
            if (!targetId.equals("")) {

            //   Iterator it = hmap.keySet().iterator();
                Iterator<Integer> it=hmap.keySet().iterator();

                while (it.hasNext()) {
                    Integer id =  it.next();
                    
                    Product composer = (Product) hmap.get(id);
                    System.out.println("yayyy"+  composer.getName());
                    if ( // targetId matches first name
                         composer.getName().toLowerCase().startsWith(targetId) ||
                         // targetId matches last name
                         composer.getManufacturer().toLowerCase().startsWith(targetId) ||
                         // targetId matches full name
                         composer.getName().toLowerCase().concat(" ")
                            .concat(composer.getManufacturer().toLowerCase()).startsWith(targetId)){
System.out.println("yayyy11132342342342");
                        sb.append("<composer>");
                        sb.append("<id>" + composer.Id + "</id>");
                        sb.append("<firstName>" + composer.Name + "</firstName>");
                       sb.append("<lastName>" + composer.getManufacturer() + "</lastName>");
                        sb.append("</composer>");
                        namesAdded = true;
                    }
                }
            }

            if (namesAdded) {
                System.out.println("ddd"+sb.toString());
                response.setContentType("text/xml");
                response.setHeader("Cache-Control", "no-cache");
                response.getWriter().write("<composers>" + sb.toString() + "</composers>");
            } else {
                //nothing to show
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }
        }

        if (action.equals("lookup")) {

            // put the target composer in the request scope to display 
            if ((targetId != null) && composers.containsKey(targetId.trim())) {
                request.setAttribute("composer", composers.get(targetId));
                 
                  Iterator<Integer> it1=hmap.keySet().iterator();

                while (it1.hasNext()) {
                    Integer id =  it1.next();
                    
                    Product composer = (Product) hmap.get(id);
                    String idstr=Integer.toString(composer.Id);
                    if(idstr.equals(targetId))
                    {
                        context.getRequestDispatcher("/"+composer.getManufacturer()).forward(request, response);
                    }
                }
                
            }
        }
    }
}
