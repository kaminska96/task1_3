package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.soap.ws.client.calc.Calculator;
import com.soap.ws.client.calc.CalculatorSoap;

@WebServlet("/calculator")
public class CalculatorServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Handles GET requests to perform calculations using the Calculator SOAP web service.
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get the operation and numbers from the request
        String operation = request.getParameter("operation");
        int num1 = Integer.parseInt(request.getParameter("num1"));
        int num2 = Integer.parseInt(request.getParameter("num2"));

        // Connect to the Calculator SOAP web service
        Calculator calculatorService = new Calculator();
        CalculatorSoap calculatorPort = calculatorService.getCalculatorSoap();

        int result = 0;
        String operationName = "";

        // Choose the operation to perform based on the user's input
        switch (operation) {
            case "add":
                result = calculatorPort.add(num1, num2);
                operationName = "Addition";
                break;
            case "subtract":
                result = calculatorPort.subtract(num1, num2);
                operationName = "Subtraction";
                break;
            case "multiply":
                result = calculatorPort.multiply(num1, num2);
                operationName = "Multiplication";
                break;
            case "divide":
                if (num2 != 0) {
           z        result = calculatorPort.divide(num1, num2);
                    operationName = "Division";
                } else {
                    operationName = "Error";
                    result = -1; // Indicating an error (division by zero)
                }
                break;
        }

        // Display the result on the web page
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        if (operationName.equals("Error")) {
            out.write("Cannot divide by zero!");
        } else {
            out.write(operationName + " Result: " + result);
        }
        out.flush();
        out.close();
    }
}
