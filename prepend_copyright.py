#!/usr/bin/python

# ./prepend_copyright.py PREAMBLE $(find src/ | grep "\.java$")

import sys
import string

args = len(sys.argv)
if not args > 2:
	print "usage: prepend_copyright.py copyright file1 [file2, ..., fileN]"
	exit()	

f = open(sys.argv[1], "r")
copyright = f.read();
f.close()

for x in sys.argv[2:]:
	print x
	f = open(x, "r")
	c = f.read()
	f.close()
	f = open(x, "w")
	f.write(copyright)
	f.write(c)
	f.close()
