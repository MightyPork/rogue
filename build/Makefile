STUB = ./stub.jar

IN_DIR = ./in
IN = $(IN_DIR)/build.jar

OUT_DIR = ./out
OUT = $(OUT_DIR)/release.jar

TMP_DIR = ./tmp

all:
	# clean
	mkdir -p $(TMP_DIR)
	mkdir -p $(OUT_DIR)
	
	# extract
	unzip $(IN) -d $(TMP_DIR)
	
	rm -rf $(TMP_DIR)/META-INF
	
	unzip $(STUB) -d $(TMP_DIR)
	
	# export	
	(cd $(TMP_DIR); zip -r9 ./pack.zip .)
	mv -f $(TMP_DIR)/pack.zip $(OUT)
	chmod +x $(OUT)

	# clean
	rm -rf $(TMP_DIR)

run:
	java -jar $(OUT)