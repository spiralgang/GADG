


                              SMC -
                     The State Machine Compiler


+ Installing the Php library
----------------------------

1. To install the Php statemap file in your own directory tree,
   then download statemap.php and package.xml to that directory.

2. To install the php statemap module on your system with PEAR
   (usually defaults to /usr/share/php/StateMachine/statemap.php),
   do:

   # pear install package.xml

   After installing, the FSMs can include it with
   require_once 'StateMachine/statemap.php';

   To uninstall the statemap, use

   # pear uninstall __uri/statemap
