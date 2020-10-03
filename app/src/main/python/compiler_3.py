import os
from com.chaquo.python import Python
import time

def main(novum_inp):
    files_dir = str(Python.getPlatform().getApplication().getFilesDir())
    out = ""
    def pr(text):
        global out
        out += str(text) + "|"
    def inp():
        return novum_inp

    files_dir = str(Python.getPlatform().getApplication().getFilesDir())
    fh = open(files_dir + "/" + "pyCode.txt", "r")
    exec(fh.read())
    fh.close()
    return out
