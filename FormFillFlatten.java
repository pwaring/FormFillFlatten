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
    String jsonFilename = args[0];
    String inputFilename = args[1];
    String outputFilename = args[2];
    
    try
    {
      BufferedReader jsonReader = new BufferedReader(new FileReader(jsonFilename));
      FileOutputStream outputStream = new FileOutputStream(outputFilename);
      PdfReader reader = new PdfReader(inputFilename);
      PdfStamper stamper = new PdfStamper(reader, outputStream);
      
      StringBuffer jsonData = new StringBuffer();
      String currentLine;
      
      // Read in all the JSON data
      while ((currentLine = jsonReader.readLine()) != null)
      {
        jsonData.append(currentLine);
      }
      
      // Convert JSON string into an accessible JSON object
      JSONObject jsonObj = new JSONObject(jsonData.toString());
      
      Iterator fieldIterator = jsonObj.keys();
      AcroFields fields = stamper.getAcroFields();
      
      // Set all the fields based on the JSON config
      while (fieldIterator.hasNext())
      {
        String fieldName = (String) fieldIterator.next();
        String fieldValue = (String) jsonObj.getString(fieldName);
        
        fields.setField(fieldName, fieldValue);
      }
      
      // Flatten and save the PDF
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
