@echo off
rmdir build /s /q
mkdir build
"C:\Program Files\Java\jdk-10.0.1\bin\javac" -d build --module-path ../assembly src/module-info.java src/beer/mathematics/BeerParameter.java src/beer/mathematics/BeerMathResult.java
cd build
"C:\Program Files\Java\jdk-10.0.1\bin\jar" cf beer-mathematics.jar *
cd ..