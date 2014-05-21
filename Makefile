STUB = ./build/stub.jar

IN_DIR = ./build/in
IN = $(IN_DIR)/build.jar

OUT_DIR = ./build/out
OUT = $(OUT_DIR)/release.jar

TMP_DIR = ./build/tmp

all: build

build: $(OUT)

$(OUT): $(IN) $(STUB)
	# clean
	mkdir -p $(TMP_DIR)
	mkdir -p $(OUT_DIR)
	
	# extract
	unzip $(IN) -d $(TMP_DIR)
	
	rm -rf $(TMP_DIR)/META-INF
	
	unzip $(STUB) -d $(TMP_DIR)
	
	find $(TMP_DIR) -name "*.xcf" -type f -delete
	
	# export
	(cd $(TMP_DIR); zip -r9 ./pack.zip .)
	mv -f $(TMP_DIR)/pack.zip $(OUT)
	chmod +x $(OUT)

	# clean
	rm -rf $(TMP_DIR)


run: $(OUT)
	java -jar $(OUT) -w .rogue-save
	
debug: $(OUT)
	java -jar $(OUT) -w .rogue-save --verbose --debug-bus
	
stats:
	@-echo "Commits:" `git rev-list HEAD --count`
	@-echo "Files:" `find src -type f -print | wc -l`
	@-echo "Lines:" `(find src -name '*.java' -print0 | xargs -0 cat ) | wc -l`

deploy: $(OUT)
	cp -f $(OUT) /home/ondra/Dropbox/Public/Rogue
