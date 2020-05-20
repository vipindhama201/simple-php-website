#download the image with PHP and apache
FROM devopsedu/webapp

#docker file author and  maintained by 
MAINTAINER Raveendiran-RR

#delete the default contents
RUN rm -f  /var/www/html/*

#copy the contents of the simple-php website
COPY  . /var/www/html/

#expose the port 80 of the container when this image is run
EXPOSE 80

# Apache is started when a conatiner is created
CMD ["/usr/sbin/apachectl","-D","FOREGROUND"]
