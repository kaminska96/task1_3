package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.soap.ws.client.hello.HelloEndpointService;
import com.soap.ws.client.hello.HelloEndpoint;
import com.soap.ws.client.hello.HelloRequest;
import com.soap.ws.client.hello.HelloResponse;

@WebServlet("/hello")
public class HelloServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Handles GET requests to fetch the user's name and interact with the Hello SOAP web service.
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get the username from the request
        String userName = request.getParameter("username");

        // Create a request object to interact with the SOAP web service
        HelloRequest helloRequest = new HelloRequest();
        helloRequest.setName(userName);

        // Call the Hello SOAP web service
        HelloEndpointService helloService = new HelloEndpointService();
        HelloEndpoint helloPort = helloService.getHelloEndpointPort();

        // Send the request and get the response from the web service
        HelloResponse helloResponse = helloPort.sayHello(helloRequest);
        String greetingMessage = helloResponse.getMessage();

        // Display the response on the web page
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.write("<html><body>");
        out.write(greetingMessage);
        out.write("</body></html>");
        out.flush();
        out.close();
    }
}
