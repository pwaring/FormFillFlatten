import java.io.*;
import java.util.Iterator;

// itext
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

// JSON-java
import org.json.JSONObject;
import org.json.JSONException;

public class FormFillFlatten
{
  public static void main(String args[])
  {
    PdfReader reader;
    PdfStamper stamper;
    FileOutputStream outputStream;
    BufferedReader jsonReader;
    
    String jsonFilename = args[0];
    String inputFilename = args[1];
    String outputFilename = args[2];
    
    StringBuffer jsonData;
    String currentLine;
    JSONObject jsonObj;
    
    Iterator fieldIterator;
    
    try
    {
      jsonReader = new BufferedReader(new FileReader(jsonFilename));
      outputStream = new FileOutputStream(outputFilename);
      reader = new PdfReader(inputFilename);
      stamper = new PdfStamper(reader, outputStream);
      
      jsonData = new StringBuffer();
      
      while ((currentLine = jsonReader.readLine()) != null)
      {
        jsonData.append(currentLine);
      }
      
      jsonObj = new JSONObject(jsonData.toString());
      
      fieldIterator = jsonObj.keys();
      AcroFields fields = stamper.getAcroFields();
      
      while (fieldIterator.hasNext())
      {
        String fieldName = (String) fieldIterator.next();
        String fieldValue = (String) jsonObj.getString(fieldName);
        
        fields.setField(fieldName, fieldValue);
      }
      
      stamper.setFormFlattening(true);
      stamper.close();
    }
    catch (IOException e)
    {
      System.err.println("IOException: " + e.getMessage());
    }
    catch (DocumentException e)
    {
      System.err.println("DocumentException: " + e.getMessage());
    }
    catch (JSONException e)
    {
      System.err.println("JSONException: " + e.getMessage());
    }
  }
}
