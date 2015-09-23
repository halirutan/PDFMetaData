package de.halirutan.pdfmetadata;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;

import java.io.File;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

/**
 * Simple class for altering the meta data in a pdf file
 * @author halirutan (15.09.15)
 */
@SuppressWarnings("unused")
public class PDFMetaDataSetter {

  public static final Set<String> META_NAME;

  static {
    META_NAME = new HashSet<>();
    META_NAME.add("Title");
    META_NAME.add("Author");
    META_NAME.add("Subject");
    META_NAME.add("Keywords");
    META_NAME.add("Creator");
    META_NAME.add("Producer");
  }

  /**
   * Opens a pdf file and give the opportunity to set some meta information.
   * @param fileName Existing PDF file name
   * @param meta a list of {key1, value1, key2, value2, ...} pairs for setting meta data.
   * @return fileName of the pdf file or the exception message if something went wrong.
   */
  public static String setMetaData(final String fileName, final String outputFilename, final String meta[]){
    try {
      PDDocument document = PDDocument.load(new File(fileName));
      final PDDocumentInformation information = document.getDocumentInformation();
      if (meta.length % 2 != 0) {
        throw new Exception("Meta-data list has not an even number of elements.");
      }
      for (int i = 0; i < meta.length; i++) {
        String key = meta[i++];
        String value = meta[i];
        if (META_NAME.contains(key)) {
          Method method = information.getClass().getMethod("set" + key, String.class);
          method.invoke(information, value);
        }

      }

      document.setDocumentInformation(information);
      document.save(outputFilename);
      document.close();
      return fileName;
    } catch (Exception e) {
      return "Exception: " + e.getMessage();
    }
  }
}
