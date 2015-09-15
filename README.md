# Setting meta data in PDF files with Mathematica

Place the `PDFMetaData` sub-directory where *Mathematica* can find it.
For instance in

```
FileNameJoin[{$UserBaseDirectory, "Applications"}]
```

and then use it like this:


    << PDFMetaData`

    SetPDFMetaData[
      Export["tmp/meta.pdf", "Hello"]][
        {
           "Author" -> "Xavier Breath",
           "Title" -> "Stop Arguing!",
           "Creator" -> "Funny Inc.",
           "Producer" -> "Nick R. Elastic",
           "Keywords" -> "Mathematica PDF"
        }
    ]