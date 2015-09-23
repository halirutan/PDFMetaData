package de.halirutan.pdfmetadata;

import junit.framework.TestCase;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.AccessPermission;

import java.io.File;
import java.net.URL;

/**
 * @author halirutan (23.09.15)
 */
public class PDFLockerTest extends TestCase {

  public void testLockDocument() throws Exception {
    final ClassLoader classLoader = PDFLockerTest.class.getClassLoader();
    final URL file = classLoader.getResource("de/halirutan/pdfmetadata/test.pdf");
    assertNotNull(file);
    final String outputFileName = (new File(file.getPath())).getParent() + File.separator + "output.pdf";
    final String outputMessage = PDFLocker.lockDocument(file.getFile(), outputFileName);
    assertEquals(outputMessage, outputFileName);
    PDDocument lockedPDF = PDDocument.load(new File(file.toURI()));
    final AccessPermission access = lockedPDF.getCurrentAccessPermission();
    assertEquals("Modify", false, access.canModify());
    assertEquals("Modify Annotations: ", false, access.canModifyAnnotations());
    assertEquals("Assemble Document: ", false, access.canAssembleDocument());
    assertEquals("Extract Content: ", false, access.canExtractContent());
    assertEquals("Print: ", true, access.canPrint());
    assertEquals("Print Degraded: ", true, access.canPrintDegraded());
    assertEquals("Are Permissions readonly? ", true, access.isReadOnly());

  }
}