package test;

import com.soap.ws.client.calc.Calculator;
import com.soap.ws.client.calc.CalculatorSoap;
import com.soap.ws.client.hello.HelloEndpoint;
import com.soap.ws.client.hello.HelloEndpointService;
import com.soap.ws.client.hello.HelloRequest;
import com.soap.ws.client.hello.HelloResponse;

import javax.xml.soap.*;
import java.net.URL;

public class WebServiceTest {

    public static void main(String[] args) {
        testHelloService();

        testCalculatorService();

        testTemperatureConverterService();
    }

    public static void testHelloService() {
        try {
            System.out.println("Тестування Hello Service:");

            HelloEndpointService helloService = new HelloEndpointService();
            HelloEndpoint helloPort = helloService.getHelloEndpointPort();

            HelloRequest request = new HelloRequest();
            request.setName("Ivan");

            HelloResponse response = helloPort.sayHello(request);

            System.out.println("Відповідь Hello Service: " + response.getMessage());
        } catch (Exception e) {
            System.out.println("Помилка у Hello Service: " + e.getMessage());
        }
    }

    public static void testCalculatorService() {
        try {
            System.out.println("Тестування Calculator Service:");

            Calculator calculatorService = new Calculator();
            CalculatorSoap calculatorPort = calculatorService.getCalculatorSoap();

            int result = calculatorPort.add(10, 5);

            System.out.println("Результат додавання: " + result);
        } catch (Exception e) {
            System.out.println("Помилка у Calculator Service: " + e.getMessage());
        }
    }

    public static void testTemperatureConverterService() {
        try {
            System.out.println("Тестування Temperature Converter Service:");

            SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
            SOAPConnection soapConnection = soapConnectionFactory.createConnection();

            String url = "https://www.w3schools.com/xml/tempconvert.asmx";

            SOAPMessage soapMessageCtoF = createSOAPRequest("CelsiusToFahrenheit", "100");

            SOAPMessage soapResponseCtoF = soapConnection.call(soapMessageCtoF, url);

            System.out.println("100°C у Фаренгейтах:");
            soapResponseCtoF.writeTo(System.out);

            SOAPMessage soapMessageFtoC = createSOAPRequest("FahrenheitToCelsius", "212");

            SOAPMessage soapResponseFtoC = soapConnection.call(soapMessageFtoC, url);

            System.out.println("\n212°F у Цельсіях:");
            soapResponseFtoC.writeTo(System.out);

            soapConnection.close();
        } catch (Exception e) {
            System.out.println("Помилка у Temperature Converter Service: " + e.getMessage());
        }
    }

    private static SOAPMessage createSOAPRequest(String operation, String tempValue) throws Exception {
        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage soapMessage = messageFactory.createMessage();

        SOAPPart soapPart = soapMessage.getSOAPPart();

        String serverURI = "https://www.w3schools.com/xml/";

        // SOAP Envelope
        SOAPEnvelope envelope = soapPart.getEnvelope();
        envelope.addNamespaceDeclaration("example", serverURI);

        // SOAP Body
        SOAPBody soapBody = envelope.getBody();
        SOAPElement soapBodyElem = soapBody.addChildElement(operation, "example");
        SOAPElement soapBodyElem1 = soapBodyElem.addChildElement(operation.equals("CelsiusToFahrenheit") ? "Celsius" : "Fahrenheit", "example");
        soapBodyElem1.addTextNode(tempValue);

        MimeHeaders headers = soapMessage.getMimeHeaders();
        headers.addHeader("SOAPAction", serverURI + operation);

        soapMessage.saveChanges();

        return soapMessage;
    }
}
