#!/bin/bash
cd src/main/java
mainRoot=ir/ac/kntu
javac $mainRoot/*.java
java $mainRoot/Main.java
rm $mainRoot/*.class

