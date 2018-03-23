all: requirements.pdf architecture.pdf

%.pdf: %.tex *.tex template/*.tex
	pdflatex $<

clean:
	@echo "cleaning..."
	@rm -f *.log *~ *.aux *.ptb *.out *.toc

white : clean
	@echo "removing pdf files.."
	rm -f *.pdf

.PHONY: clean white
