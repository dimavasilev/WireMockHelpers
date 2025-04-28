package services.soap;

import jakarta.xml.soap.MessageFactory;
import jakarta.xml.soap.MimeHeaders;
import jakarta.xml.soap.SOAPBody;
import jakarta.xml.soap.SOAPConnection;
import jakarta.xml.soap.SOAPConnectionFactory;
import jakarta.xml.soap.SOAPEnvelope;
import jakarta.xml.soap.SOAPException;
import jakarta.xml.soap.SOAPMessage;
import jakarta.xml.soap.SOAPPart;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;

public class SoapClient {
  private final String endpoint;
  private final String namespace;

  public SoapClient(String endpoint, String namespace) {
    this.endpoint = endpoint;
    this.namespace = namespace;
  }

  public String callSoapService(String soapAction, String requestBody) throws SOAPException {
    try {
      // Создаем SOAP соединение
      SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
      SOAPConnection connection = soapConnectionFactory.createConnection();

      // Создаем сообщение
      MessageFactory messageFactory = MessageFactory.newInstance();
      SOAPMessage message = messageFactory.createMessage();
      message.setProperty("Content-Type", "text/xml");

      // Устанавливаем заголовки
      MimeHeaders headers = message.getMimeHeaders();
      headers.setHeader("Accept", "*/*");
      headers.setHeader("SOAPAction", soapAction);
      headers.setHeader("Content-Type", "text/xml");

      // Создаем SOAP envelope
      SOAPPart soapPart = message.getSOAPPart();
      SOAPEnvelope envelope = soapPart.getEnvelope();
      envelope.addNamespaceDeclaration("ns", namespace);

      // Добавляем тело запроса
      SOAPBody soapBody = envelope.getBody();
      soapBody.addChildElement(envelope.createName("getUserRequest", "ns", namespace))
          .addChildElement("name")
          .addTextNode("Test user");

      // Сохраняем изменения
      message.saveChanges();

      // Отправляем запрос
      SOAPMessage response = connection.call(message, endpoint);
      connection.close();

      // Преобразуем ответ в строку
      return messageToString(response);

    } catch (Exception e) {
      throw new SOAPException("SOAP request failed", e);
    }
  }

  private String messageToString(SOAPMessage message) throws Exception {
    TransformerFactory transformerFactory = TransformerFactory.newInstance();
    Transformer transformer = transformerFactory.newTransformer();
    Source source = message.getSOAPPart().getContent();
    StringWriter writer = new StringWriter();
    StreamResult result = new StreamResult(writer);
    transformer.transform(source, result);
    return writer.toString();
  }
}