JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
	MazeRunner.java \
	Maze.java \
	MazeDisplay.java \
	MazeGenerator.java \
	MazeSolver.java \
	MazeClickListener.java \
	Cell.java

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class

run:
	@java MazeRunner
