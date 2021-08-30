import os

RUTA_INICIAL = "./src"

sourceList = open("sources.txt", "w")

def searchCompilableFiles(path):
	with os.scandir(path) as entries:
		for entry in entries:
			if entry.is_dir():
				searchCompilableFiles(path+'/'+entry.name)
			elif entry.is_file() and isCompilableFile(entry.name):
				sourceList.write(path+'/'+entry.name+'\n')

def isCompilableFile(filename):
	return filename.split(".")[-1] == "java"

searchCompilableFiles(RUTA_INICIAL)