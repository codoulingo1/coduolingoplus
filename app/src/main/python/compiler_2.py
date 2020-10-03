import os
from com.chaquo.python import Python
import time

out = ""
def main():
    global out
    out = ""
    def pr(text):
        global out
        out += str(text) + "|"

    files_dir = str(Python.getPlatform().getApplication().getFilesDir())
    fh = open(files_dir + "/" + "pyCode.txt", "r")
    exec(fh.read())
    fh.close()
    return out

i = 0
def main_2(novum_inp):
    global out
    i=0
    files_dir = str(Python.getPlatform().getApplication().getFilesDir())
    out = ""
    def pr(text):
        global out
        out += str(text) + "|"
    def inp():
        global i
        return novum_inp[i]
        i = i + 1

    files_dir = str(Python.getPlatform().getApplication().getFilesDir())
    fh = open(files_dir + "/" + "pyCode.txt", "r")
    exec(fh.read())
    fh.close()
    return out
