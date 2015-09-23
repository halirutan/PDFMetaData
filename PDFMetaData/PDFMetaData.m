(* Mathematica Package *)
(* Created by Mathematica Plugin for IntelliJ IDEA *)

(* :Title: PDFMetaData *)
(* :Context: PDFMetaData` *)
(* :Author: patrick *)
(* :Date: 2015-09-15 *)

(* :Package Version: 0.1 *)
(* :Mathematica Version: *)
(* :Copyright: (c) 2015 halirutan *)
(* :Keywords: *)
(* :Discussion: *)

BeginPackage["PDFMetaData`", {"JLink`"}];
(* Exported symbols added here with SymbolName::usage *)

SetPDFMetaData::usage = "SetPDFMetaData[file][meta] sets metadata in the pdf file. The argument meta needs to be a list of rules {\"Author\" -> \"Pam\", ...} and possible meta keys are Author, Title, Subject, Keywords, Creator, Producer.";

Begin["`Private`"];

SetPDFMetaData[file_String /; FileExistsQ[file]][meta : {(_String -> _String)..}] := JavaBlock[
  InstallJava[];
  LoadJavaClass["de.halirutan.pdfmetadata.PDFMetaDataSetter", StaticsVisible -> True];
  PDFMetaDataSetter`setMetaData[file, file, Flatten[List @@@ meta]]
];

LockPDF[file_String /; FileExistsQ[file]] := JavaBlock[
  InstallJava[];
  LoadJavaClass["de.halirutan.pdfmetadata.PDFLocker", StaticsVisible -> True];
  PDFLocker`lockDocument[file, file]
];

End[]; (* `Private` *)

EndPackage[];