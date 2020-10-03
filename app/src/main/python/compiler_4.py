import os
from com.chaquo.python import Python
import time

def main(novum_inp):
    i=0
    files_dir = str(Python.getPlatform().getApplication().getFilesDir())
    out = ""
    def pr(text):
        global out
        out += str(text) + "|"
    def inp():
        i = i + 1
        return novum_inp[i-1]

    files_dir = str(Python.getPlatform().getApplication().getFilesDir())
    fh = open(files_dir + "/" + "pyCode.txt", "r")
    exec(fh.read())
    fh.close()
    return out
