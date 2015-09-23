package de.halirutan.pdfmetadata;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.AccessPermission;
import org.apache.pdfbox.pdmodel.encryption.StandardProtectionPolicy;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * @author halirutan (23.09.15)
 */
@SuppressWarnings("unused")
public class PDFLocker {

  public static String lockDocument(final String fileName, final String outputFileName) throws Exception {
    try {
      File file = new File(fileName);
      final PDDocument document = PDDocument.load(file);
      AccessPermission accessPermission = new AccessPermission();

      accessPermission.setCanModify(false);
      accessPermission.setCanModifyAnnotations(false);
      accessPermission.setCanAssembleDocument(false);
      accessPermission.setCanExtractContent(false);
      accessPermission.setCanPrint(true);
      accessPermission.setCanPrintDegraded(true);
      accessPermission.setReadOnly();

      StandardProtectionPolicy standardProtectionPolicy = new StandardProtectionPolicy(UUID.randomUUID().toString(), "", accessPermission);
      standardProtectionPolicy.setEncryptionKeyLength(128);
      standardProtectionPolicy.setPermissions(accessPermission);
      document.protect(standardProtectionPolicy);

      document.save(outputFileName);
      document.close();
      return outputFileName;
    } catch (IOException e) {
      return "Exception: " + e.getMessage();
    }
  }
}
