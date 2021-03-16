JAVADIR=/usr/share/java
JARNAME=woo-app

all:
	(cd src; javac -cp /mnt/c/Users/gugag/Desktop/IST/'2 Ano'/PO/Projeto/po-uuilib/po-uuilib.jar:/mnt/c/Users/gugag/Desktop/IST/'2 Ano'/PO/project/woo-core/woo-core.jar -encoding UTF-8 `find . -name \*.java`)
	(cd src; jar cf ../$(JARNAME).jar `find . -name \*.class -o -name \*.java` )

clean:
	$(RM) $(JARNAME).jar `find . -name \*.class`
