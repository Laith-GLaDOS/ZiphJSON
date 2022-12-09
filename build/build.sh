echo "Building source..." &&
javac ../src/ziph/*.java -d ./output &&
echo "Creating jar file..." &&
cd output &&
jar cmf ../manifest.mf ./ZiphJSON.jar ./ziph/*.class &&
echo "Cleaning up..." &&
rm -rf ziph
