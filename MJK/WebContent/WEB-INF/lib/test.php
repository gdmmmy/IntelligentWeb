<?php

require_once("java/Java.inc");

// get instance of Java class java.lang.System in PHP

$system = new Java('java.lang.System');

$s = new Java("java.lang.String", "php-java-bridge config...<br><br>");

echo $s;

// demonstrate property access

print 'Java version='.$system->getProperty('java.version').' <br>';

print 'Java vendor=' .$system->getProperty('java.vendor').' <br>';

print 'OS='.$system->getProperty('os.name').' '.

$system->getProperty('os.version').' on '.

$system->getProperty('os.arch').' <br>';

// java.util.Date example

$formatter = new Java('java.text.SimpleDateFormat',

"EEEE, MMMM dd, yyyy 'at' h:mm:ss a zzzz");

print $formatter->format(new Java('java.util.Date'));

?>