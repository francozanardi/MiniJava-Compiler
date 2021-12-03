regenerar sources.txt:
	> python3 generateSources.py

compilar:
	> javac -encoding UTF-8 -d ./myOut @sources.txt

ejecutar: 
	> cd myOut
	> java ar.edu.uns.cs.minijava.Main pathfileToCompile pathfileOfCompiled