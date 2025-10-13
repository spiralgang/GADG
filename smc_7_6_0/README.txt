SMC - The State Machine Compiler 7.6.0 Downloads


NOTE: This release introduces a new release format in which all
      files associated with the release are contained in a single
      folder.


Major Changes: None.

Minor Changes: None.

Bug Fixes:

Bug #238: Wrong header path generated.
          Given the command line option "-headerd ../include/foo/bar"
          the generated header path is "../include/include/include/yyy.h"
          but should be "../include/foo/bar/yyy.h"
          Target: C, C++

There are six download folders:

+ Bin: contains Smc.jar used to compile .sm file into target
       language file.

+ Documentation: contains SMC Programmer's Manul in PDF and HTML
          web pages. Web page files are in tar, gzipped and zip
          formats.

+ Examples: Example applications using SMC and in all supported
            target programming languages.

+ Library: contains SMC API header and library files needed to
           compile SMC-based applications in each of the
           supported target programming languages.

+ Source Code: snapshot of SMC maven project files at release
          time.

+ Tools: contains the SMC maven plug-in jar files, including
         source and javadocs.
