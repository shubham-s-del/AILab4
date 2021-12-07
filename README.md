# AI LAB 4
KNN and KMeans




Name: Shubham Srivastava

NetID: ss14687




##Running options:

(Only specifying the way to compile from source code, as that is what is preferred as per the professor. Please reach out in case there is an issue in running the code this way.)


###Compiling the code on the spot, and running manually:

unzip AILab4.zip (if you haven't done that already)
cd AILab4 (if you are not already in this directory)

To compile the source code - 
mvn clean compile assembly:single

Running from command line:

KNN:

java -jar target/AILab4-1.0-SNAPSHOT-jar-with-dependencies.jar -mode knn -k <k> -d <e2|manh> <optional -unitw flag> -train <absolute_path_to_train_file> -test <absolute_path_to_test_file>


eg: java -jar target/AILab4-1.0-SNAPSHOT-jar-with-dependencies.jar -mode knn -k 7 -d manh -unitw -train input3.txt -test testinput3.txt

KMeans:


java -jar target/AILab4-1.0-SNAPSHOT-jar-with-dependencies.jar -mode kmeans -d <e2|manh> -data <absolute_path_to_input_file> <centroid1> <centroid2> <centroid3...>


eg: java -jar target/AILab4-1.0-SNAPSHOT-jar-with-dependencies.jar -mode kmeans -d e2 -data km2.txt 0,0,0 200,200,200 500,500,500



