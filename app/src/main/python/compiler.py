import os
from com.chaquo.python import Python
import time

def main():
    files_dir = str(Python.getPlatform().getApplication().getFilesDir())
    with open(files_dir + "/" + "pyOut.txt", "w") as wr:
        wr.write("")
    def pr(text):
        files_dir = str(Python.getPlatform().getApplication().getFilesDir())
        with open(files_dir + "/" + "pyOut.txt", "a") as wr:
            wr.write(str(text) + "|")
    def inp():
        with open(files_dir + "/" + "pyInp.txt", "w") as wr:
            wr.write("wait")
        while True:
            with open(files_dir + "/" + "pyInp.txt", "r") as rd:
                text = rd.read()
            if (text!="wait" and text!=""):
                break
        with open(files_dir + "/" + "pyInp.txt", "r") as rd:
            text = rd.read()
        return text

    files_dir = str(Python.getPlatform().getApplication().getFilesDir())
    fh = open(files_dir + "/" + "pyCode.txt", "r")
    exec(fh.read())
    fh.close()