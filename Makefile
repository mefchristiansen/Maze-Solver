JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
	MazeSolver.java \
	Node.java

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class

run:
	@java MazeSolver
