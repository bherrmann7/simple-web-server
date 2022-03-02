
# Notes on creating and running self signed cert

openssl genrsa -des3 -out jetty.key
openssl req -new -x509 -key jetty.key -out jetty.crt
keytool -keystore keystore -import -alias jetty -file jetty.crt -trustcacerts
openssl req -new -key jetty.key -out jetty.csr
openssl pkcs12 -inkey jetty.key -in jetty.crt -export -out jetty.pkcs12
keytool -importkeystore -srckeystore jetty.pkcs12 -srcstoretype PKCS12 -destkeystore keystore


