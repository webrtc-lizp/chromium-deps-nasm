# Makefile for the Netwide Assembler under 32-bit DOS(tm)
#
# The Netwide Assembler is copyright (C) 1996 Simon Tatham and
# Julian Hall. All rights reserved. The software is
# redistributable under the licence given in the file "Licence"
# distributed in the NASM archive.
#
# This Makefile is designed to build NASM using the 32-bit WIN32 C
# compiler Symantec(tm) C++ 7.5, provided you have a MAKE-utility
# that's compatible to SMAKE.

CC = sc
CCFLAGS = -c -a1 -mx -Nc -w2 -w7 -o+time -5
# -5            optimize for pentium (tm)
# -c            compile only
# -o-all        no optimizations (to avoid problems in disasm.c)
# -o+time       optimize for speed
# -o+space      optimize for size
# -A1           byte alignment for structures
# -mn           compile for Win32 executable
# -mx           compile for DOS386 (DOSX) executable
# -Nc           create COMDAT records
# -w2           possible unattended assignment: off
# -w7           for loops with empty instruction-body

LINK = link
LINKFLAGS = /noi /exet:DOSX
# /noignorecase all symbols are case-sensitive
# /exet:NT      Exetype: NT (Win32)
# /exet:DOSX    Exetype: DOSX (DOS32)
# /su:console   Subsystem: Console (Console-App)

LIBRARIES =
EXE = .exe
OBJ = obj

.c.$(OBJ):
        $(CC) $(CCFLAGS) $*.c


#
# modules needed for different programs
#

NASMOBJS = nasm.$(OBJ) nasmlib.$(OBJ) float.$(OBJ) insnsa.$(OBJ) \
           assemble.$(OBJ) labels.$(OBJ) parser.$(OBJ) outform.$(OBJ) \
	   output/outbin.$(OBJ) output/outaout.$(OBJ) output/outcoff.$(OBJ) \
	   output/outelf32.$(OBJ) output/outelf64.$(OBJ) \
	   output/outobj.$(OBJ) output/outas86.$(OBJ) output/outrdf.$(OBJ) output/outrdf2.$(OBJ) \
	   output/outieee.$(OBJ) output/outdbg.$(OBJ) \
	   preproc.$(OBJ) listing.$(OBJ) eval.$(OBJ)

NDISASMOBJS = ndisasm.$(OBJ) disasm.$(OBJ) sync.$(OBJ) nasmlib.$(OBJ) \
              insnsd.$(OBJ)


#
# programs to create
#

all : nasm$(EXE) ndisasm$(EXE)


#
# We have to have a horrible kludge here to get round the 128 character
# limit, as usual... we'll simply use LNK-files :)
#
nasm$(EXE): $(NASMOBJS)
        $(LINK) $(LINKFLAGS) @<<
cx.obj $(NASMOBJS)
nasm.exe
<<

ndisasm$(EXE): $(NDISASMOBJS)
        $(LINK) $(LINKFLAGS) @<<
cx.obj $(NDISASMOBJS)
ndisasm.exe
<<

#
# modules for programs
#
clean :
	del *.obj
	del nasm$(EXE)
	del ndisasm$(EXE)

#-- Magic hints to mkdep.pl --#
# @object-ending: ".$(OBJ)"
# @path-separator: "/"
#-- Everything below is generated by mkdep.pl - do not edit --#
assemble.$(OBJ): assemble.c preproc.h insns.h pptok.h regs.h regflags.c \
 config.h version.h nasmlib.h nasm.h regvals.c assemble.h insnsi.h
crc64.$(OBJ): crc64.c
disasm.$(OBJ): disasm.c insns.h sync.h regdis.c regs.h config.h regs.c \
 version.h nasm.h insnsn.c names.c insnsi.h disasm.h
eval.$(OBJ): eval.c labels.h eval.h regs.h config.h version.h nasmlib.h \
 nasm.h insnsi.h
float.$(OBJ): float.c regs.h config.h version.h nasm.h insnsi.h
hashtbl.$(OBJ): hashtbl.c regs.h config.h version.h nasmlib.h hashtbl.h \
 nasm.h insnsi.h
