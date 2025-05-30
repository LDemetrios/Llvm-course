build-app:
	mkdir build || continue
	cd build && clang ../app/*.c -lSDL2 -lm

run-app: build-app
	./build/a.out

emit-llvm:
	mkdir llvm || continue
	cd llvm && clang ../app/*.c -S -emit-llvm

build-stat:
	mkdir build || continue
	clang++ stat/pass.cpp -fPIC -shared -I$$(llvm-config --includedir) -o build/tracer.so && \
	cd stat && \
	clang ../app/*.c -fpass-plugin=../build/tracer.so -Os -S -emit-llvm && \
	clang *.ll -lSDL2

run-stat: build-stat
# dozen of frames or two...
	stat/a.out | head -n 400000000 > log.txt && \
	du -h log.txt && \
  	gradle collect-stat && \
  	rm log.txt

clean:
	rm -rf llvm build **/*.ll **/*.out simulator.jar

prepare: run-stat clean emit-llvm

simulator.jar:
	gradle shadowJar
	cp build/libs/Llvm-course-1.0-SNAPSHOT-all.jar ./simulator.jar

run-simple-example: simulator.jar
	java -jar simulator.jar -d ./examples/simple.s

run-graphics-example: simulator.jar
	java -jar simulator.jar -nb ./examples/graphics.s

simulator-help: simulator.jar
	java -jar simulator.jar --help