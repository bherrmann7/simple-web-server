
echo Builds and installs into your $HOME/bin the simple web server, "sws" program

clj -T:build jar
mv target/simple-web-server-standalone.jar ~/bin/
echo 'java -jar $HOME/bin/simple-web-server-standalone.jar $*' > ~/bin/sws
chmod a+x ~/bin/sws