insnsa.$(OBJ): insnsa.c insns.h regs.h config.h version.h nasm.h insnsi.h
insnsd.$(OBJ): insnsd.c insns.h regs.h config.h version.h nasm.h insnsi.h
insnsn.$(OBJ): insnsn.c
labels.$(OBJ): labels.c regs.h config.h version.h hashtbl.h nasmlib.h nasm.h \
 insnsi.h
listing.$(OBJ): listing.c regs.h config.h version.h nasmlib.h nasm.h \
 insnsi.h listing.h
macros.$(OBJ): macros.c
names.$(OBJ): names.c regs.c insnsn.c
nasm.$(OBJ): nasm.c labels.h preproc.h insns.h parser.h eval.h pptok.h \
 regs.h outform.h config.h version.h nasmlib.h nasm.h stdscan.h assemble.h \
 insnsi.h listing.h
nasmlib.$(OBJ): nasmlib.c insns.h regs.h config.h version.h nasmlib.h nasm.h \
 insnsi.h
ndisasm.$(OBJ): ndisasm.c insns.h sync.h regs.h config.h version.h nasmlib.h \
 nasm.h insnsi.h disasm.h
outform.$(OBJ): outform.c regs.h config.h outform.h version.h nasm.h \
 insnsi.h
output/outaout.$(OBJ): output/outaout.c regs.h outform.h config.h version.h \
 nasmlib.h nasm.h stdscan.h insnsi.h
output/outas86.$(OBJ): output/outas86.c regs.h outform.h config.h version.h \
 nasmlib.h nasm.h insnsi.h
output/outbin.$(OBJ): output/outbin.c labels.h eval.h regs.h outform.h \
 config.h version.h nasmlib.h nasm.h stdscan.h insnsi.h
output/outcoff.$(OBJ): output/outcoff.c regs.h outform.h config.h version.h \
 nasmlib.h nasm.h insnsi.h
output/outdbg.$(OBJ): output/outdbg.c regs.h outform.h config.h version.h \
 nasmlib.h nasm.h insnsi.h
output/outelf32.$(OBJ): output/outelf32.c regs.h outform.h config.h \
 version.h nasmlib.h nasm.h stdscan.h insnsi.h
output/outelf64.$(OBJ): output/outelf64.c regs.h outform.h config.h \
 version.h nasmlib.h nasm.h stdscan.h insnsi.h
output/outieee.$(OBJ): output/outieee.c regs.h outform.h config.h version.h \
 nasmlib.h nasm.h insnsi.h
output/outmacho.$(OBJ): output/outmacho.c compiler.h regs.h outform.h \
 config.h version.h nasmlib.h nasm.h insnsi.h
output/outobj.$(OBJ): output/outobj.c regs.h outform.h config.h version.h \
 nasmlib.h nasm.h stdscan.h insnsi.h
output/outrdf.$(OBJ): output/outrdf.c regs.h outform.h config.h version.h \
 nasmlib.h nasm.h insnsi.h
output/outrdf2.$(OBJ): output/outrdf2.c rdoff/rdoff.h regs.h outform.h \
 config.h version.h nasmlib.h nasm.h insnsi.h
parser.$(OBJ): parser.c insns.h parser.h float.h regs.h regflags.c config.h \
 version.h nasmlib.h nasm.h stdscan.h insnsi.h
pptok.$(OBJ): pptok.c preproc.h pptok.h nasmlib.h
preproc.$(OBJ): preproc.c preproc.h macros.c pptok.h regs.h config.h \
 version.h nasmlib.h nasm.h insnsi.h
regdis.$(OBJ): regdis.c
regflags.$(OBJ): regflags.c
regs.$(OBJ): regs.c
regvals.$(OBJ): regvals.c
stdscan.$(OBJ): stdscan.c insns.h regs.h config.h version.h nasmlib.h nasm.h \
 stdscan.h insnsi.h
sync.$(OBJ): sync.c sync.h
tokhash.$(OBJ): tokhash.c insns.h regs.h config.h version.h nasm.h insnsi.h
