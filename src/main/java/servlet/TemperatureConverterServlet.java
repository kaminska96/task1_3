package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.soap.*;

@WebServlet("/tempconverter")
public class TemperatureConverterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Handles GET requests to convert temperature using the TemperatureConverter SOAP web service.
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get the conversion type and temperature value from the request
        String conversionType = request.getParameter("conversionType");
        String tempValue = request.getParameter("tempValue");

        String result = "";

        try {
            // Create a SOAP request to the appropriate method in the web service
            SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
            SOAPConnection soapConnection = soapConnectionFactory.createConnection();

            // URL of the SOAP web service
            String url = "https://www.w3schools.com/xml/tempconvert.asmx";

            // Create the appropriate SOAP request based on the conversion type
            SOAPMessage soapMessage;
            if (conversionType.equals("CtoF")) {
                soapMessage = createSOAPRequest("CelsiusToFahrenheit", tempValue);
            } else {
                soapMessage = createSOAPRequest("FahrenheitToCelsius", tempValue);
            }

            // Execute the request and get the SOAP response
            SOAPMessage soapResponse = soapConnection.call(soapMessage, url);

            // Extract the result from the SOAP response
            result = extractResultFromSOAPResponse(soapResponse);

            soapConnection.close();

        } catch (Exception e) {
            result = "Error in Temperature Converter Service: " + e.getMessage();
        }

        // Display the result on the web page
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.write(result);
        out.flush();
        out.close();
    }

    // Helper method to create a SOAP request based on the operation and temperature value
    private SOAPMessage createSOAPRequest(String operation, String tempValue) throws Exception {
        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage soapMessage = messageFactory.createMessage();

        SOAPPart soapPart = soapMessage.getSOAPPart();
        String serverURI = "https://www.w3schools.com/xml/";

        // Create the SOAP envelope and add the necessary namespaces
        SOAPEnvelope envelope = soapPart.getEnvelope();
        envelope.addNamespaceDeclaration("example", serverURI);

        // Add the body and the specific request operation (Celsius to Fahrenheit or vice versa)
        SOAPBody soapBody = envelope.getBody();
        SOAPElement soapBodyElem = soapBody.addChildElement(operation, "example");
        SOAPElement soapBodyElem1 = soapBodyElem.addChildElement(operation.equals("CelsiusToFahrenheit") ? "Celsius" : "Fahrenheit", "example");
        soapBodyElem1.addTextNode(tempValue);

        // Set the SOAPAction header
        MimeHeaders headers = soapMessage.getMimeHeaders();
        headers.addHeader("SOAPAction", serverURI + operation);

        soapMessage.saveChanges();
        return soapMessage;
    }

    // Helper method to extract the result from the SOAP response
    private String extractResultFromSOAPResponse(SOAPMessage soapResponse) throws Exception {
        SOAPBody body = soapResponse.getSOAPBody();
        return body.getTextContent().trim();
    }
}
