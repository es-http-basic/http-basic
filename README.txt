This plugin revised http-basic plugin, encrypted the http.basic.password (using SHA-256). So the encrypted password can be stored in elasticsearch.yml.

Using like this: 
./gen_pass_paramvalue.sh 
input the password, only a-z A-Z 0-9 !@-+_= allowed, no space, max length 10:
123456
write the following parameter into elasticsearch.yml, using key: http.basic.password
8D969EEF6ECAD3C29A3A629280E686CF0C3F5D5A86AFF3CA12020C923ADC6C92
