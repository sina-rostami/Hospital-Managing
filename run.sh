#!/bin/bash
mainRoot=src/main/java/ir/ac/kntu
javac mainRoot/*.java
java mainRoot/Main.java
rm mainRoot/*.class

